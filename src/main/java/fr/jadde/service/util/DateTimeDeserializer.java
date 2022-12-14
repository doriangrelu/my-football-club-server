package fr.jadde.service.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    protected DateTimeDeserializer(final Class<?> vc) {
        super(vc);
    }

    protected DateTimeDeserializer() {
        this(null);
    }


    @Override
    public LocalDateTime deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final String date = jsonParser.getText();
        return LocalDateTime.parse(date, FORMATTER);
    }

}
