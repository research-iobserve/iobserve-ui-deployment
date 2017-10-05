package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.dataaccessobjects.NodeGroupDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * Created by cdor on 13.06.16.
 */
public class NodeGroupService extends AbstractSystemComponentService<NodeGroup,NodeGroupDto> {

    @Inject
    public NodeGroupService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected NodeGroupDto transformModelToDto(NodeGroup nodeGroup) {
        return this.modelToDtoMapper.transform(nodeGroup);
    }

    @Override
    protected NodeGroup transformDtoToModel(NodeGroupDto nodeGroupDto) {
        final NodeGroup nodeGroup = dtoToBasePropertyEntityMapper.transform(nodeGroupDto);

        return nodeGroup;
    }
}
