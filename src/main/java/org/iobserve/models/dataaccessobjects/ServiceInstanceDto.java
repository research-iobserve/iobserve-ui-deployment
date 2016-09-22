package org.iobserve.models.dataaccessobjects;


import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.ServiceInstance;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.ServiceInstanceService;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.ServiceInstance
 */
@ModelClassOfDto(value = ServiceInstance.class, service = ServiceInstanceService.class)
@JsonTypeName("serviceInstance")
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
