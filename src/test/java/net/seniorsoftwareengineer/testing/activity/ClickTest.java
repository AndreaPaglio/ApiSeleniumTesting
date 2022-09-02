package net.seniorsoftwareengineer.testing.activity;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.seniorsoftwareengineer.testing.exception.TestingException;

class ClickTest {
	Activity click;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		click = new Click();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testClick_execute() throws TestingException {
		assertThatExceptionOfType(TestingException.class).isThrownBy(() ->  {
			click.execute(null);
		});		
	}

}
