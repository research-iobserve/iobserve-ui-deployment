package org.iobserve.models.util;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cdor on 25.05.16.
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class  AbstractBaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3113270441001085656L;
	
	@Id
    @Column(name="id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
