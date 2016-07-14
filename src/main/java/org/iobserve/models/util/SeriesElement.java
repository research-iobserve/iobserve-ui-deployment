package org.iobserve.models.util;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by cdor on 25.05.16.
 */
@Entity
public class SeriesElement extends BaseEntity{
    @Column(name="series_id")
    private String seriesId;
    private Integer value;
    private Long timestamp;

    public SeriesElement() {
        super();
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String series) {
        this.seriesId = series;
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
