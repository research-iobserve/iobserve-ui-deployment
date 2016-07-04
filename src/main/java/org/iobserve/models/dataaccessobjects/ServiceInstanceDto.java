package org.iobserve.models.dataaccessobjects;


import org.iobserve.models.ServiceInstance;
import org.iobserve.models.annotations.ModelClassOfDto;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.ServiceInstance
 */
@ModelClassOfDto(ServiceInstance.class)
@XmlType(name = "serviceInstance")
public class ServiceInstanceDto extends MeasurableDataTrasferObject {
    private String name;
    private String nodeId;
    private String serviceId;

    public ServiceInstanceDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
