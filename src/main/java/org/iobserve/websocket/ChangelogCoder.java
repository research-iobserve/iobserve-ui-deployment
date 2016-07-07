package org.iobserve.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iobserve.models.dataaccessobjects.ChangelogDto;

import javax.websocket.*;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class ChangelogCoder implements Encoder.TextStream<ChangelogDto>, Decoder.TextStream<ChangelogDto>{

    @Override
    public ChangelogDto decode(Reader reader) throws DecodeException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return  mapper.readValue(reader, ChangelogDto.class);
    }

    @Override
    public void encode(ChangelogDto changelogDto, Writer writer) throws EncodeException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        writer.append(objectMapper.writeValueAsString(changelogDto));
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // nothing to do here
    }

    @Override
    public void destroy() {
        // nothing to do here
    }
}
