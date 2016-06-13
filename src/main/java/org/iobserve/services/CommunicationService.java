package org.iobserve.services;

import org.iobserve.models.Communication;
import org.iobserve.models.dataaccessobjects.CommunicationDto;

/**
 * Created by cdor on 13.06.16.
 */
public class CommunicationService extends AbstractSystemComponentService<Communication,CommunicationDto> {

    @Override
    protected CommunicationDto transformModelToDto(Communication communication) {
        return this.modelToDtoMapper.transform(communication);
    }
}
