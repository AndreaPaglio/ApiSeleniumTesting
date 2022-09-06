package net.seniorsoftwareengineer.testing.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for convert Object to Json trip
 *
 */
public class JsonUtils {
    public static <T> String convertObjectToJsonString(T object) throws JsonProcessingException {
	final ObjectMapper objectMapper = new ObjectMapper();
	return objectMapper.writeValueAsString(object);
    }
}
