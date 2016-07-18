package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.Service;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.util.BaseEntity;

import javax.xml.bind.annotation.XmlType;

/**
 * DataTransportObjects represent a lightweight version of a corresponding model instance.
 * By convention these should have the same name as their model appended with "Dto".
 * Instances will be serialized using JSON which not not contain nested objects.
 * DataTransportObjects are most commonly required to avoid automatic evaluation
 * of lazy loaded JPA references.
 *
 * The transformation functions between model instances and their corresponding DataTransportObject
 * are generated using MapStruct and {@link org.iobserve.models.mappers.EntityToDtoMapper}.
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.mappers.EntityToDtoMapper
 */

@JsonTypeName("baseEntity")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MeasurableDataTrasferObject.class, name = "measurable"),
        @JsonSubTypes.Type(value = ServiceDto.class, name = "service"),
        @JsonSubTypes.Type(value = ServiceInstanceDto.class, name = "serviceInstance"),
        @JsonSubTypes.Type(value = NodeDto.class, name = "node"),
        @JsonSubTypes.Type(value = NodeGroupDto.class, name = "nodeGroup"),
        @JsonSubTypes.Type(value = ChangelogDto.class, name = "changelog"),
        @JsonSubTypes.Type(value = CommunicationDto.class, name = "communication"),
        @JsonSubTypes.Type(value = CommunicationInstanceDto.class, name = "communicationInstance"),
        @JsonSubTypes.Type(value = TimeSeriesDto.class, name = "timeSeries"),
        @JsonSubTypes.Type(value = SeriesElementDto.class, name = "seriesElement") })
public abstract class DataTransportObject {
    String id;

    public DataTransportObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
