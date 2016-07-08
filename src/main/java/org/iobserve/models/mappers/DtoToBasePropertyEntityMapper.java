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
 * and their corresponding {@link DataTransportObject}.
 * Transformations are declared using the {@link Mapping} annotation.
 * These transformations are then used between the resource (http/json) layer and the service layer (JPA).
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see DataTransportObject
 * @see <a href="http://mapstruct.org">MapStruct</a>
 */
@Mapper
public interface DtoToBasePropertyEntityMapper  { // TODO: ignore measureable

    //TODO Maybe ignre for ids

    DtoToBasePropertyEntityMapper INSTANCE = Mappers.getMapper(DtoToBasePropertyEntityMapper.class);
    
    System transform(SystemDto systemDto);

    Communication transform(CommunicationDto communicationDto);

    CommunicationInstance transform(CommunicationInstanceDto communicationDto);

    NodeGroup transform(NodeGroupDto nodeGroupDto);

    Node transform(NodeDto nodeDto);

    Service transform(ServiceDto nodeDto);

    ServiceInstance transform(ServiceInstanceDto nodeDto);

    //Changelog transform(ChangelogDto changelogDto);

    @Mapping(source = "parentId", target = "parent")
    TimeSeries transform(TimeSeriesDto seriesDto);


}
