package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.iobserve.models.util.ChangelogOperation;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@XmlType(name = "changelog")
public class ChangelogDto extends RevisionedBeanDataTransportObject {

    private ChangelogOperation operation;
    private NodeDto data;

    public ChangelogDto() {
        super();
    }

    public ChangelogOperation  getOperation() {
        return operation;
    }

    public void setOperation(ChangelogOperation  operation) {
        this.operation = operation;
    }

    public NodeDto getData() {
        return data;
    }

    public void setData(NodeDto data) {
        this.data = data;
    }
}
