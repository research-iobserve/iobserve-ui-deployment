package org.iobserve.models.dataaccessobjects;

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
public abstract class DataTransportObject {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
