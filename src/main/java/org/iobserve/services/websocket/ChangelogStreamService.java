package org.iobserve.services.websocket;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.iobserve.models.dataaccessobjects.ChangelogDto;

import javax.validation.constraints.NotNull;
import javax.websocket.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
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
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ChangelogDto.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");


            StringWriter outWriter = new StringWriter();
            StreamResult result = new StreamResult(outWriter);

            marshaller.marshal(message, result);
            String json = outWriter.getBuffer().toString();
            System.out.printf("json for socket "+ json);
            sessionsBySystem.get(systemId).broadcast(json);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
