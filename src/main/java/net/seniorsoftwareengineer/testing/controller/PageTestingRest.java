package net.seniorsoftwareengineer.testing.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.builder.BuilderTestCase;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.response.ResultTesting;
import net.seniorsoftwareengineer.testing.utility.ConstantsTesting;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for Selenium Testing API
 *
 */
@RestController
@Slf4j
@Api(description = "API to perform Selenium test", position = 0, tags = { "Selenium Testing End-to-End" })
public class PageTestingRest {
    @Autowired
    ChromeTesting chromeUseToTesting;

    @ApiIgnore
    @ApiModelProperty(hidden = true)
    @ApiOperation(hidden = true, value = "Testing Exception")
    @RequestMapping("/json-testing-file")
    public ResponseEntity<Resource> file() {
	Resource resource = new ClassPathResource("/json/testing-2.0.json");
	HttpHeaders headers = new HttpHeaders();
	headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"testing-2.0.json\"");

	return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @Operation(summary = "Run test at url configured")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "All elements listed in ACTIVITY with Exist or ExistText type have founded", content = {
		    @Content(mediaType = "application/json") }),
	    @ApiResponse(responseCode = "500", description = "Any elements listed in ACTIVITY with Exist or ExistText type haven't founded.\n If some error was thrown will be listed in errors field", content = {
		    @Content(mediaType = "application/json") }) })
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<ResultTesting> listenerPageToTest(
	    @RequestBody @Valid @Parameter(name = "Request Body", description = "Configuration (actions to run, URL, selectors css to find out)") TestCase test)
	    throws TestingException {
	ResultTesting result = new ResultTesting();
	TestCase testCaseExecute = null;
	BuilderTestCase builderTestCase = null;
	try {
	    builderTestCase = BuilderTestCase.create(test).execute();
	    testCaseExecute = builderTestCase.getTestCase();
	} catch (final Exception e) {
	    Map<String, TestingException> errors = new HashMap<String, TestingException>();
	    result.setResult(ConstantsTesting.TESTING_RESULT_KO);
	    TestingException exception;
	    if (e instanceof TestingException) {
		exception = new TestingException(((TestingException) e).getText(),
			((TestingException) e).getClassName());
	    } else {
		exception = new TestingException(e.getMessage(), e.getClass().getName());
	    }
	    errors.put("error", exception);
	    result.setErrors(errors);
	    return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	result.setRequestId(testCaseExecute.getIdx());
	result.setResult(ConstantsTesting.TESTING_RESULT_OK);
	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiModelProperty(hidden = true)
    @ApiOperation(hidden = true, value = "Method validation Exception")
    @ApiIgnore
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ResultTesting> handleValidationExceptions(
	    org.springframework.web.bind.MethodArgumentNotValidException ex) {
	ResultTesting result = new ResultTesting();
	Map<String, TestingException> errors = new HashMap<>();
	ex.getBindingResult().getAllErrors().forEach((error) -> {
	    TestingException exception = new TestingException(error.getDefaultMessage(),
		    ((FieldError) error).getField());
	    errors.put(exception.getClassName(), exception);
	});
	result.setErrors(errors);
	result.setResult(ConstantsTesting.TESTING_RESULT_KO);
	return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiIgnore
    @ApiModelProperty(hidden = true)
    @ApiOperation(hidden = true, value = "Testing Exception")
    @ExceptionHandler(TestingException.class)
    public ResponseEntity<ResultTesting> handleExceptions(TestingException ex) {
	ResultTesting result = new ResultTesting();
	Map<String, TestingException> errors = new HashMap<>();
	TestingException exception = new TestingException(ex.getText(), ex.getClassName());
	errors.put("error", exception);
	result.setErrors(errors);
	result.setResult(ConstantsTesting.TESTING_RESULT_KO);
	return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
