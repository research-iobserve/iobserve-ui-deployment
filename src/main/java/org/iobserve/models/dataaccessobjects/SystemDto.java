package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann
 * @see org.iobserve.models.System
 */
public class SystemDto extends DataTransportObject {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
