package org.iobserve.services;

import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.dataaccessobjects.CommunicationInstanceDto;

/**
 * Created by cdor on 13.06.16.
 */
public class CommunicationInstanceService extends AbstractSystemComponentService<CommunicationInstance,CommunicationInstanceDto> {
    @Override
    protected CommunicationInstanceDto transformModelToDto(CommunicationInstance communicationInstance) {
        return this.modelToDtoMapper.transform(communicationInstance);
    }
}
