package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.DataTransportObject;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public interface IModelResource<ModelDto extends DataTransportObject> {
    ModelDto getById(String id);
}
