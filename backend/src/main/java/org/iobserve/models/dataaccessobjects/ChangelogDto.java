package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.Changelog;
import org.iobserve.models.ModelType;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.services.ChangelogService;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@XmlType(name = ModelType.TypeName.CHANGELOG)
@JsonTypeName(ModelType.TypeName.CHANGELOG)
@ModelClassOfDto(value = Changelog.class, service = ChangelogService.class)
public class ChangelogDto extends RevisionedBeanDataTransportObject {

    private ChangelogOperation operation;
    private DataTransportObject data;

    public ChangelogDto() {
        super();
    }

    public ChangelogOperation  getOperation() {
        return operation;
    }

    public void setOperation(ChangelogOperation  operation) {
        this.operation = operation;
    }

    public DataTransportObject getData() {
        return data;
    }

    public void setData(DataTransportObject data) {
        this.data = data;
    }
}
