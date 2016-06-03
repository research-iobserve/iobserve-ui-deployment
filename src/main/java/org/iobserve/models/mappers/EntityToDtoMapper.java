package org.iobserve.models.mappers;

import org.iobserve.models.Communication;
import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.Node;
import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.CommunicationDto;
import org.iobserve.models.dataaccessobjects.CommunicationInstanceDto;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.dataaccessobjects.SystemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Mathis Neumann
 */
@Mapper
public interface EntityToDtoMapper {

    SystemDto systemToSystemDto(System system);

    @Mapping(source = "source.id", target = "sourceId") // TODO: http://stackoverflow.com/a/32556026/1249001
    @Mapping(source = "target.id", target = "targetId")
    CommunicationDto transformCommunication(Communication communication);

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "target.id", target = "targetId")
    CommunicationInstanceDto transformCommunicationInstance(CommunicationInstance communication);

    @Mapping(source = "group.id", target = "groupId")
    NodeDto transformNode(Node node);
}
