package org.iobserve.websocket;

import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.services.websocket.ChangelogStreamService;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;

/** loaded by jetty
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@ServerEndpoint(
        value = "/v1/changelogstream/{systemId}",
        decoders = ChangelogCoder.class,
        encoders = ChangelogCoder.class
)
public class ChangelogStreamWebsocket {

    private static final Thread dummyThread = createDummyThread("system123");
    private static ChangelogStreamService streamService = ChangelogStreamService.INSTANCE;

    @OnOpen
    public void onOpen(@PathParam("systemId") String systemId, Session session) {
        streamService.subscribe(systemId, session);
    }

    @OnClose
    public void onClose(@PathParam("systemId") String systemId, Session session) {
        streamService.unsubscribe(systemId, session);
    }

    // TODO: remove. Just for development purposes
    private static Thread createDummyThread(String systemId) {
        Thread thread = new Thread(() -> {
            try {
                long sequence = 0L;
                while (true) {
                    ChangelogDto changelog = new ChangelogDto();
                    changelog.setId("test-changelog-" + sequence);
                    changelog.setOperation(ChangelogOperation.CREATE);
                    NodeDto nodeDto = new NodeDto();
                    nodeDto.setName("Node " + sequence);
                    nodeDto.setId("test-node-" + sequence);
                    nodeDto.setHostname("localhost");
                    nodeDto.setIp("10.0.0.1");
                    nodeDto.setRevisionNumber(sequence);
                    nodeDto.setSystemId(systemId);
                    nodeDto.setChangelogSequence(sequence);
                    nodeDto.setLastUpdate(new Date());
                    changelog.setData(nodeDto);
                    changelog.setLastUpdate(new Date());
                    changelog.setSystemId(systemId);

                    System.out.println("sending dummy changelog " + sequence);

                    streamService.broadcastChangelog(systemId, changelog);

                    sequence++;
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        return thread;
    }
}
