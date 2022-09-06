package net.seniorsoftwareengineer.testing;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;

import net.seniorsoftwareengineer.testing.activity.Activity;
import net.seniorsoftwareengineer.testing.browser.option.OptionChromeTesting;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.config.TestProperty;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;

@WebMvcTest({ ChromeTesting.class, TestProperty.class })
@TestPropertySource(properties = "testing.properties")
class ChromeTestingTest {
    OptionChromeTesting optionsChrome;
    ChromeTesting chromeUseToTesting;
    TestCase test;

    @Autowired
    TestProperty testProperty;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
	test = new TestCase();
	optionsChrome = new OptionChromeTesting(test.getConfiguration());
	chromeUseToTesting = new ChromeTesting();

    }

    @AfterEach
    void end() throws Exception {
	chromeUseToTesting.close();
    }

    void testDefaultChromeTesting_configureDriver_() throws Exception {
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
    }

    void testDefaultChromeTesting_configureDriver_browserVersionNull() throws Exception {
	test.getConfiguration().setBrowserVersion(null);
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
    }

    void testDefaultChromeTesting_configureDriver() throws Exception {
	test.getConfiguration().setBrowserVersion(null);
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	assertThatExceptionOfType(NullPointerException.class);
    }

    void testDefaultChromeTesting_execute() throws Exception {
	test.getConfiguration().setBrowserVersion(null);
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	List<Activity> activities = null;
	String id = UUID.randomUUID().toString();
	chromeUseToTesting.execute(id, activities);
	;
	assertThatExceptionOfType(NullPointerException.class);
    }

    void testDefaultChromeTesting_goOneStepInfiniteScroll() throws Exception {
	test.getConfiguration().setBrowserVersion(null);
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	chromeUseToTesting.goOneStepInfiniteScroll();
	assertThatExceptionOfType(NullPointerException.class);
    }

    void testDefaultChromeTesting_enableJquery() throws Exception {
	test.getConfiguration().setBrowserVersion(null);
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	chromeUseToTesting.enableJquery();
	assertThatExceptionOfType(NullPointerException.class);
    }

    void testDefaultChromeTesting_goInfiniteScroll() throws Exception {
	test.getConfiguration().setBrowserVersion(null);
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	chromeUseToTesting.goInfiniteScroll();
	assertThatExceptionOfType(NoSuchElementException.class);
    }

    void testDefaultChromeTesting_executeSelector() throws Exception {
	test.getConfiguration().setBrowserVersion(null);
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	chromeUseToTesting.executeSelector(null);
	assertThatExceptionOfType(NullPointerException.class);
    }

    void testDefaultChromeTesting_takeSnapShot() throws TestingException, Exception {
	test.getConfiguration().setBrowserVersion(testProperty.getBrowserVersion());
	chromeUseToTesting.configure(optionsChrome.configure(), test.getConfiguration());
	assertThatExceptionOfType(TestingException.class).isThrownBy(() -> {
	    chromeUseToTesting.takeSnapShot(null);
	});

    }
}
