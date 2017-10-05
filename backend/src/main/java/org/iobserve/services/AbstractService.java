/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.iobserve.services;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.dataaccessobjects.MeasurableDataTrasferObject;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.AbstractBaseEntity;
import org.iobserve.models.util.AbstractMeasurable;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.TimeSeries;

/**
 * A service implementation should provide basic interaction for a specific model.
 *
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class AbstractService<Model extends AbstractBaseEntity, ModelDto extends DataTransportObject>
        implements IService<ModelDto> {

    protected final EntityManagerFactory entityManagerFactory;

    protected final IEntityToDtoMapper modelToDtoMapper;

    protected final ServiceLocator serviceLocator;

    protected final IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper;

    @Inject
    public AbstractService(final EntityManagerFactory entityManagerFactory, final IEntityToDtoMapper modelToDtoMapper,
            final ServiceLocator serviceLocator, final IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {

        this.entityManagerFactory = entityManagerFactory;
        this.modelToDtoMapper = modelToDtoMapper;
        this.serviceLocator = serviceLocator;
        this.dtoToBasePropertyEntityMapper = dtoToBasePropertyEntityMapper;
    }

    @SuppressWarnings("unchecked")
    protected final Class<Model> persistentClass = (Class<Model>) ((ParameterizedType) this.getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    @Transactional
    public List<ModelDto> findAll() {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        final Query query = entityManager.createQuery("Select t from " + this.persistentClass.getSimpleName() + " t");
        @SuppressWarnings("unchecked")
        final List<ModelDto> resultList = this.transformModelToDto(query.getResultList());
        entityManager.close();
        return resultList;
    }

    @Override
    @Transactional
    public ModelDto findById(final String id) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final Model result = entityManager.find(this.persistentClass, id);
        final ModelDto dto = this.transformModelToDto(result);

        if (result instanceof AbstractMeasurable) {
            final AbstractMeasurable measurable = (AbstractMeasurable) result;
            final MeasurableDataTrasferObject measurableDto = (MeasurableDataTrasferObject) dto;
            final Set<TimeSeries> timeSeriesSet = measurable.getTimeSeries();

            // sort timeseries Data by date
            for (final TimeSeries timeSeries : timeSeriesSet) {

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
     * Transforms a model instance to it's corresponding DataTransportObject. Probably uses the
     * generated implementation of {@link IEntityToDtoMapper}
     *
     * @param model
     * @return a lightweight version of the model instance using DataTransportObjects
     *
     * @see IEntityToDtoMapper
     * @see DataTransportObject
     */
    protected abstract ModelDto transformModelToDto(Model model);

    protected abstract Model transformDtoToModel(ModelDto model);

    protected List<ModelDto> transformModelToDto(final List<Model> models) {
        return models.stream().map(this::transformModelToDto).collect(Collectors.toList());
    }

    protected List<Model> transformDtoToModel(final List<ModelDto> models) {
        return models.stream().map(this::transformDtoToModel).collect(Collectors.toList());
    }
}
