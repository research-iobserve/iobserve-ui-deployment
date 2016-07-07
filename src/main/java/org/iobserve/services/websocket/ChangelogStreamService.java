package org.iobserve.services.websocket;

import org.iobserve.models.dataaccessobjects.ChangelogDto;

import javax.validation.constraints.NotNull;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class ChangelogStreamService {

    public static ChangelogStreamService INSTANCE = new ChangelogStreamService(); // TODO: via dependency injection!

    private ConcurrentHashMap<String, SystemSessionHandler> sessionsBySystem = new ConcurrentHashMap<>();

    private ChangelogStreamService() {

    }

    public void subscribe(@NotNull String systemId, Session websocketSession) {
        synchronized(sessionsBySystem) {
            SystemSessionHandler handler;
            handler = sessionsBySystem.get(systemId);
            if(handler == null) {
                handler = new SystemSessionHandler(systemId);
                sessionsBySystem.put(systemId, handler);
            }
            handler.subscribe(websocketSession);
        }
    }

    public void broadcastChangelog(String systemId, ChangelogDto message) {
        sessionsBySystem.get(systemId).broadcast(message);
    }
}
