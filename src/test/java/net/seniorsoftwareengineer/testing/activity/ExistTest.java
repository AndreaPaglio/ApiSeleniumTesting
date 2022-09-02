package net.seniorsoftwareengineer.testing.activity;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.seniorsoftwareengineer.testing.exception.TestingException;

class ExistTest {
	Exist exist;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		exist = new Exist();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testExist_() throws TestingException {
		assertThatExceptionOfType(TestingException.class).isThrownBy(() -> {
			exist.execute(null);
		});
	}

}
