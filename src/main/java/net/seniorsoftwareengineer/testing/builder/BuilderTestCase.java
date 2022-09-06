package net.seniorsoftwareengineer.testing.builder;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import net.seniorsoftwareengineer.testing.activity.Activity;
import net.seniorsoftwareengineer.testing.browser.option.OptionChromeTesting;
import net.seniorsoftwareengineer.testing.configuration.Configuration;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.service.ValidationService;
import net.seniorsoftwareengineer.testing.service.ValidationServiceImpl;

public class BuilderTestCase {
    private Configuration configuration;
    private ChromeTesting chromeUseToTesting;
    private BuilderTestCase factoryTest;
    private TestCase testCase;
    private ValidationService validationService;
    private Set<ConstraintViolation<TestCase>> violations;
    private TestingException exception;

    public static BuilderTestCase create() {
	BuilderTestCase factory = new BuilderTestCase();
	factory.validationService = new ValidationServiceImpl();
	factory.testCase = new TestCase();
	return factory;
    }

    public static BuilderTestCase create(TestCase test) {
	BuilderTestCase factory = new BuilderTestCase();
	factory.testCase = test;
	factory.configuration = test.getConfiguration();
	factory.validationService = new ValidationServiceImpl();
	return factory;
    }

    public BuilderTestCase url(String url) {
	testCase.setUrl(url);
	return this;
    }

    public BuilderTestCase configuration(Configuration configuration) {
	this.configuration = configuration;
	testCase.setConfiguration(configuration);
	return this;
    }

    public BuilderTestCase addActivity(Activity activity) throws TestingException {
	testCase.getActivities().add(activity);
	return this;
    }

    public BuilderTestCase execute() throws TestingException {
	factoryTest = this;
	violations = new HashSet<ConstraintViolation<TestCase>>();
//		First validation before configure
	validationService.validate(testCase);

//		Configure test case
	chromeUseToTesting = new ChromeTesting();
	OptionChromeTesting optionsChrome = new OptionChromeTesting(configuration);
	try {
	    chromeUseToTesting.configure(optionsChrome.configure(), configuration);
	} catch (Exception e) {
	    chromeUseToTesting.close();
	    exception = new TestingException("Error configuration test", "configuration");
	    throw exception;
	}
//		Second validation before analyze
	validationService.validate(testCase);
	for (Activity activity : testCase.getActivities()) {
	    validationService.validate(activity);
	    validationService.validate(activity.getSelector());
	}

	if (violations.isEmpty()) {
	    try {
		analyze(testCase, chromeUseToTesting);
	    } catch (NoSuchElementException e) {
		exception = new TestingException("Error configuration test", "configuration");
		throw exception;
	    } catch (TestingException e) {
		exception = new TestingException(e.getText(), e.getClassName());
		throw exception;
	    } catch (Exception e) {
		chromeUseToTesting.close();
		exception = new TestingException(e.getMessage(), e.getClass().getName());
		throw exception;
	    }
	}
	return this;
    }

    private void analyze(TestCase testCase, ChromeTesting chromeUseToTesting)
	    throws TestingException, NoSuchElementException, Exception {
	if (StringUtils.isNotEmpty(testCase.getUrl())) {
	    WebDriver driverChrome = chromeUseToTesting.getDriver().get();
	    String url = testCase.getUrl();
	    driverChrome.get(url);
	    if (StringUtils.isEmpty(testCase.getIdx())) {
		testCase.setIdx(UUID.randomUUID().toString());
	    }
	    if (testCase.getConfiguration().getUseJQuery()) {
		chromeUseToTesting.enableJquery();
	    }

	    chromeUseToTesting.execute(testCase.getIdx(), testCase.getActivities());
	    chromeUseToTesting.close();
	}
    }

    public TestCase getTestCase() {
	return testCase;
    }

    public TestingException getException() {
	return exception;
    }
}
