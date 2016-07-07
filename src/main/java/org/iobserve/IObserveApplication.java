package org.iobserve;

import org.glassfish.jersey.server.ResourceConfig;
import org.iobserve.filters.CORSResponseFilter;
import org.iobserve.filters.GeneralExceptionMapper;
import org.iobserve.injection.DependencyInjectionBinder;
import org.iobserve.services.websocket.ChangelogStreamService;

import javax.ws.rs.ApplicationPath;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@ApplicationPath("resources")
public class IObserveApplication extends ResourceConfig{

    // TODO: possible to use dependency injection within javax.websocket endpoint? Websocket not handled by jersey, but jetty servlet.
    private static ChangelogStreamService changelogStreamService;

    public IObserveApplication() {
        packages("org.iobserve.resources");

        DependencyInjectionBinder dependencyBinder = new DependencyInjectionBinder();
        register(dependencyBinder);
        register(CORSResponseFilter.class);
        register(GeneralExceptionMapper.class);

//        changelogStreamService = (ChangelogStreamService) this.getInstances().stream().filter(o -> o instanceof ChangelogStreamService).findFirst().get();
        System.out.println("Singletons "+this.getApplication().getSingletons());
    }

    public static ChangelogStreamService getChangelogStreamService() {
        return changelogStreamService;
    }
}
