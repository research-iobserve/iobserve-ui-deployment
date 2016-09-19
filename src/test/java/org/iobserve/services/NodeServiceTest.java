package org.iobserve.services;

import org.iobserve.models.Node;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.services.util.EntityManagerTestSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.*;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeServiceTest {

    EntityManagerFactory entityManagerFactory = EntityManagerTestSetup.getEntityManagerFactory();

    @InjectMocks
    NodeService nodeService;

    @Mock
    Node node;
    @Mock
    NodeDto nodeDto;

    @Before
    public void setUp(){
        nodeService = new NodeService();
    }


    @Test
    public void transformModelToDto() throws Exception {
        EntityManager em = entityManagerFactory.createEntityManager();
//        NodeDto transformedNode = nodeService.transformModelToDto(node);
//        assertEquals(transformedNode, nodeDto);

    }
//
//    @Test
//    public void transformDtoToModel() throws Exception {
//        Node transformedNode = nodeService.transformDtoToModel(nodeDto);
//        assertEquals(transformedNode, node);
//    }

}