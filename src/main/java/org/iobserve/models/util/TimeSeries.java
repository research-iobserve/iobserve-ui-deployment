package org.iobserve.models.util;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class TimeSeries extends BaseEntity{
    @Column(name="parent_id")
    private String parent;

    private String label;
    private Long intervalStart;
    private Long intervalEnd;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="series_id", referencedColumnName="id")
    private List<SeriesElement> series;

    public TimeSeries() {
    }

    public TimeSeries(String label, Long intervalStart, Long intervalEnd, List<SeriesElement> series) {
        this.label = label;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.series = series;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(Long start) {
        this.intervalStart = start;
    }

    public Long getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(Long end) {
        this.intervalEnd = end;
    }

    public List<SeriesElement> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesElement> series) {
        this.series = series;
    }

}
