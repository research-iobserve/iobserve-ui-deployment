package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.SystemIdDataTransportObject;

import java.util.List;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public interface SystemComponentModelResource<ModelDto extends SystemIdDataTransportObject> extends ModelResource<ModelDto> {
    List<ModelDto> getBySystem(String systemId);
}
