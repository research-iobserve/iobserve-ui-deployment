package org.iobserve.services;

import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.SystemDto;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Mathis Neumann
 */
public class SystemService extends AbstractService<System,SystemDto> {
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
}
