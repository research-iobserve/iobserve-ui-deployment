package org.iobserve.services.websocket;

import javax.websocket.Session;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class SystemSessionHandler {

    private String systemId;
    private Set<Session> sessions = new HashSet<>();

    public SystemSessionHandler(String systemId) {
        this.systemId = systemId;
    }

    public synchronized void subscribe(Session session) {
        sessions.add(session);
    }

    public synchronized void broadcast(String message) {
        // no need to block, only the iterator has to be synchronized.
        sessions.forEach((session) -> {
            session.getAsyncRemote().sendText(message);
        });

    }

    public String getSystemId() {
        return systemId;
    }
}
