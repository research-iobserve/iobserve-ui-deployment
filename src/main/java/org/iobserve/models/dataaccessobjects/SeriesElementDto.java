package org.iobserve.models.dataaccessobjects;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.services.SeriesElementService;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by Christoph on 13.07.2016.
 */
@ModelClassOfDto(value = SeriesElement.class, service = SeriesElementService.class)
@JsonTypeName("seriesElement")
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
