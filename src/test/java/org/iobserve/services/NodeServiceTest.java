package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Node;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;
import org.iobserve.services.util.EntityManagerTestSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeServiceTest {

    EntityManagerFactory entityManagerFactory = EntityManagerTestSetup.getEntityManagerFactory();
    EntityToDtoMapper entityToDtoMapper = EntityToDtoMapper.INSTANCE;
    DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper = DtoToBasePropertyEntityMapper.INSTANCE;

    @Mock
    ServiceLocator mockedServiceLocator;

    NodeService nodeService;


    Node node;
    NodeDto nodeDto;

    @Before
    public void setUp(){
        when(mockedServiceLocator.getService(any())).thenReturn(NodeService.class);

        nodeService = new NodeService(entityManagerFactory, entityToDtoMapper, mockedServiceLocator, dtoToBasePropertyEntityMapper);
        node = new Node();
        nodeDto = new NodeDto();
    }

    @Test
    public void transformModelToDto() throws Exception {
        NodeDto transformedNode = nodeService.transformModelToDto(node);


    }
//
//    @Test
//    public void transformDtoToModel() throws Exception {
//        Node transformedNode = nodeService.transformDtoToModel(nodeDto);
//        assertEquals(transformedNode, node);
//    }

}