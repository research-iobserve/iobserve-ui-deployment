package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.SystemDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class SystemService extends AbstractService<System,SystemDto> {

    @Inject
    public SystemService(EntityManagerFactory entityManagerFactory, EntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    //    @Override
//    public List<SystemDto> findAll() {
//        Query query = entityManager.createQuery("SELECT e FROM System e");
//        return this.transformModelToDto((List<System>) query.getResultList());
//    }

//    @Override
//    public SystemDto findById(String id) {
//        return modelToDtoMapper.transform(entityManager.find(System.class, id));
//    }

    @Override
    protected SystemDto transformModelToDto(System system) {
        return this.modelToDtoMapper.transform(system);
    }

    @Override
    protected System transformDtoToModel(SystemDto systemDto) {
        final System system = dtoToBasePropertyEntityMapper.transform(systemDto);

        return system;
    }
}
