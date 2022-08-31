package net.seniorsoftwareengineer.testing.service.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {
	public static <T> String convertObjectToJsonString(T object) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
//		final ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		return objectMapper.writeValueAsString(object);
	}
}
