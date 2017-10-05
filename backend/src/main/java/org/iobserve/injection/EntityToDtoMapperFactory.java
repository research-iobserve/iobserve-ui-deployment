package org.iobserve.injection;

import org.glassfish.hk2.api.Factory;
import org.iobserve.models.mappers.IEntityToDtoMapper;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class EntityToDtoMapperFactory implements Factory<IEntityToDtoMapper> {

    @Override
    public IEntityToDtoMapper provide() {
        return IEntityToDtoMapper.INSTANCE;
    }

    @Override
    public void dispose(IEntityToDtoMapper instance) {
        // nothing to do
    }
}
