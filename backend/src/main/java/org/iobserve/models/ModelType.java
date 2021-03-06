package org.iobserve.models;

import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.util.AbstractBaseEntity;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public enum ModelType {

    // only "leaf" classes are relevant, because they will be communicated to the client
    CHANGELOG(TypeName.CHANGELOG,  Changelog.class, ChangelogDto.class),
    COMMUNICATION(TypeName.COMMUNICATION,  Communication.class, CommunicationDto.class),
    COMMUNICATION_INSTANCE(TypeName.COMMUNICATION_INSTANCE, CommunicationInstance.class, CommunicationInstanceDto.class),
    NODE(TypeName.NODE, Node.class, NodeDto.class),
    NODE_GROUP(TypeName.NODE_GROUP, NodeGroup.class, NodeGroupDto.class),
    SERIES_ELEMENT(TypeName.SERIES_ELEMENT, SeriesElement.class, SeriesElementDto.class),
    SERVICE(TypeName.SERVICE, Service.class, ServiceDto.class),
    SERVICE_INSTANCE(TypeName.SERVICE_INSTANCE, ServiceInstance.class, ServiceInstanceDto.class),
    STATUS_INFO(TypeName.STATUS_INFO, StatusInfo.class, StatusInfoDto.class),
    SYSTEM(TypeName.SYSTEM, System.class, SystemDto.class),
    TIME_SERIES(TypeName.TIME_SERIES, TimeSeries.class, TimeSeriesDto.class);

    public static ModelType getForType(String type) {
        for (ModelType modelType : ModelType.values()) {
            if(modelType.getType().equals(type)) {
                return modelType;
            }
        }
        throw new IllegalArgumentException("type "+ type +" does not exist");
    }
    public static ModelType getForModel(Class<? extends AbstractBaseEntity> modelClass) {
        for (ModelType modelType : ModelType.values()) {
            if(modelType.getModelClass().equals(modelClass)) {
                return modelType;
            }
        }
        throw new IllegalArgumentException("mode class "+ modelClass.getName() +" does not exist");
    }

    public final String type;
    private final Class<? extends AbstractBaseEntity> modelClass;
    private final Class<? extends DataTransportObject> dto;

    ModelType(String type, Class<? extends AbstractBaseEntity> modelClass, Class<? extends DataTransportObject> dto) {
        this.modelClass = modelClass;
        this.type = type;
        this.dto = dto;
    }

    public String getType() {
        return type;
    }

    public Class<? extends AbstractBaseEntity> getModelClass() {
        return modelClass;
    }

    public Class<? extends DataTransportObject> getDto() {
        return dto;
    }

    public static class TypeName {
        public static final String CHANGELOG = "changelog";
        public static final String COMMUNICATION = "communication";
        public static final String COMMUNICATION_INSTANCE = "communicationInstance";
        public static final String NODE = "node";
        public static final String NODE_GROUP = "nodeGroup";
        public static final String SERIES_ELEMENT = "seriesElement";
        public static final String SERVICE = "service";
        public static final String SERVICE_INSTANCE = "serviceInstance";
        public static final String STATUS_INFO = "statusInfo";
        public static final String SYSTEM = "system";
        public static final String TIME_SERIES = "timeSeries";
    }
}
