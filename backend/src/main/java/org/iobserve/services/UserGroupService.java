package org.iobserve.services;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.UserGroup;
import org.iobserve.models.dataaccessobjects.UserGroupDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

public class UserGroupService extends AbstractSystemComponentService<UserGroup, UserGroupDto> {

    @Inject
    public UserGroupService(final EntityManagerFactory entityManagerFactory, final EntityToDtoMapper modelToDtoMapper,
            final ServiceLocator serviceLocator, final DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected UserGroupDto transformModelToDto(final UserGroup userGroup) {
        return this.modelToDtoMapper.transform(userGroup);
    }

    @Override
    protected UserGroup transformDtoToModel(final UserGroupDto userGroupDto) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final UserGroup userGroup = this.dtoToBasePropertyEntityMapper.transform(userGroupDto);

        entityManager.close();

        return userGroup;
    }
}
