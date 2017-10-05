package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.RevisionedBeanDataTransportObject;

import java.util.List;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public interface ISystemComponentModelResource<ModelDto extends RevisionedBeanDataTransportObject> extends IModelResource<ModelDto> {
    List<ModelDto> getAllBySystem(String systemId);
}
