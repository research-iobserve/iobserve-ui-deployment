package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;

import org.iobserve.models.*;

import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.models.util.Status;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.services.util.EntityManagerTestSetup;
import org.iobserve.services.websocket.ChangelogStreamService;
import org.junit.Before;
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


    private final EntityManagerFactory entityManagerFactory = EntityManagerTestSetup.getEntityManagerFactory();
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

    @Before
    public void setUp(){
        this.changelogService = new ChangelogService(entityManagerFactory, entityToDtoMapper, mockedServiceLocator, dtoToBasePropertyEntityMapper,
                mockedChangelogStreamService);

        this.nodeService = new NodeService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.serviceInstanceService = new ServiceInstanceService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.communicationInstanceService = new CommunicationInstanceService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.timeSeriesService = new TimeSeriesService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.seriesElementService = new SeriesElementService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);

        when(mockedServiceLocator.getService(ChangelogService.class)).thenReturn(this.changelogService);
        when(mockedServiceLocator.getService(NodeService.class)).thenReturn(this.nodeService);
        when(mockedServiceLocator.getService(ServiceInstanceService.class)).thenReturn(this.serviceInstanceService);
        when(mockedServiceLocator.getService(CommunicationInstanceService.class)).thenReturn(this.communicationInstanceService);
        when(mockedServiceLocator.getService(TimeSeriesService.class)).thenReturn(this.timeSeriesService);
        when(mockedServiceLocator.getService(SeriesElementService.class)).thenReturn(this.seriesElementService);
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

    @Test
    public void deleteChangelog(){
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
    public void appendChangelog(){
        EntityManager em = this.entityManagerFactory.createEntityManager();

        List<ChangelogDto> changelogDtoList = new LinkedList<>();

        TimeSeriesDto newTimeSeriesDto = createTimeSeries();

        ChangelogDto appendChangelogDto = new ChangelogDto();
        appendChangelogDto.setOperation(ChangelogOperation.APPEND);
        appendChangelogDto.setData(newTimeSeriesDto);
        changelogDtoList.add(appendChangelogDto);

        Node oldNode = em.find(Node.class,newTimeSeriesDto.getParentId());


        em.close();
        this.changelogService.addChangelogs(testSystem,changelogDtoList);

        em = this.entityManagerFactory.createEntityManager();

        Node updatedNode = em.find(Node.class,newTimeSeriesDto.getParentId());
        TimeSeries newTimeSeries = em.find(TimeSeries.class,newTimeSeriesDto.getId());

        assertNotNull(newTimeSeries);
        assertNotSame(updatedNode.getTimeSeries(),oldNode.getTimeSeries());


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

}