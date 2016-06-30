package org.iobserve.models.mappers;

import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.models.util.TimeSeriesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface provides a blueprint for the transformation between model classes (JPA entities)
 * and their corresponding {@link org.iobserve.models.dataaccessobjects.DataTransportObject}.
 * Transformations are declared using the {@link Mapping} annotation.
 * These transformations are then used between the resource (http/json) layer and the service layer (JPA).
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.dataaccessobjects.DataTransportObject
 * @see <a href="http://mapstruct.org">MapStruct</a>
 */
@Mapper
public interface EntityToDtoMapper { // TODO: ignore measureable

    EntityToDtoMapper INSTANCE = Mappers.getMapper(EntityToDtoMapper.class);

    SystemDto transform(System system);

    @Mapping(source = "source.id", target = "sourceId") // TODO: http://stackoverflow.com/a/32556026/1249001
    @Mapping(source = "target.id", target = "targetId")
    @Mapping(target = "timeSeries", ignore=true)
    @Mapping(target = "statusInformations", ignore=true)
    CommunicationDto transform(Communication communication);

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "target.id", target = "targetId")
    @Mapping(source = "communication.id", target = "communicationId")
    @Mapping(target = "timeSeries", ignore=true)
    @Mapping(target = "statusInformations", ignore=true)
    CommunicationInstanceDto transform(CommunicationInstance communication);

    @Mapping(target = "timeSeries", ignore=true)
    @Mapping(target = "statusInformations", ignore=true)
    NodeGroupDto transform(NodeGroup nodeGroup);

    @Mapping(source = "nodeGroup.id", target = "nodeGroupId")
    @Mapping(target = "timeSeries", ignore=true)
    @Mapping(target = "statusInformations", ignore=true)
    NodeDto transform(Node node);

    @Mapping(target = "timeSeries", ignore=true)
    @Mapping(target = "statusInformations", ignore=true)
    ServiceDto transform(Service node);

    @Mapping(source = "node.id", target = "nodeId")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(target = "timeSeries", ignore=true)
    @Mapping(target = "statusInformations", ignore=true)
    ServiceInstanceDto transform(ServiceInstance node);

    @Mapping(source = "parent", target = "parentId")
    TimeSeriesDto transform(TimeSeries series);

    ChangelogDto transform(Changelog changelog);


}
