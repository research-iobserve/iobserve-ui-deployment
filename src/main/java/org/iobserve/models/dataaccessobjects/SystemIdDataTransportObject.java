package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann
 */
public abstract class SystemIdDataTransportObject extends DataTransportObject {
    String systemId;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
