package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;

import org.iobserve.models.Node;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.ServiceInstance;
import org.iobserve.models.Service;

import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.models.util.Status;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class ChangelogServiceTest {

    private static final String testSystem = "system123";


    EntityManagerFactory entityManagerFactory = EntityManagerTestSetup.getEntityManagerFactory();
    EntityToDtoMapper entityToDtoMapper = EntityToDtoMapper.INSTANCE;
    DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper = DtoToBasePropertyEntityMapper.INSTANCE;

    @Mock
    ServiceLocator mockedServiceLocator;

    @Mock
    ChangelogStreamService mockedChangelogStreamService;

    ChangelogService changelogService;

    NodeService nodeService;

    ServiceInstanceService serviceInstanceService;

    @Before
    public void setUp(){
        this.changelogService = new ChangelogService(entityManagerFactory, entityToDtoMapper, mockedServiceLocator, dtoToBasePropertyEntityMapper,
                mockedChangelogStreamService);

        this.nodeService = new NodeService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);

        this.serviceInstanceService = new ServiceInstanceService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);

        when(mockedServiceLocator.getService(ChangelogService.class)).thenReturn(this.changelogService);
        when(mockedServiceLocator.getService(NodeService.class)).thenReturn(this.nodeService);
        when(mockedServiceLocator.getService(ServiceInstanceService.class)).thenReturn(this.serviceInstanceService);
    }


    @Test
    public void createChangelog(){

        List<ChangelogDto> changelogDtoList = new LinkedList<>();
        NodeDto newNodeDto = createNewNode();

        ChangelogDto createChangelog = new ChangelogDto();
        createChangelog.setOperation(ChangelogOperation.CREATE);
        createChangelog.setData(newNodeDto);

        changelogDtoList.add(createChangelog);

        this.changelogService.addChangelogs(this.testSystem,changelogDtoList);

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

        String oldServiceInstanceId = "test-system123-serviceInstance-1";
        EntityManager em = this.entityManagerFactory.createEntityManager();
        ServiceInstance oldServiceInstance = em.find(ServiceInstance.class,oldServiceInstanceId);

        assertNotNull(oldServiceInstance);

        String oldServiceId = oldServiceInstance.getService().getId();

        ServiceInstanceDto oldServiceInstanceDto = this.serviceInstanceService.transformModelToDto(oldServiceInstance);

        ChangelogDto deleteChangelog = new ChangelogDto();
        deleteChangelog.setOperation(ChangelogOperation.DELETE);
        deleteChangelog.setData(oldServiceInstanceDto);

        changelogDtoList.add(deleteChangelog);
        this.changelogService.addChangelogs(this.testSystem,changelogDtoList);

        oldServiceInstance = em.find(ServiceInstance.class,oldServiceInstanceId);
        Service oldService = em.find(Service.class, oldServiceId);

        assertNull(oldServiceInstance);
        assertNotNull(oldServiceId);

        oldService.getInstances().forEach(serviceInstance -> assertFalse(serviceInstance.getId().equals(oldServiceInstanceId)));


        em.close();
    }

    @Test
    public void updateChangelog(){
        EntityManager em = this.entityManagerFactory.createEntityManager();

        em.close();
    }

    @Test
    public void appendChangelog(){
        EntityManager em = this.entityManagerFactory.createEntityManager();

        em.close();
    }


    private NodeDto createNewNode() {
        NodeDto newNode = new NodeDto();
        newNode.setId("test-system123-node-100");
        newNode.setSystemId("system123");
        newNode.setStatus(Status.FAIL);
        newNode.setName("newNode");
        newNode.setIp("10.0.0.2");
        newNode.setHostname("host2");
        newNode.setNodeGroupId("test-system123-nodeGroup-1");

        return newNode;
    }

}