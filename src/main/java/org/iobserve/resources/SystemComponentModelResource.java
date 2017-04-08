package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.RevisionedBeanDataTransportObject;

import java.util.List;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public interface SystemComponentModelResource<ModelDto extends RevisionedBeanDataTransportObject> extends ModelResource<ModelDto> {
    List<ModelDto> getAllBySystem(String systemId);
}
