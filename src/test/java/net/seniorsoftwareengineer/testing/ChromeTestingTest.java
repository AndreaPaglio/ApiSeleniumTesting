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

import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.entitydom.Element;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.option.OptionChromeUseToTesting;

class ChromeTestingTest {
	OptionChromeUseToTesting optionsChrome;
	ChromeTesting chromeUseToTesting;
	Element message;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		message = new Element();
		optionsChrome = new OptionChromeUseToTesting(message.getConfiguration());
		chromeUseToTesting = new ChromeTesting();
		
	}

	@AfterEach
	void tearDown() throws Exception {
		chromeUseToTesting.close();
	}

	@Test
	void testDefaultChromeTesting_configureDriver_() throws Exception {
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
	}
	
	@Test
	void testDefaultChromeTesting_configureDriver_browserVersionNull() throws Exception {
		message .getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
	}

	@Test
	void testDefaultChromeTesting_configureDriver() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		message.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.analyze(message);
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_analyze() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		message.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.analyze(message);
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_goOneStepInfiniteScroll() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		message.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.goOneStepInfiniteScroll();
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_enableJquery() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		message.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.enableJquery();
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_goInfiniteScroll() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		message.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.goInfiniteScroll();
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_executeSelector() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		message.setPageToTest(chromeUseToTesting);
		chromeUseToTesting.executeSelector(null);
		assertThatExceptionOfType(NullPointerException.class);
	}
	
	@Test
	void testDefaultChromeTesting_takeSnapShot() throws Exception {
		message.getConfiguration().setBrowserVersion(null);
		chromeUseToTesting.configure(optionsChrome.configure(), message.getConfiguration());
		message.setPageToTest(chromeUseToTesting);
		assertThatExceptionOfType(TestingException.class).isThrownBy(() -> {
			chromeUseToTesting.takeSnapShot(null);
		});
		
		
	}
}
