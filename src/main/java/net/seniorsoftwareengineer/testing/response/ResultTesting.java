package net.seniorsoftwareengineer.testing.response;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * Class for returning all information for GET API /test
 *
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultTesting implements Serializable {
    private static final long serialVersionUID = 1L;
    private String requestId;
    private Map<String, TestingException> errors;
    private String result;
}
