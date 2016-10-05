package org.iobserve.models.dataaccessobjects;


import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.ModelType;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.services.SeriesElementService;

/**
 * Created by Christoph on 13.07.2016.
 */
@ModelClassOfDto(value = SeriesElement.class, service = SeriesElementService.class)
@JsonTypeName(ModelType.TypeName.SERIES_ELEMENT)
public class SeriesElementDto extends DataTransportObject{
    private String seriesId;
    private Integer value;
    private Long timestamp;

    public SeriesElementDto() {
        super();
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
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
