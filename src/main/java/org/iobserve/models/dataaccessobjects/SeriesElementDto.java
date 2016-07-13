package org.iobserve.models.dataaccessobjects;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.util.SeriesElement;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by Christoph on 13.07.2016.
 */
@ModelClassOfDto(SeriesElement.class)
@JsonTypeName("seriesElement")
public class SeriesElementDto extends DataTransportObject{
    private String seriesId;
    private Integer value;

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
}
