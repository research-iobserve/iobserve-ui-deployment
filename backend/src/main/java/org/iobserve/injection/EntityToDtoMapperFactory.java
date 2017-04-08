package org.iobserve.injection;

import org.glassfish.hk2.api.Factory;
import org.iobserve.models.mappers.EntityToDtoMapper;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class EntityToDtoMapperFactory implements Factory<EntityToDtoMapper> {

    @Override
    public EntityToDtoMapper provide() {
        return EntityToDtoMapper.INSTANCE;
    }

    @Override
    public void dispose(EntityToDtoMapper instance) {
        // nothing to do
    }
}
