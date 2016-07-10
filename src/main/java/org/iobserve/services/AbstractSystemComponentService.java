package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.util.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class AbstractSystemComponentService<Model extends BaseEntity, ModelDto extends DataTransportObject> extends AbstractService<Model, ModelDto> {

    public  List<ModelDto> findAllBySystem(String systemId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t where systemId = :systemId")
                .setParameter("systemId", systemId);
        return transformModelToDto((List<Model>) query.getResultList());
    }
}
