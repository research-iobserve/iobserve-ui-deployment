package org.iobserve.services;

import org.iobserve.models.Node;
import org.iobserve.models.dataaccessobjects.NodeDto;

/**
 * Created by cdor on 13.06.16.
 */
public class NodeService extends AbstractSystemComponentService<Node,NodeDto> {

    @Override
    protected NodeDto transformModelToDto(Node node) {
        return this.modelToDtoMapper.transform(node);
    }
}
