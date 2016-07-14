package org.iobserve.models.util;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class StatusInfo extends BaseEntity {
    @Column(name="parent_id")
    private String parentId;
    private Long timestamp;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
