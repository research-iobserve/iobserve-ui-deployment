package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann
 * @see org.iobserve.models.CommunicationInstance
 */
public class CommunicationInstanceDto extends SystemIdDataTransportObject {

    /**
     * id of the source service
     */
    String sourceId;


    /**
     * id of the target service
     */
    String targetId;

    String communicationId;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }
}
