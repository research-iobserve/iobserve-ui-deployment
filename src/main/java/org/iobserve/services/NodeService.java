package org.iobserve.services;

import org.iobserve.models.Node;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.dataaccessobjects.NodeDto;

import javax.persistence.EntityManager;

/**
 * Created by cdor on 13.06.16.
 */
public class NodeService extends AbstractSystemComponentService<Node,NodeDto> {

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
