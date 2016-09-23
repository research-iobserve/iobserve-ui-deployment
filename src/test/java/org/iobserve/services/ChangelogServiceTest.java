package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;

import org.iobserve.models.*;

import org.iobserve.models.Service;
import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.models.util.Status;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.services.util.EntityManagerTestSetup;
import org.iobserve.services.websocket.ChangelogStreamService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class ChangelogServiceTest {

    private static final String testSystem = "system123";


    private EntityManagerFactory entityManagerFactory;
    private final EntityToDtoMapper entityToDtoMapper = EntityToDtoMapper.INSTANCE;
    private final DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper = DtoToBasePropertyEntityMapper.INSTANCE;

    @Mock
    ServiceLocator mockedServiceLocator;

    @Mock
    ChangelogStreamService mockedChangelogStreamService;

    private ChangelogService changelogService;
    private NodeService nodeService;
    private ServiceInstanceService serviceInstanceService;
    private CommunicationInstanceService communicationInstanceService;
    private TimeSeriesService timeSeriesService;
    private SeriesElementService seriesElementService;
    private StatusInfoService statusInfoService;


    @Before
    public void setUp(){
        this.entityManagerFactory = EntityManagerTestSetup.getEntityManagerFactory();

        this.changelogService = new ChangelogService(entityManagerFactory, entityToDtoMapper, mockedServiceLocator, dtoToBasePropertyEntityMapper,
                mockedChangelogStreamService);

        this.nodeService = new NodeService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.serviceInstanceService = new ServiceInstanceService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.communicationInstanceService = new CommunicationInstanceService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.timeSeriesService = new TimeSeriesService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.seriesElementService = new SeriesElementService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.statusInfoService = new StatusInfoService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);

        when(mockedServiceLocator.getService(ChangelogService.class)).thenReturn(this.changelogService);
        when(mockedServiceLocator.getService(NodeService.class)).thenReturn(this.nodeService);
        when(mockedServiceLocator.getService(ServiceInstanceService.class)).thenReturn(this.serviceInstanceService);
        when(mockedServiceLocator.getService(CommunicationInstanceService.class)).thenReturn(this.communicationInstanceService);
        when(mockedServiceLocator.getService(TimeSeriesService.class)).thenReturn(this.timeSeriesService);
        when(mockedServiceLocator.getService(SeriesElementService.class)).thenReturn(this.seriesElementService);
        when(mockedServiceLocator.getService(StatusInfoService.class)).thenReturn(this.statusInfoService);
    }


    @Test
    public void createChangelog(){
        List<ChangelogDto> changelogDtoList = new LinkedList<>();
        NodeDto newNodeDto = createNewNode();

        ChangelogDto createChangelog = new ChangelogDto();
        createChangelog.setOperation(ChangelogOperation.CREATE);
        createChangelog.setData(newNodeDto);

        changelogDtoList.add(createChangelog);

        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        //assert that the changelog is correctly progressed
        EntityManager em = this.entityManagerFactory.createEntityManager();

        Node node = em.find(Node.class, newNodeDto.getId());

        assertNotNull(node);
        assertEquals(node.getNodeGroup().getId(), newNodeDto.getNodeGroupId());
        assertEquals(node.getName(), newNodeDto.getName());

        em.close();
    }

    /**
     * delete a node in 3 steps:
     * 1. delete all communications
     * 2. delete all services
     * 3. delete the node
     */
    @Test
    public void deleteChangelogStepByStep(){
        /* delete the communication */
        List<ChangelogDto> changelogDtoList = new LinkedList<>();

        String oldCommunicationInstanceId = "test-system123-communicationInstance-1";
        EntityManager em = this.entityManagerFactory.createEntityManager();
        CommunicationInstance oldCommunicationInstance = em.find(CommunicationInstance.class,oldCommunicationInstanceId);

        assertNotNull(oldCommunicationInstance);

        String oldCommunicationId = oldCommunicationInstance.getCommunication().getId();

        CommunicationInstanceDto oldCommunicationInstanceDto = this.communicationInstanceService.transformModelToDto(oldCommunicationInstance);

        ChangelogDto deleteChangelog = new ChangelogDto();
        deleteChangelog.setOperation(ChangelogOperation.DELETE);
        deleteChangelog.setData(oldCommunicationInstanceDto);

        changelogDtoList.add(deleteChangelog);
        em.close();

        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        oldCommunicationInstance = em.find(CommunicationInstance.class,oldCommunicationInstanceId);
        Communication oldCommunication = em.find(Communication.class, oldCommunicationId);

        assertNull(oldCommunicationInstance);
        assertNotNull(oldCommunicationId);

        oldCommunication.getInstances().forEach(instance -> assertFalse(instance.getId().equals(oldCommunicationInstanceId)));


        /* delete the service */
        changelogDtoList.clear();

        String oldServiceInstanceId = "test-system123-serviceInstance-1";

        ServiceInstance oldServiceInstance = em.find(ServiceInstance.class,oldServiceInstanceId);

        assertNotNull(oldServiceInstance);

        String oldServiceId = oldServiceInstance.getService().getId();

        ServiceInstanceDto oldServiceInstanceDto = this.serviceInstanceService.transformModelToDto(oldServiceInstance);

        deleteChangelog = new ChangelogDto();
        deleteChangelog.setOperation(ChangelogOperation.DELETE);
        deleteChangelog.setData(oldServiceInstanceDto);

        changelogDtoList.add(deleteChangelog);
        em.close();

        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        oldServiceInstance = em.find(ServiceInstance.class,oldServiceInstanceId);
        Service oldService = em.find(Service.class, oldServiceId);

        assertNull(oldServiceInstance);
        assertNotNull(oldServiceId);

        oldService.getInstances().forEach(instance -> assertFalse(instance.getId().equals(oldServiceInstanceId)));


         /* delete the node */
        changelogDtoList.clear();

        String oldNodeId = "test-system123-node-1";

        Node oldNode = em.find(Node.class,oldNodeId);

        assertNotNull(oldNode);

        String oldNodeGroupId = oldNode.getNodeGroup().getId();

        NodeDto oldNodeDto = this.nodeService.transformModelToDto(oldNode);

        deleteChangelog = new ChangelogDto();
        deleteChangelog.setOperation(ChangelogOperation.DELETE);
        deleteChangelog.setData(oldNodeDto);

        changelogDtoList.add(deleteChangelog);
        em.close();

        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        oldNode = em.find(Node.class,oldNodeId);
        NodeGroup oldNodeGroup = em.find(NodeGroup.class, oldNodeGroupId);

        assertNull(oldNode);
        assertNotNull(oldNodeGroupId);

        oldNodeGroup.getNodes().forEach(instance -> assertFalse(instance.getId().equals(oldNodeId)));
        em.close();
    }

    /**
     * delete a node with one Changeloglist
     */
    @Test
    public void deleteChangelogOneStep(){
        List<ChangelogDto> changelogDtoList = new LinkedList<>();
        EntityManager em = this.entityManagerFactory.createEntityManager();

        //set up he entities that have to be deleted
        String oldCommunicationInstanceId = "test-system123-communicationInstance-1";
        String oldServiceInstanceId = "test-system123-serviceInstance-1";
        String oldNodeId = "test-system123-node-1";

        CommunicationInstance oldCommunicationInstance = em.find(CommunicationInstance.class,oldCommunicationInstanceId);
        ServiceInstance oldServiceInstance = em.find(ServiceInstance.class,oldServiceInstanceId);
        Node oldNode = em.find(Node.class,oldNodeId);

        String oldCommunicationId = oldCommunicationInstance.getCommunication().getId();
        String oldServiceId = oldServiceInstance.getService().getId();
        String oldNodeGroupId = oldNode.getNodeGroup().getId();

        CommunicationInstanceDto oldCommunicationInstanceDto = this.communicationInstanceService.transformModelToDto(oldCommunicationInstance);
        ServiceInstanceDto oldServiceInstanceDto = this.serviceInstanceService.transformModelToDto(oldServiceInstance);
        NodeDto oldNodeDto = this.nodeService.transformModelToDto(oldNode);

        //assert the entities exist in the system
        assertNotNull(oldNode);
        assertNotNull(oldCommunicationInstance);
        assertNotNull(oldServiceInstance);

        //create the changelogs
        ChangelogDto deleteCommunicationInstanceChangelog = new ChangelogDto();
        deleteCommunicationInstanceChangelog.setOperation(ChangelogOperation.DELETE);
        deleteCommunicationInstanceChangelog.setData(oldCommunicationInstanceDto);

        ChangelogDto deleteServiceInstanceChangelog = new ChangelogDto();
        deleteServiceInstanceChangelog.setOperation(ChangelogOperation.DELETE);
        deleteServiceInstanceChangelog.setData(oldServiceInstanceDto);

        ChangelogDto deleteNodeChangelog = new ChangelogDto();
        deleteNodeChangelog.setOperation(ChangelogOperation.DELETE);
        deleteNodeChangelog.setData(oldNodeDto);

        //assert order of the changelogs
        changelogDtoList.add(0,deleteCommunicationInstanceChangelog);
        changelogDtoList.add(1,deleteServiceInstanceChangelog);
        changelogDtoList.add(2,deleteNodeChangelog);
        em.close();


        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        oldCommunicationInstance = em.find(CommunicationInstance.class,oldCommunicationInstanceId);
        oldServiceInstance = em.find(ServiceInstance.class,oldServiceInstanceId);
        oldNode = em.find(Node.class,oldNodeId);

        Communication oldCommunication = em.find(Communication.class, oldCommunicationId);
        Service oldService = em.find(Service.class, oldServiceId);
        NodeGroup oldNodeGroup = em.find(NodeGroup.class, oldNodeGroupId);

        assertNull(oldCommunicationInstance);
        assertNotNull(oldCommunicationId);
        assertNull(oldServiceInstance);
        assertNotNull(oldServiceId);
        assertNull(oldNode);
        assertNotNull(oldNodeGroupId);

        oldCommunication.getInstances().forEach(instance -> assertFalse(instance.getId().equals(oldCommunicationInstanceId)));
        oldService.getInstances().forEach(instance -> assertFalse(instance.getId().equals(oldServiceInstanceId)));
        oldNodeGroup.getNodes().forEach(instance -> assertFalse(instance.getId().equals(oldNodeId)));
        em.close();
    }



    @Test
    public void updateChangelog(){
        EntityManager em = this.entityManagerFactory.createEntityManager();

        List<ChangelogDto> changelogDtoList = new LinkedList<>();
        NodeDto updateNodeDto = createUpdateNode();
        Node oldNode = em.find(Node.class,updateNodeDto.getId());

        assertNotNull(oldNode);

        ChangelogDto createChangelog = new ChangelogDto();
        createChangelog.setOperation(ChangelogOperation.UPDATE);
        createChangelog.setData(updateNodeDto);

        changelogDtoList.add(createChangelog);

        em.close();
        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        Node updateNode = em.find(Node.class,updateNodeDto.getId());

        assertNotNull(updateNode);

        assertEquals(updateNode.getNodeGroup().getId(), oldNode.getNodeGroup().getId());
        assertEquals(updateNode.getIp(), oldNode.getIp());
        assertFalse(updateNode.getHostname().equals(oldNode.getHostname()));
        assertNotSame(updateNode.getStatus(),oldNode.getStatus());

        em.close();
    }

    @Test
    public void appendTimeSeriesChangelog(){
        EntityManager em = this.entityManagerFactory.createEntityManager();

        List<ChangelogDto> changelogDtoList = new LinkedList<>();

        TimeSeriesDto newTimeSeriesDto = createTimeSeries();

        ChangelogDto appendChangelogDto = new ChangelogDto();
        appendChangelogDto.setOperation(ChangelogOperation.APPEND);
        appendChangelogDto.setData(newTimeSeriesDto);
        changelogDtoList.add(appendChangelogDto);

        Node oldNode = em.find(Node.class,newTimeSeriesDto.getParentId());
        assertNotNull(oldNode);

        em.close();
        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        Node updatedNode = em.find(Node.class,newTimeSeriesDto.getParentId());
        TimeSeries newTimeSeries = em.find(TimeSeries.class,newTimeSeriesDto.getId());

        assertNotNull(newTimeSeries);
        assertNotSame(updatedNode.getTimeSeries(),oldNode.getTimeSeries());
        em.close();
    }

    @Test
    public void appendStatusInfoChangelog(){
        EntityManager em = this.entityManagerFactory.createEntityManager();

        List<ChangelogDto> changelogDtoList = new LinkedList<>();

        StatusInfoDto newStatusInfoDto = createStatusInfo();

        ChangelogDto appendChangelogDto = new ChangelogDto();
        appendChangelogDto.setOperation(ChangelogOperation.APPEND);
        appendChangelogDto.setData(newStatusInfoDto);
        changelogDtoList.add(appendChangelogDto);

        Node oldNode = em.find(Node.class,newStatusInfoDto.getParentId());
        assertNotNull(oldNode);

        em.close();
        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        Node updatedNode = em.find(Node.class,newStatusInfoDto.getParentId());
        StatusInfo statusInfo = em.find(StatusInfo.class,newStatusInfoDto.getId());

        assertNotNull(statusInfo);
        assertEquals(statusInfo.getValue(),newStatusInfoDto.getValue());
        assertNotSame(updatedNode.getStatusInformations(),oldNode.getStatusInformations());
        em.close();

    }




    private NodeDto createNewNode() {
        NodeDto newNode = new NodeDto();
        newNode.setId("test-system123-node-100");
        newNode.setSystemId("system123");
        newNode.setStatus(Status.FAIL);
        newNode.setName("newNode");
        newNode.setIp("10.0.0.2");
        newNode.setHostname("newHost");
        newNode.setNodeGroupId("test-system123-nodeGroup-1");

        return newNode;
    }

    private NodeDto createUpdateNode() {
        NodeDto updateNode = new NodeDto();
        updateNode.setName("WebNode");
        updateNode.setId("test-system123-node-1");
        updateNode.setSystemId("system123");
        updateNode.setStatus(Status.FAIL);
        updateNode.setHostname("updatedHost");
        updateNode.setIp("10.0.0.1");
        updateNode.setNodeGroupId("test-system123-nodeGroup-1");

        return updateNode;
    }

    private TimeSeriesDto createTimeSeries(){
        TimeSeriesDto timeSeriesDto = new TimeSeriesDto();
        timeSeriesDto.setId("test-system123-timeSeries-100");
        timeSeriesDto.setParentId("test-system123-node-1");
        timeSeriesDto.setLabel("TestValues");
        timeSeriesDto.setValueLabel("TestLabel");

        SeriesElementDto seriesElementDto = new SeriesElementDto();
        seriesElementDto.setId("test-system123-seriesElement-100");
        seriesElementDto.setSeriesId("test-system123-timeSeries-100");
        seriesElementDto.setValue(1);
        seriesElementDto.setTimestamp(1468063952600L);

        SeriesElementDto seriesElementDto1 = new SeriesElementDto();
        seriesElementDto1.setId("test-system123-seriesElement-101");
        seriesElementDto1.setSeriesId("test-system123-timeSeries-100");
        seriesElementDto1.setValue(2);
        seriesElementDto1.setTimestamp(1468063952600L);

        SeriesElementDto seriesElementDto2 = new SeriesElementDto();
        seriesElementDto2.setId("test-system123-seriesElement-102");
        seriesElementDto2.setSeriesId("test-system123-timeSeries-100");
        seriesElementDto2.setValue(3);
        seriesElementDto2.setTimestamp(1468063952600L);

        SeriesElementDto seriesElementDto3 = new SeriesElementDto();
        seriesElementDto3.setId("test-system123-seriesElement-103");
        seriesElementDto3.setSeriesId("test-system123-timeSeries-100");
        seriesElementDto3.setValue(2);
        seriesElementDto3.setTimestamp(1468063952600L);

        timeSeriesDto.getSeries().add(seriesElementDto);
        timeSeriesDto.getSeries().add(seriesElementDto1);
        timeSeriesDto.getSeries().add(seriesElementDto2);
        timeSeriesDto.getSeries().add(seriesElementDto3);

        return timeSeriesDto;
    }

    private StatusInfoDto createStatusInfo() {
        StatusInfoDto statusInfoDto = new StatusInfoDto();
        statusInfoDto.setId("test-system123-statusInfo-100");
        statusInfoDto.setParentId("test-system123-node-1");
        statusInfoDto.setKey("newStatusInfo");
        statusInfoDto.setValue("newStatusValue");

        return statusInfoDto;
    }

}