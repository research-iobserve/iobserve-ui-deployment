package org.iobserve.websocket;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@ServerEndpoint(value = "/v1/changelogstream/{systemId}")
public class ChangelogStreamWebsocket {

    @OnOpen
    public void onOpen(@PathParam("systemId") String systemId, Session session) {
        System.out.println("socket connection!");
    }
}
