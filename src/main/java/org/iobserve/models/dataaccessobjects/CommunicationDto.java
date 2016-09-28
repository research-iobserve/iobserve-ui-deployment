package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.Communication;
import org.iobserve.models.ModelType;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.CommunicationService;



/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.CommunicationInstance
 */
@ModelClassOfDto(value = Communication.class, service = CommunicationService.class)
@JsonTypeName(ModelType.TypeName.COMMUNICATION)
public class CommunicationDto extends MeasurableDataTrasferObject {

    private String technology;

    /**
     * id of the source service
     */
    private String sourceId;


    /**
     * id of the target service
     */
    private String targetId;

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
