package org.iobserve.resources;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.Size;
import javax.ws.rs.Path;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by cdor on 03.06.16.
 */
public abstract class AbstractEntityResource<T> {
    private EntityManager entityManager;
    protected Class<T> persistentClass;

    @Inject
    public AbstractEntityResource(EntityManager entityManager) {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public T getSingle(String entityId){
        System.out.println(persistentClass.getSimpleName());
        Query query = entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t where id = :systemId")
                .setParameter("id", entityId);
        return  (T) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(String systemId){
        System.out.println(persistentClass.getSimpleName());
        Query query = entityManager.createQuery("Select t from " + persistentClass.getSimpleName() +" t where system_id = :systemId")
                .setParameter("systemId", systemId);
        return (List<T>) query.getResultList();
    }


}
