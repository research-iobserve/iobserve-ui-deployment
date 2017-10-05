package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;

import java.util.Collections;
import java.util.Set;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.dataaccessobjects.MeasurableDataTrasferObject;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.AbstractBaseEntity;
import org.iobserve.models.util.AbtractMeasurable;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.TimeSeries;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service implementation should provide basic interaction for a specific model
 * TODO: implement generic findAll/findById (based on other branch)
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class AbstractService<Model extends AbstractBaseEntity, ModelDto extends DataTransportObject> implements IService<ModelDto> {

    protected final EntityManagerFactory entityManagerFactory;

    protected final IEntityToDtoMapper modelToDtoMapper;

    protected final ServiceLocator serviceLocator;

    protected final IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper;

    @Inject
    public AbstractService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper,
                           ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {

        this.entityManagerFactory = entityManagerFactory;
        this.modelToDtoMapper = modelToDtoMapper;
        this.serviceLocator = serviceLocator;
        this.dtoToBasePropertyEntityMapper = dtoToBasePropertyEntityMapper;
    }

    protected final  Class<Model> persistentClass = (Class<Model>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    // TODO: implement generic methods to simplify subclasses
    @Transactional
    public List<ModelDto> findAll(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("Select t from " + persistentClass.getSimpleName() +" t");
        List<ModelDto> resultList = transformModelToDto((List<Model>) query.getResultList());
        entityManager.close();
        return resultList;
    }

    @Transactional
    public ModelDto findById(String id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Model result = entityManager.find(persistentClass,id);
        ModelDto dto = transformModelToDto(result);

        if(result instanceof AbtractMeasurable ){
            final AbtractMeasurable measurable =  (AbtractMeasurable) result;
            final MeasurableDataTrasferObject measurableDto =  (MeasurableDataTrasferObject) dto;
            final Set<TimeSeries> timeSeriesSet = measurable.getTimeSeries();

            //TODO improve efficiency
            //sort timeseries Data by date
            for (TimeSeries timeSeries  : timeSeriesSet) {

                final List<SeriesElement> seriesList = timeSeries.getSeries();
                Collections.sort(seriesList,
                        (series1, series2) -> series1.getTimestamp().compareTo(series2.getTimestamp()));
            }

            measurableDto.setTimeSeries(timeSeriesSet);
            measurableDto.setStatusInformations(measurable.getStatusInformations());


        }


        entityManager.close();
        // enhance dto with measureable data from result
        return dto;
    }

    /**
     * Transforms a model instance to it's corresponding DataTransportObject.
     * Probably uses the generated implementation of {@link IEntityToDtoMapper}
     * @param model
     * @return a lightweight version of the model instance using DataTransportObjects
     *
     * @see IEntityToDtoMapper
     * @see DataTransportObject
     */
    protected abstract ModelDto transformModelToDto(Model model);

    protected abstract Model transformDtoToModel(ModelDto model);

    protected List<ModelDto> transformModelToDto(List<Model> models) {
        return models.stream().map(this::transformModelToDto).collect(Collectors.toList());
    }

    protected List<Model> transformDtoToModel(List<ModelDto> models) {
        return models.stream().map(this::transformDtoToModel).collect(Collectors.toList());
    }
}


