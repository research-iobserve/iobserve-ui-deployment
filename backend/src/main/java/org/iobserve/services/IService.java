package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.DataTransportObject;

import java.util.List;

/**
 * Base interface for all services.
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public interface IService<ModelDto extends DataTransportObject> {
    List<ModelDto> findAll();
    ModelDto findById(String id);
}
