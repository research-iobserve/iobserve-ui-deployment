package org.iobserve.injection;

import org.glassfish.hk2.api.Factory;
import org.iobserve.services.websocket.ChangelogStreamService;

/**
 * Provides access to the ChangelogStreamService which hides the interal Singleton implementation
 * @see DependencyInjectionBinder
 */
public class ChangelogStreamServiceFactory implements Factory<ChangelogStreamService> { // TODO: use CloseableService


    @Override
    public ChangelogStreamService provide() {
        return ChangelogStreamService.INSTANCE;
    }

    @Override
    public void dispose(ChangelogStreamService entityManager) {

    }
}
