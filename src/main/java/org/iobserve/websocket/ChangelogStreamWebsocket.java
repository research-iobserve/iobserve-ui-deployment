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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/** loaded by jetty
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@ServerEndpoint(
        value = "/v1/changelogstream/{systemId}",
        decoders = ChangelogCoder.class,
        encoders = ChangelogCoder.class
)
public class ChangelogStreamWebsocket {

    //private static final Thread dummyThread = createDummyThread("system123");
    private static final ChangelogStreamService streamService = ChangelogStreamService.INSTANCE;

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
                ChangelogOperation operation = ChangelogOperation.UPDATE;
                while (true) {
                    ChangelogDto changelog = new ChangelogDto();
                    changelog.setId("test-changelog-" + sequence);
                    changelog.setOperation(operation);
                    NodeDto nodeDto = new NodeDto();
                    nodeDto.setName("SOCKET Node " + sequence);

                    if(operation.equals(ChangelogOperation.CREATE)) {
                        nodeDto.setId("test-node-" + sequence);
                    } else {
                        nodeDto.setId("test-system123-node-1");
                    }
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

                    List<ChangelogDto> changelogs = Collections.singletonList(changelog);
                    streamService.broadcastChangelogs(systemId, changelogs);

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
