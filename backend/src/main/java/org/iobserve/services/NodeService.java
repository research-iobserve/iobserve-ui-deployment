package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Node;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by cdor on 13.06.16.
 */
public class NodeService extends AbstractSystemComponentService<Node,NodeDto> {

    @Inject
    public NodeService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected NodeDto transformModelToDto(Node node) {
        return this.modelToDtoMapper.transform(node);
    }

    @Override
    protected Node transformDtoToModel(NodeDto nodeDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Node node = dtoToBasePropertyEntityMapper.transform(nodeDto);

        if(nodeDto.getNodeGroupId() != null){
            final NodeGroup nodeGroup = entityManager.find(NodeGroup.class, nodeDto.getNodeGroupId());
            node.setNodeGroup(nodeGroup);
        }


        entityManager.close();

        return node;
    }
}
