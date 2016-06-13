package org.iobserve.services;

import org.iobserve.models.NodeGroup;
import org.iobserve.models.dataaccessobjects.NodeGroupDto;

/**
 * Created by cdor on 13.06.16.
 */
public class NodeGroupService extends AbstractSystemComponentService<NodeGroup,NodeGroupDto> {

    @Override
    protected NodeGroupDto transformModelToDto(NodeGroup nodeGroup) {
        return this.modelToDtoMapper.transform(nodeGroup);
    }
}
