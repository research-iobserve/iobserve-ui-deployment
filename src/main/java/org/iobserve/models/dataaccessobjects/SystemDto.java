package org.iobserve.models.dataaccessobjects;

import org.iobserve.models.System;
import org.iobserve.models.annotations.ModelClassOfDto;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.System
 */
@ModelClassOfDto(System.class)
@XmlType(name = "system")
public class SystemDto extends DataTransportObject {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
