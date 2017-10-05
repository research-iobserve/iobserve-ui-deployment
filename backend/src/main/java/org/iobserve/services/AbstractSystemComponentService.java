package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.AbstractBaseEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class AbstractSystemComponentService<Model extends AbstractBaseEntity, ModelDto extends DataTransportObject> extends AbstractService<Model, ModelDto> {

    @Inject
    public AbstractSystemComponentService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    public  List<ModelDto> findAllBySystem(String systemId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t where systemId = :systemId")
                .setParameter("systemId", systemId);
        List<Model> result = query.getResultList();
        entityManager.close();
        return transformModelToDto(result);
    }
}
