package org.iobserve.models.dataaccessobjects;


import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.ModelType;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.services.StatusInfoService;

/**
 * Created by cdor on 25.04.16.
 */

@ModelClassOfDto(value = StatusInfo.class, service = StatusInfoService.class)
@JsonTypeName(ModelType.TypeName.STATUS_INFO)
public class StatusInfoDto extends NestedDataAccessObject {
    private Long timestamp;
    private String key;
    private String value;

    public StatusInfoDto() {
        super();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
