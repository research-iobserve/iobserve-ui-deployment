package org.iobserve.models.dataaccessobjects;

import org.iobserve.models.util.BaseEntity;
import org.iobserve.models.util.RevisionedBean;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class ChangelogDto extends RevisionedBeanDataTransportObject {
    public enum OPERATION {CREATE,DELETE,UPDATE,APPEND}

    private OPERATION operation;
    private BaseEntity data;

    public OPERATION getOperation() {
        return operation;
    }

    public void setOperation(OPERATION operation) {
        this.operation = operation;
    }

    public BaseEntity getData() {
        return data;
    }

    public void setData(BaseEntity data) {
        this.data = data;
    }
}
