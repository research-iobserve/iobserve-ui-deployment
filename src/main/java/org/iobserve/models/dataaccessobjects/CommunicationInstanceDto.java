package org.iobserve.models.dataaccessobjects;

import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.annotations.ModelClassOfDto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.CommunicationInstance
 */
@ModelClassOfDto(CommunicationInstance.class)
@XmlType(name = "communicationInstance")
public class CommunicationInstanceDto extends MeasurableDataTrasferObject {

    /**
     * id of the source service
     */
    String sourceId;


    /**
     * id of the target service
     */
    String targetId;

    String communicationId;

    Long workload;

    public CommunicationInstanceDto() {
        super();
    }

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

    public Long getWorkload() {
        return workload;
    }

    public void setWorkload(Long workload) {
        this.workload = workload;
    }


}
