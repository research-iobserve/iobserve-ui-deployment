package org.iobserve.models.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.util.BaseEntity;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.models.dataaccessobjects.TimeSeriesDto;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class DtoToBaseEntityMapper {

    private final DtoToBasePropertyEntityMapper dtoToBasePropertyMapper = DtoToBasePropertyEntityMapper.INSTANCE;

    @Inject
    private EntityManagerFactory entityManagerFactory;

    //TODO find proper solution
    public BaseEntity transform(DataTransportObject dto){
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

        }else if(dto instanceof StatusInfoDto){
            return transform((StatusInfoDto) dto);

        }else if(dto instanceof SeriesElementDto){
            return transform((SeriesElementDto) dto);

        }else{
            return null;
        }
    }

    public System transform(SystemDto systemDto) {
        final System system = dtoToBasePropertyMapper.transform(systemDto);

        return system;
    }

    public Communication transform(CommunicationDto communicationDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Communication communication = dtoToBasePropertyMapper.transform(communicationDto);

        communication.setSource(entityManager.find(Service.class, communicationDto.getSourceId()));
        communication.setTarget(entityManager.find(Service.class, communicationDto.getTargetId()));
        entityManager.close();

        return communication;
    }


    public CommunicationInstance transform(CommunicationInstanceDto communicationInstanceDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final CommunicationInstance communicationInstance = dtoToBasePropertyMapper.transform(communicationInstanceDto);

        communicationInstance.setCommunication(entityManager.find(Communication.class, communicationInstanceDto.getCommunicationId()));
        communicationInstance.setSource(entityManager.find(ServiceInstance.class, communicationInstanceDto.getSourceId()));
        communicationInstance.setTarget(entityManager.find(ServiceInstance.class, communicationInstanceDto.getTargetId()));

        entityManager.close();

        return communicationInstance;
    }


    public NodeGroup transform(NodeGroupDto nodeGroupDto) {
        final NodeGroup nodeGroup = dtoToBasePropertyMapper.transform(nodeGroupDto);

        return nodeGroup;
    }


    public Node transform(NodeDto nodeDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Node node = dtoToBasePropertyMapper.transform(nodeDto);

        if(nodeDto.getNodeGroupId() != null){
            final NodeGroup nodeGroup = entityManager.find(NodeGroup.class, nodeDto.getNodeGroupId());
            node.setNodeGroup(nodeGroup);
        }


        entityManager.close();

        return node;
    }


    public Service transform(ServiceDto serviceDto) {
        final Service service = dtoToBasePropertyMapper.transform(serviceDto);

        return service;
    }


    public ServiceInstance transform(ServiceInstanceDto serviceInstanceDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final ServiceInstance serviceInstance = dtoToBasePropertyMapper.transform(serviceInstanceDto);

        serviceInstance.setNode(entityManager.find(Node.class, serviceInstanceDto.getNodeId()));
        serviceInstance.setService(entityManager.find(Service.class, serviceInstanceDto.getServiceId()));

        entityManager.close();

        return serviceInstance;
    }

    public Changelog transform(ChangelogDto changelogDto) {
        final Changelog changelog = dtoToBasePropertyMapper.transform(changelogDto);

        final ObjectMapper objectMapper = new ObjectMapper();
        String data = "";

        try {
            data = objectMapper.writeValueAsString(changelogDto.getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        changelog.setData(data);

        return changelog;
    }

    public TimeSeries transform(TimeSeriesDto seriesDto){
        final TimeSeries series = dtoToBasePropertyMapper.transform(seriesDto);

        return series;
    }

    public SeriesElement transform(SeriesElementDto seriesElementDto){
        final SeriesElement seriesElement = dtoToBasePropertyMapper.transform(seriesElementDto);
        return seriesElement;
    }

    public StatusInfo transform(StatusInfoDto statusInfoDto){
        final StatusInfo info = dtoToBasePropertyMapper.transform(statusInfoDto);

        return info;
    }
}
