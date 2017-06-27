package org.iobserve.services;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolationException;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.SystemDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class SystemService extends AbstractService<System, SystemDto> {

    @Inject
    public SystemService(final EntityManagerFactory entityManagerFactory, final EntityToDtoMapper modelToDtoMapper,
            final ServiceLocator serviceLocator, final DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    // @Override
    // public List<SystemDto> findAll() {
    // Query query = entityManager.createQuery("SELECT e FROM System e");
    // return this.transformModelToDto((List<System>) query.getResultList());
    // }

    // @Override
    // public SystemDto findById(String id) {
    // return modelToDtoMapper.transform(entityManager.find(System.class, id));
    // }

    @Override
    protected SystemDto transformModelToDto(final System system) {
        return this.modelToDtoMapper.transform(system);
    }

    @Override
    protected System transformDtoToModel(final SystemDto systemDto) {
        final System system = this.dtoToBasePropertyEntityMapper.transform(systemDto);

        return system;
    }

    public synchronized void createSystem(final SystemDto system) throws ConstraintViolationException {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final System oldSystem = entityManager.find(System.class, system.getId());
        if (oldSystem != null) {
            // TODO error handling: new system with id already used by another system
        }
        final System createdSystem = new System();
        createdSystem.setId(system.getId());
        createdSystem.setName(system.getName());

        final EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(createdSystem); // saves all thanks to cascading
        tx.commit();
        entityManager.close();

    }
}
