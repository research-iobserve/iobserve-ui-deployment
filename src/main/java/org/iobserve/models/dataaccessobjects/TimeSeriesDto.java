package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.util.TimeSeries;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */

@ModelClassOfDto(TimeSeries.class)
@JsonTypeName("timeSeries")
public class TimeSeriesDto extends DataTransportObject {

    private String parentId;

    private String label;
    private Long intervalStart;
    private Long intervalEnd;

    private List<SeriesElementDto> series;

    public TimeSeriesDto() {
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public void setIntervalStart(Long intervalStart) {
        this.intervalStart = intervalStart;
    }

    public Long getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(Long intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public List<SeriesElementDto> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesElementDto> series) {
        this.series = series;
    }
}
