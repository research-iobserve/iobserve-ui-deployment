package org.iobserve.models.util;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class TimeSeries extends BaseEntity{
    @Column(name="parent_id")
    private String parentId;

    private String label;
    private String valueLabel;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="series_id", referencedColumnName="id")
    private List<SeriesElement> series = new LinkedList<>();

    public TimeSeries() {
        super();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parent) {
        this.parentId = parent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public List<SeriesElement> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesElement> series) {
        this.series = series;
    }

    public String getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(String valueLabel) {
        this.valueLabel = valueLabel;
    }
}
