package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.iobserve.models.util.ChangelogOperation;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@XmlType(name = "changelog")
@XmlRootElement(name = "changelog")
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
