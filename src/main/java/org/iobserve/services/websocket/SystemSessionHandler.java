package org.iobserve.services.websocket;

import org.iobserve.models.dataaccessobjects.ChangelogDto;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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

    public synchronized void broadcast(List<ChangelogDto> changelogs) {
        // no need to block, only the iterator has to be synchronized.
        ChangelogDto[] changelogDtos = changelogs.toArray(new ChangelogDto[changelogs.size()]);
        sessions.forEach((session) -> {
            session.getAsyncRemote().sendObject(changelogDtos); // see ChangelogCoder
        });

    }

    public String getSystemId() {
        return systemId;
    }


    public synchronized void unsubscribe(Session session) {
        if(session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean didRemove = sessions.remove(session);
        if(!didRemove) {
            System.out.println("could not remove session " + session);
        }
    }
}
