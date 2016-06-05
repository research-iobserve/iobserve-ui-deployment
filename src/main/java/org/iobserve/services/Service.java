package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.DataTransportObject;

import java.util.List;

/**
 * Base interface for all services.
 *
 * @author Mathis Neumann
 */
public interface Service<ModelDto extends DataTransportObject> {
    List<ModelDto> findAll();
    ModelDto findById(String id);
}
