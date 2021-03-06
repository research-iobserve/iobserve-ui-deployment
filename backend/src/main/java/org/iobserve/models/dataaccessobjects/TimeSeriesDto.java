package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.ModelType;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.services.TimeSeriesService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */

@ModelClassOfDto(value = TimeSeries.class, service = TimeSeriesService.class)
@JsonTypeName(ModelType.TypeName.TIME_SERIES)
public class TimeSeriesDto extends NestedDataAccessObject {

    private String label;
    private String valueLabel;

    private List<SeriesElementDto> series;

    public TimeSeriesDto() {
        series = new LinkedList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public List<SeriesElementDto> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesElementDto> series) {
        this.series = series;
    }

    public String getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(String valueLabel) {
        this.valueLabel = valueLabel;
    }
}
