package net.seniorsoftwareengineer.testing.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({ "localizedMessage", "suppressed", "cause", "stackTrace" })
public class TestingException extends Exception {
    private String text;
    private String className;
}
