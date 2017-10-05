package org.iobserve.injection;

import org.glassfish.hk2.api.Factory;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class DtoToBasePropertyEntityMapperFactory implements Factory<IDtoToBasePropertyEntityMapper> {


    @Override
    public IDtoToBasePropertyEntityMapper provide() {
        return IDtoToBasePropertyEntityMapper.INSTANCE;
    }

    @Override
    public void dispose(IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {

    }
}
