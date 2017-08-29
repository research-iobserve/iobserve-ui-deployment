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
import org.iobserve.models.dataaccessobjects.DataTransportObject;
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
 * and their corresponding {@link DataTransportObject}. Transformations are declared using the
 * {@link Mapping} annotation. These transformations are then used between the resource (http/json)
 * layer and the service layer (JPA).
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see DataTransportObject
 * @see <a href="http://mapstruct.org">MapStruct</a>
 */
@Mapper
public interface DtoToBasePropertyEntityMapper {

    // TODO: ignore measureable

    // TODO Maybe ignore for ids

    DtoToBasePropertyEntityMapper INSTANCE = Mappers.getMapper(DtoToBasePropertyEntityMapper.class);

    System transform(SystemDto systemDto);

    Communication transform(CommunicationDto communicationDto);

    CommunicationInstance transform(CommunicationInstanceDto communicationDto);

    NodeGroup transform(NodeGroupDto nodeGroupDto);

    Node transform(NodeDto nodeDto);

    UserGroup transform(UserGroupDto userGroupDto);

    Service transform(ServiceDto nodeDto);

    ServiceInstance transform(ServiceInstanceDto nodeDto);

    @Mapping(target = "data", ignore = true)
    Changelog transform(ChangelogDto changelogDto);

    TimeSeries transform(TimeSeriesDto seriesDto);

    StatusInfo transform(StatusInfoDto statusInfo);

    SeriesElement transform(SeriesElementDto seriesElementDto);

    Revision transform(RevisionDto revisionDto);

}
