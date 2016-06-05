package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.util.BaseEntity;

import java.util.List;

/**
 * @author Mathis Neumann
 */
public abstract class AbstractSystemComponentService<Model extends BaseEntity, ModelDto extends DataTransportObject> extends AbstractService<Model, ModelDto> {
    public abstract List<ModelDto> findAllBySystem(String systemId);
}
