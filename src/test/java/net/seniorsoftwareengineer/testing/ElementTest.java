package net.seniorsoftwareengineer.testing;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;

import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.config.TestProperty;
import net.seniorsoftwareengineer.testing.controller.PageTestingRest;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.option.OptionChromeUseToTesting;
@WebMvcTest({ChromeTesting.class, TestProperty.class})
@TestPropertySource(properties = "testing.properties")
class ElementTest {
	OptionChromeUseToTesting optionsChrome;
	ChromeTesting chromeUseToTesting;
	TestCase message;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		message = new TestCase();		
	}

	@AfterEach
	void tearDown() throws Exception {
		message.close();
	}

	
	void testElement_execute() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		message.execute(null);	
	}
	
}
