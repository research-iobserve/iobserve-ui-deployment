package org.iobserve.models.mappers;

import org.iobserve.models.Changelog;
import org.iobserve.models.Communication;
import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.Node;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.Service;
import org.iobserve.models.ServiceInstance;
import org.iobserve.models.System;
import org.iobserve.models.UserGroup;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.dataaccessobjects.CommunicationDto;
import org.iobserve.models.dataaccessobjects.CommunicationInstanceDto;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.dataaccessobjects.NodeGroupDto;
import org.iobserve.models.dataaccessobjects.RevisionDto;
import org.iobserve.models.dataaccessobjects.SeriesElementDto;
import org.iobserve.models.dataaccessobjects.ServiceDto;
import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;
import org.iobserve.models.dataaccessobjects.StatusInfoDto;
import org.iobserve.models.dataaccessobjects.SystemDto;
import org.iobserve.models.dataaccessobjects.TimeSeriesDto;
import org.iobserve.models.dataaccessobjects.UserGroupDto;
import org.iobserve.models.util.Revision;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface provides a blueprint for the transformation between model classes (JPA entities)
 * and their corresponding {@link org.iobserve.models.dataaccessobjects.DataTransportObject}.
 * Transformations are declared using the {@link Mapping} annotation. These transformations are then
 * used between the resource (http/json) layer and the service layer (JPA).
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.dataaccessobjects.DataTransportObject
 * @see <a href="http://mapstruct.org">MapStruct</a>
 */
@Mapper
public interface EntityToDtoMapper { // TODO: ignore measureable

    EntityToDtoMapper INSTANCE = Mappers.getMapper(EntityToDtoMapper.class);

    SystemDto transform(System system);

    @Mapping(source = "source.id", target = "sourceId") // TODO:
                                                        // http://stackoverflow.com/a/32556026/1249001
    @Mapping(source = "target.id", target = "targetId")
    @Mapping(target = "timeSeries", ignore = true)
    @Mapping(target = "statusInformations", ignore = true)
    CommunicationDto transform(Communication communication);

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "target.id", target = "targetId")
    @Mapping(source = "communication.id", target = "communicationId")
    @Mapping(target = "timeSeries", ignore = true)
    @Mapping(target = "statusInformations", ignore = true)
    CommunicationInstanceDto transform(CommunicationInstance communication);

    @Mapping(target = "timeSeries", ignore = true)
    @Mapping(target = "statusInformations", ignore = true)
    NodeGroupDto transform(NodeGroup nodeGroup);

    @Mapping(source = "nodeGroup.id", target = "nodeGroupId")
    @Mapping(target = "timeSeries", ignore = true)
    @Mapping(target = "statusInformations", ignore = true)
    NodeDto transform(Node node);

    // TODO UserGroup
    // @Mapping(source = "userGroupGroup.id", target = "userGroupGroupId")
    @Mapping(source = "services", target = "services")
    @Mapping(target = "timeSeries", ignore = true)
    @Mapping(target = "statusInformations", ignore = true)
    UserGroupDto transform(UserGroup userGroup);

    @Mapping(target = "timeSeries", ignore = true)
    @Mapping(target = "statusInformations", ignore = true)
    ServiceDto transform(Service node);

    @Mapping(source = "node.id", target = "nodeId")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(target = "timeSeries", ignore = true)
    @Mapping(target = "statusInformations", ignore = true)
    ServiceInstanceDto transform(ServiceInstance node);

    @Mapping(target = "data", ignore = true)
    ChangelogDto transform(Changelog changelog);

    TimeSeriesDto transform(TimeSeries series);

    StatusInfoDto transform(StatusInfo statusInfo);

    SeriesElementDto transform(SeriesElement seriesElement);

    RevisionDto transform(Revision revision);

}
