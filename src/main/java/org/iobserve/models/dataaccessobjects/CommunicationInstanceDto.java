package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.ModelType;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.CommunicationInstanceService;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.CommunicationInstance
 */
@ModelClassOfDto(value = CommunicationInstance.class, service = CommunicationInstanceService.class)
@JsonTypeName(ModelType.TypeName.COMMUNICATION_INSTANCE)
public class CommunicationInstanceDto extends MeasurableDataTrasferObject {

    /**
     * id of the source service
     */
    private String sourceId;


    /**
     * id of the target service
     */
    private String targetId;

    private String communicationId;

    private Long workload;

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
