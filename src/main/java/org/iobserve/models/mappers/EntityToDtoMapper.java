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
import org.mapstruct.factory.Mappers;

/**
 * This interface provides a blueprint for the transformation between model classes (JPA entities)
 * and their corresponding {@link org.iobserve.models.dataaccessobjects.DataTransportObject}.
 * Transformations are declared using the {@link Mapping} annotation.
 * These transformations are then used between the resource (http/json) layer and the service layer (JPA).
 *
 * @author Mathis Neumann
 * @see org.iobserve.models.dataaccessobjects.DataTransportObject
 * @see http://mapstruct.org
 */
@Mapper
public interface EntityToDtoMapper {

    EntityToDtoMapper INSTANCE = Mappers.getMapper(EntityToDtoMapper.class);
    
    SystemDto transform(System system);

    @Mapping(source = "source.id", target = "sourceId") // TODO: http://stackoverflow.com/a/32556026/1249001
    @Mapping(source = "target.id", target = "targetId")
    CommunicationDto transform(Communication communication);

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "target.id", target = "targetId")
    CommunicationInstanceDto transform(CommunicationInstance communication);

    @Mapping(source = "group.id", target = "groupId")
    NodeDto transform(Node node);

}
