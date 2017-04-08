package org.iobserve.models.util;

import javax.persistence.Entity;

/**
 * Created by cdor on 25.05.16.
 */
@Entity
public class SeriesElement extends NestedMeasurement {

    private Integer value;
    private Long timestamp;

    public SeriesElement() {
        super();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
