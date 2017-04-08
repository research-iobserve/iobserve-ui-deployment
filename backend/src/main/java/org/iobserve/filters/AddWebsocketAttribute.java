package org.iobserve.filters;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface AddWebsocketAttribute {
}
