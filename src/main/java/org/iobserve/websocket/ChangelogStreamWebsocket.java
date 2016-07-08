package org.iobserve.websocket;

import org.iobserve.services.websocket.ChangelogStreamService;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** loaded by jetty
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@ServerEndpoint(
        value = "/v1/changelogstream/{systemId}",
        decoders = ChangelogCoder.class,
        encoders = ChangelogCoder.class
)
public class ChangelogStreamWebsocket {

    private static final Map<String, Set<Session>> sessionsPerSystem = new HashMap<>();
    ChangelogStreamService streamService = ChangelogStreamService.INSTANCE;

    @OnOpen
    public void onOpen(@PathParam("systemId") String systemId, Session session) {
        streamService.subscribe(systemId, session);
    }
}
