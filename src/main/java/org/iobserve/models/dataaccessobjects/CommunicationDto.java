package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann
 * @see org.iobserve.models.CommunicationInstance
 */
public class CommunicationDto extends SystemIdDataTransportObject {

    String technology;

    /**
     * id of the source service
     */
    String sourceId;


    /**
     * id of the target service
     */
    String targetId;

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

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
