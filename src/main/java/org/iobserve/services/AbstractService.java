package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.mappers.EntityToDtoMapper;
import org.iobserve.models.util.BaseEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service implementation should provide basic interaction for a specific model
 * TODO: implement generic findAll/findById (based on other branch)
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class AbstractService<Model extends BaseEntity, ModelDto extends DataTransportObject> implements Service<ModelDto> {

    @Inject
    protected EntityManager entityManager;

    @Inject
    protected EntityToDtoMapper modelToDtoMapper;

    protected final  Class<Model> persistentClass = (Class<Model>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    // TODO: implement generic methods to simplify subclasses
    public List<ModelDto> findAll(){

        Query query = entityManager.createQuery("Select t from " + persistentClass.getSimpleName() +" t");
        return transformModelToDto((List<Model>) query.getResultList());
    }

    public ModelDto findById(String id){
        Model result = entityManager.find(persistentClass,id);
        ModelDto dto = transformModelToDto(result);
        // enhance dto with measureable data from result
        return dto;
    }

    /**
     * Transforms a model instance to it's corresponding DataTransportObject.
     * Probably uses the generated implementation of {@link EntityToDtoMapper}
     * @param model
     * @return a lightweight version of the model instance using DataTransportObjects
     *
     * @see EntityToDtoMapper
     * @see DataTransportObject
     */
    protected abstract ModelDto transformModelToDto(Model model);

    protected List<ModelDto> transformModelToDto(List<Model> models) {
        return models.stream().map(this::transformModelToDto).collect(Collectors.toList());
    }
}
