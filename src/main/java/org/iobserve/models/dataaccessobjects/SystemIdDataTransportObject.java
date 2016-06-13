package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
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
