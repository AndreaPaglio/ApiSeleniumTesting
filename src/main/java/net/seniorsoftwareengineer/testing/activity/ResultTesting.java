package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for returning all information for GET API /test
 *
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultTesting implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String requestId;
	private Map<String, String> errors;
	private String result;
}
