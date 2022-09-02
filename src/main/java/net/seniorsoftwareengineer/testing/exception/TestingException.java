package net.seniorsoftwareengineer.testing.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom Exception class for manage testing exception
 *
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestingException extends Exception{
	private String text;
	private String className;
}
