package net.seniorsoftwareengineer.testing.controller;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.validation.Valid;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.entitydom.Element;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.option.OptionChromeUseToTesting;
import net.seniorsoftwareengineer.testing.response.ResultTesting;
import net.seniorsoftwareengineer.testing.utility.constants.ConstantsTesting;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for Selenium Testing API
 *
 */
@RestController
@Slf4j
@Api(description = "API to perform Selenium test", position = 0, tags = {"Selenium Testing End-to-End"})
public class PageTestingRest {
	@Autowired
	ChromeTesting chromeUseToTesting;
	
	@ApiIgnore
	@ApiModelProperty(hidden =  true)
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
	  @ApiResponse(responseCode = "200", description = "All elements listed in ACTIVITY with Exist or ExistText type have founded",
			  content = { @Content(mediaType = "application/json")}),
	  @ApiResponse(responseCode = "500", description = "Any elements listed in ACTIVITY with Exist or ExistText type haven't founded.\n If some error was thrown will be listed in errors field",
			  content = { @Content(mediaType = "application/json")})
	 })
    @RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<ResultTesting> listenerPageToTest(@RequestBody @Valid @Parameter(name="Request Body", description =  "Configuration (actions to run, URL, selectors css to find out)") Element message) throws TestingException {
		final OptionChromeUseToTesting optionsChrome = new OptionChromeUseToTesting(message.getConfiguration());
		ResultTesting result = new ResultTesting();
		try {
			chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		} catch (final Exception e) {
			Map<String, String> errors = new HashMap<String, String>();
			result.setResult(ConstantsTesting.TESTING_RESULT_KO);
			errors.put("error", e.getMessage());
			result.setErrors(errors);
			return new ResponseEntity<>(
		    		result, 
		            HttpStatus.INTERNAL_SERVER_ERROR);
		}
		message.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.analyze(message);
		result.setRequestId(message.getIdx());
		result.setResult(ConstantsTesting.TESTING_RESULT_OK);
		return new ResponseEntity<>(
	    		result, 
	            HttpStatus.OK);
	}
	
	@ApiModelProperty(hidden =  true)
	@ApiOperation(hidden = true, value = "Method validation Exception")
	@ApiIgnore
	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public ResponseEntity<ResultTesting> handleValidationExceptions(
			org.springframework.web.bind.MethodArgumentNotValidException ex) {
		ResultTesting result = new ResultTesting();
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    result.setErrors(errors);
		result.setResult(ConstantsTesting.TESTING_RESULT_KO);
	    return new ResponseEntity<>(
	    		result, 
	            HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiIgnore
	@ApiModelProperty(hidden =  true)
	@ApiOperation(hidden = true, value = "Testing Exception")
	@ExceptionHandler(TestingException.class)
	public ResponseEntity<ResultTesting> handleExceptions(
			TestingException ex) {
		ResultTesting result = new ResultTesting();
	    Map<String, String> errors = new HashMap<>();
	    errors.put("error", ex.getText());
	    result.setErrors(errors);
		result.setResult(ConstantsTesting.TESTING_RESULT_KO);
	    return new ResponseEntity<>(
	    		result, 
	            HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
