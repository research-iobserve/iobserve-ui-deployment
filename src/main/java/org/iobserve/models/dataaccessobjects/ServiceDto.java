package org.iobserve.models.dataaccessobjects;


import org.iobserve.models.Service;
import org.iobserve.models.annotations.ModelClassOfDto;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.Service
 */
@ModelClassOfDto(Service.class)
@XmlType(name = "service")
public class ServiceDto extends MeasurableDataTrasferObject {

    private String name;
    private String description;

    public ServiceDto() {
        super();
    }
}
