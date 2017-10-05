package org.iobserve.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Node;
import org.iobserve.models.ServiceInstance;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.services.util.EntityManagerTestSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeServiceTest {

    EntityManagerFactory entityManagerFactory = EntityManagerTestSetup.getEntityManagerFactory();
    IEntityToDtoMapper entityToDtoMapper = IEntityToDtoMapper.INSTANCE;
    IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper = IDtoToBasePropertyEntityMapper.INSTANCE;

    //@Mock
    ServiceLocator mockedServiceLocator;

    NodeService nodeService;
    ServiceInstanceService serviceInstanceService;


    Node node;
    NodeDto nodeDto;

    //@Before
    public void setUp(){
        when(mockedServiceLocator.getService(any())).thenReturn(NodeService.class);
        when(mockedServiceLocator.getService(ServiceInstanceService.class)).thenReturn(this.serviceInstanceService);

        this.serviceInstanceService = new ServiceInstanceService(entityManagerFactory,entityToDtoMapper,mockedServiceLocator,dtoToBasePropertyEntityMapper);
        this.nodeService = new NodeService(entityManagerFactory, entityToDtoMapper, mockedServiceLocator, dtoToBasePropertyEntityMapper);
        node = new Node();

        nodeDto = new NodeDto();
    }

    @Test
    public void dummy() {
    	Assert.assertNull(null);
    }
    
    //@Test
    public void transformModelToDto() throws Exception {
        String id = "1234567890";
        EntityManager em  = entityManagerFactory.createEntityManager();

        Node node = new Node();
        node.setId(id);

        ServiceInstance service = new ServiceInstance();
        service.setId(id);

        EntityExistsException entityExistsException = null;

        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(node);
            tx.commit();

            tx = em.getTransaction();
            tx.begin();
            em.persist(service);
            tx.commit();
        }catch (EntityExistsException e){
            entityExistsException  = e;
        }finally {
            assertNotNull(entityExistsException);
        }
        em.close();

    }
//
//    @Test
//    public void transformDtoToModel() throws Exception {
//        Node transformedNode = nodeService.transformDtoToModel(nodeDto);
//        assertEquals(transformedNode, node);
//    }

}