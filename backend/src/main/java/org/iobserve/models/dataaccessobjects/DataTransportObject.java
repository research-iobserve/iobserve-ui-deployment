package org.iobserve.models.dataaccessobjects;

import org.iobserve.models.util.StatusInfo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * DataTransportObjects represent a lightweight version of a corresponding model instance. By
 * convention these should have the same name as their model appended with "Dto". Instances will be
 * serialized using JSON which not not contain nested objects. DataTransportObjects are most
 * commonly required to avoid automatic evaluation of lazy loaded JPA references.
 *
 * The transformation functions between model instances and their corresponding DataTransportObject
 * are generated using MapStruct and {@link org.iobserve.models.mappers.EntityToDtoMapper}.
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.mappers.EntityToDtoMapper
 */

@JsonTypeName("baseEntity")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = MeasurableDataTrasferObject.class, name = "measurable"),
        @JsonSubTypes.Type(value = ServiceDto.class, name = "service"),
        @JsonSubTypes.Type(value = ServiceInstanceDto.class, name = "serviceInstance"),
        @JsonSubTypes.Type(value = NodeDto.class, name = "node"),
        @JsonSubTypes.Type(value = NodeGroupDto.class, name = "nodeGroup"),
        @JsonSubTypes.Type(value = UserGroupDto.class, name = "userGroup"),
        // @JsonSubTypes.Type(value = UserGroupGroupDto.class, name = "userGroupGroup"),
        @JsonSubTypes.Type(value = ChangelogDto.class, name = "changelog"),
        @JsonSubTypes.Type(value = CommunicationDto.class, name = "communication"),
        @JsonSubTypes.Type(value = CommunicationInstanceDto.class, name = "communicationInstance"),
        @JsonSubTypes.Type(value = StatusInfoDto.class, name = "statusInfo"),
        @JsonSubTypes.Type(value = TimeSeriesDto.class, name = "timeSeries"),
        @JsonSubTypes.Type(value = SeriesElementDto.class, name = "seriesElement"),
        @JsonSubTypes.Type(value = StatusInfo.class, name = "statusInfo") })
public abstract class DataTransportObject {
    private String id;

    public DataTransportObject() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
