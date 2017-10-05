package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.SystemDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class SystemService extends AbstractService<System,SystemDto> {

    @Inject
    public SystemService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
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
