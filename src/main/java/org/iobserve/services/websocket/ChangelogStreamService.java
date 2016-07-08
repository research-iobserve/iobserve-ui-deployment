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

    public void unsubscribe(String systemId, Session session) {
        SystemSessionHandler systemSessionHandler = sessionsBySystem.get(systemId);
        if(systemSessionHandler != null) {
            sessionsBySystem.get(systemId).unsubscribe(session);
        }
    }

    public void broadcastChangelog(String systemId, ChangelogDto message) {
        SystemSessionHandler systemSessionHandler = sessionsBySystem.get(systemId);
        if(systemSessionHandler != null) {
            systemSessionHandler.broadcast(message);
        }
    }
}
