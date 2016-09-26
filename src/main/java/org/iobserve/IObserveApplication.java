package org.iobserve;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
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

        // Now you can expect validation errors to be sent to the client.
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        DependencyInjectionBinder dependencyBinder = new DependencyInjectionBinder();
        register(EntityManagerSetup.class);
        register(dependencyBinder);
        register(CORSResponseFilter.class);
        register(GeneralExceptionMapper.class);
    }

    public static ChangelogStreamService getChangelogStreamService() {
        return changelogStreamService;
    }
}
