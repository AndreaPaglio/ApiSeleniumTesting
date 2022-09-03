package net.seniorsoftwareengineer.testing;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.option.OptionChromeUseToTesting;

class ChromeTestingTest {
	OptionChromeUseToTesting optionsChrome;
	ChromeTesting chromeUseToTesting;
	TestCase test;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		test = new TestCase();
		optionsChrome = new OptionChromeUseToTesting(test.getConfiguration());
		chromeUseToTesting = new ChromeTesting();
		
	}

	@AfterEach
	void end() throws Exception {
		chromeUseToTesting.close();
	}
	
	@Test
	void testDefaultChromeTesting_configureDriver_() throws Exception {
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	}
	
	@Test
	void testDefaultChromeTesting_configureDriver_browserVersionNull() throws Exception {
		test .getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	}

	@Test
	void testDefaultChromeTesting_configureDriver() throws Exception {
		test.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
		test.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.analyze(test);
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_analyze() throws Exception {
		test.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
		test.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.analyze(test);
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_goOneStepInfiniteScroll() throws Exception {
		test.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
		test.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.goOneStepInfiniteScroll();
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_enableJquery() throws Exception {
		test.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
		test.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.enableJquery();
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_goInfiniteScroll() throws Exception {
		test.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
		test.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.goInfiniteScroll();
		assertThatExceptionOfType(NoSuchElementException.class);
	}
	
	@Test
	void testDefaultChromeTesting_executeSelector() throws Exception {
		test.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
		test.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.executeSelector(null);
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_takeSnapShot() throws TestingException, Exception {
		test.getConfiguration().setBrowserVersion("105.0.5195.102");
		chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
		test.setPageToTest(chromeUseToTesting);
		assertThatExceptionOfType(TestingException.class).isThrownBy(() -> {
				chromeUseToTesting.takeSnapShot(null);
		});
		
		
	}
}
