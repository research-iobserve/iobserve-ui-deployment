package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.iobserve.models.Changelog;
import org.iobserve.models.ServiceInstance;

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
@XmlType(name = "baseEntity")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
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
