package org.iobserve.models.dataaccessobjects;


import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.Service;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.ServiceService;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.Service
 */
@ModelClassOfDto(value = Service.class, service = ServiceService.class)
@JsonTypeName("service")
public class ServiceDto extends MeasurableDataTrasferObject {

    private String name;
    private String description;

    public ServiceDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
