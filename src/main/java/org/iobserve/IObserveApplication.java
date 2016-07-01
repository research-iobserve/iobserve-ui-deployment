package org.iobserve;

import org.glassfish.jersey.server.ResourceConfig;
import org.iobserve.filters.CORSResponseFilter;
import org.iobserve.filters.GeneralExceptionMapper;
import org.iobserve.injection.DependencyInjectionBinder;

import javax.ws.rs.ApplicationPath;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@ApplicationPath("resources")
public class IObserveApplication extends ResourceConfig{
    public IObserveApplication() {
        packages("org.iobserve.resources");


        register(new DependencyInjectionBinder());
        register(CORSResponseFilter.class);
        register(GeneralExceptionMapper.class);
    }
}
