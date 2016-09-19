package org.iobserve.models.util;

import org.iobserve.models.NestedMeasurement;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class StatusInfo extends NestedMeasurement {
    private Long timestamp;
    @Column(name="`key`") //since key is keyword in derby, the word has to be  escaped for table creation
    private String key;
    private String value;

    public StatusInfo() {
        super();
    }

    public StatusInfo(Long timestamp, String key, String value) {
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
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
