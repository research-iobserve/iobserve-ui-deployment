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
package org.iobserve.resources;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by cdor on 03.06.16.
 */
public abstract class AbstractEntityResource<T> {
    private final EntityManager entityManager;
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    @Inject
    public AbstractEntityResource(final EntityManager entityManager) {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public T getSingle(final String entityId) {
        System.out.println(this.persistentClass.getSimpleName());
        final Query query = this.entityManager
                .createQuery("Select t from " + this.persistentClass.getSimpleName() + " t where id = :systemId")
                .setParameter("id", entityId);
        return (T) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(final String systemId) {
        System.out.println(this.persistentClass.getSimpleName());
        final Query query = this.entityManager
                .createQuery("Select t from " + this.persistentClass.getSimpleName() + " t where system_id = :systemId")
                .setParameter("systemId", systemId);
        return query.getResultList();
    }

}
