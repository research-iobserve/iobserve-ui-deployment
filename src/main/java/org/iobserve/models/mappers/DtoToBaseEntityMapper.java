package org.iobserve.models.mappers;

import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.util.BaseEntity;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.models.util.TimeSeriesDto;
import org.mapstruct.factory.Mappers;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class DtoToBaseEntityMapper {

    private static final DtoToBasePropertyEntityMapper dtoToBasePropertyMapper = DtoToBasePropertyEntityMapper.INSTANCE;

    @Inject
    private static EntityManager entityManager;

    //TODO find proper solution
    public static BaseEntity transform(DataTransportObject dto){

        if(dto instanceof NodeGroupDto){
            return transform((NodeGroupDto) dto);

        }else if (dto instanceof NodeDto){
            return transform((NodeDto) dto);

        }else if (dto instanceof ServiceDto){
            return transform((ServiceDto) dto);

        }else if (dto instanceof ServiceInstanceDto){
            return transform((ServiceInstanceDto) dto);

        }else if (dto instanceof  CommunicationDto){
            return transform((CommunicationDto) dto);

        }else if (dto instanceof CommunicationInstanceDto){
            return transform((CommunicationInstanceDto) dto);

        }else if (dto instanceof SystemDto){
            return transform((SystemDto) dto);

        }else if (dto instanceof ChangelogDto){
            return transform((ChangelogDto) dto);

        }else if(dto instanceof TimeSeriesDto){
            return transform((TimeSeriesDto) dto);

        }else{
            return null;
        }
    }

    public static System transform(SystemDto systemDto) {
        final System system = dtoToBasePropertyMapper.transform(systemDto);

        return system;
    }

    public static Communication transform(CommunicationDto communicationDto) {
        final Communication communication = dtoToBasePropertyMapper.transform(communicationDto);

        communication.setSource(entityManager.find(Service.class, communicationDto.getSourceId()));
        communication.setTarget(entityManager.find(Service.class, communicationDto.getTargetId()));

        return communication;
    }


    public static CommunicationInstance transform(CommunicationInstanceDto communicationInstanceDto) {
        final CommunicationInstance communicationInstance = dtoToBasePropertyMapper.transform(communicationInstanceDto);

        communicationInstance.setCommunication(entityManager.find(Communication.class, communicationInstanceDto.getCommunicationId()));
        communicationInstance.setSource(entityManager.find(ServiceInstance.class, communicationInstanceDto.getSourceId()));
        communicationInstance.setTarget(entityManager.find(ServiceInstance.class, communicationInstanceDto.getTargetId()));

        return communicationInstance;
    }


    public static NodeGroup transform(NodeGroupDto nodeGroupDto) {
        final NodeGroup nodeGroup = dtoToBasePropertyMapper.transform(nodeGroupDto);

        return nodeGroup;
    }


    public static Node transform(NodeDto nodeDto) {
        final Node node = dtoToBasePropertyMapper.transform(nodeDto);

        node.setNodeGroup(entityManager.find(NodeGroup.class, nodeDto.getNodeGroupId()));

        return node;
    }


    public static Service transform(ServiceDto serviceDto) {
        final Service service = dtoToBasePropertyMapper.transform(serviceDto);

        return service;
    }


    public static ServiceInstance transform(ServiceInstanceDto serviceInstanceDto) {
        final ServiceInstance serviceInstance = dtoToBasePropertyMapper.transform(serviceInstanceDto);

        serviceInstance.setNode(entityManager.find(Node.class, serviceInstanceDto.getNodeId()));
        serviceInstance.setService(entityManager.find(Service.class, serviceInstanceDto.getServiceId()));

        return serviceInstance;
    }

    public static Changelog transform(ChangelogDto changelogDto) {
        final Changelog changelog = dtoToBasePropertyMapper.transform(changelogDto);
        return changelog;
    }

    public static TimeSeries transform(TimeSeriesDto seriesDto){
        final TimeSeries series = dtoToBasePropertyMapper.transform(seriesDto);
        return series;
    }
}
