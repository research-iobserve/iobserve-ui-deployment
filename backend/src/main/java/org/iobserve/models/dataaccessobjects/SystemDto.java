package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.ModelType;
import org.iobserve.models.System;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.SystemService;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.System
 */
@ModelClassOfDto(value = System.class, service = SystemService.class)
@JsonTypeName(ModelType.TypeName.SYSTEM)
public class SystemDto extends DataTransportObject {
    private String name;

    public SystemDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
