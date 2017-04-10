package cz.jirifrank.app.springler.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UnixDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	private static final ZoneId ZONE_ID = ZoneId.of("America/Los_Angeles");

	@Override
	public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		String unixTimestamp = parser.getText().trim();

		return Instant
				.ofEpochSecond(Long.valueOf(unixTimestamp))
				.atZone(ZONE_ID)
				.toLocalDateTime();
	}
}