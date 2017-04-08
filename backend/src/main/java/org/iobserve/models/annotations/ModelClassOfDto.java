package org.iobserve.models.annotations;

import org.iobserve.models.util.BaseEntity;
import org.iobserve.services.AbstractService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelClassOfDto {
    Class<? extends BaseEntity> value();
    Class<? extends AbstractService> service();
}
