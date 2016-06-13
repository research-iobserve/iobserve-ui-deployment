package org.iobserve.models.dataaccessobjects;


/**
 * @author Mathis Neumann
 * @see org.iobserve.models.ServiceInstance
 */
public class ServiceInstanceDto extends SystemIdDataTransportObject {
    private String name;
    private String nodeId;
    private String serviceId;


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
