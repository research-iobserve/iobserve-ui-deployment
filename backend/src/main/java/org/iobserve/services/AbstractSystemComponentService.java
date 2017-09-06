package org.iobserve.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;
import org.iobserve.models.util.BaseEntity;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class AbstractSystemComponentService<Model extends BaseEntity, ModelDto extends DataTransportObject>
        extends AbstractService<Model, ModelDto> {

    @Inject
    public AbstractSystemComponentService(final EntityManagerFactory entityManagerFactory,
            final EntityToDtoMapper modelToDtoMapper, final ServiceLocator serviceLocator,
            final DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    public List<ModelDto> findAllBySystem(final String systemId) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final Query query = entityManager
                .createQuery("Select t from " + this.persistentClass.getSimpleName() + " t where systemId = :systemId")
                .setParameter("systemId", systemId);
        final List<Model> result = query.getResultList();
        entityManager.close();
        return this.transformModelToDto(result);
    }
}
