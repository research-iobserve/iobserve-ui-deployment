package org.iobserve.injection;

import org.glassfish.hk2.api.Factory;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class DtoToBasePropertyEntityMapperFactory implements Factory<DtoToBasePropertyEntityMapper> {


    @Override
    public DtoToBasePropertyEntityMapper provide() {
        return DtoToBasePropertyEntityMapper.INSTANCE;
    }

    @Override
    public void dispose(DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {

    }
}
