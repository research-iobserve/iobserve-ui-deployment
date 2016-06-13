package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
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
