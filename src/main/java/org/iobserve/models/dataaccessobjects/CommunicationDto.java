package org.iobserve.models.dataaccessobjects;

import org.iobserve.models.Communication;
import org.iobserve.models.annotations.ModelClassOfDto;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.CommunicationInstance
 */
@ModelClassOfDto(Communication.class)
@XmlType(name = "communication")
public class CommunicationDto extends MeasurableDataTrasferObject {

    String technology;

    /**
     * id of the source service
     */
    String sourceId;


    /**
     * id of the target service
     */
    String targetId;

    public CommunicationDto() {
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

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
