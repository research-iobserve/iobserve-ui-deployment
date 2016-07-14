package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.System;
import org.iobserve.models.annotations.ModelClassOfDto;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.System
 */
@ModelClassOfDto(System.class)
@JsonTypeName("system")
public class SystemDto extends DataTransportObject {
    String name;

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
