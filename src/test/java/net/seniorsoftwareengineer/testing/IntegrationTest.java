package net.seniorsoftwareengineer.testing;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.seniorsoftwareengineer.testing.activity.Click;
import net.seniorsoftwareengineer.testing.activity.Exist;
import net.seniorsoftwareengineer.testing.activity.ExistText;
import net.seniorsoftwareengineer.testing.activity.InsertText;
import net.seniorsoftwareengineer.testing.builder.BuilderConfiguration;
import net.seniorsoftwareengineer.testing.builder.BuilderTestCase;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.config.TestProperty;
import net.seniorsoftwareengineer.testing.configuration.Configuration;
import net.seniorsoftwareengineer.testing.controller.PageTestingRest;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.utility.JsonUtils;

@RunWith(SpringRunner.class)
@WebMvcTest({ PageTestingRest.class, ChromeTesting.class, TestProperty.class })
@TestPropertySource(properties = "testing.properties")
public class IntegrationTest {
    @Autowired
    TestProperty testProperty;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void start() {
    }

    @AfterEach
    public void end() {
    }

    public void PageTestingRest_configurationOk_url() throws Exception {
	Click click = new Click();
	click.getSelector().setCssSelector("body");
	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.userAgent(testProperty.getUserAgent()).extraConfiguration(testProperty.getExtraConfiguration())
		.driverVersion(testProperty.getDriverVersion()).getConfiguration();
	TestCase test = BuilderTestCase.create().addActivity(click).url(testProperty.getUrl())
		.configuration(configuration).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is2xxSuccessful());
    }

    public void PageTestingRest_configurationNull() throws Exception {
	Click click = new Click();
	Configuration configuration = BuilderConfiguration.create().userAgent(testProperty.getUserAgent())
		.extraConfiguration(testProperty.getExtraConfiguration()).driverVersion(testProperty.getDriverVersion())
		.getConfiguration();
	click.getSelector().setCssSelector("body");
	TestCase test = BuilderTestCase.create().addActivity(click).configuration(configuration).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.['errors']['configuration.browserVersion']['text']", isA(String.class)));
    }

    public void PageTestingRest_browserVersion() throws Exception {
	Configuration configuration = BuilderConfiguration.create().driverVersion(testProperty.getDriverVersion())
		.getConfiguration();
	TestCase test = BuilderTestCase.create().configuration(configuration).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.['errors']['configuration.browserVersion']['text']", isA(String.class)));
    }

    public void PageTestingRest_urlNull() throws Exception {
	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.driverVersion(testProperty.getDriverVersion()).getConfiguration();
	TestCase test = BuilderTestCase.create().configuration(configuration).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.['errors']['url']['text']", isA(String.class)));
    }

    public void PageTestingRest_testsimple() throws Exception {
	Click click = new Click();
	click.getSelector().setCssSelector("input[value='Google Search']");

	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.makeSnapshot().extraConfiguration(testProperty.getExtraConfiguration())
		.driverVersion(testProperty.getDriverVersion()).useJQuery().getConfiguration();
	TestCase test = BuilderTestCase.create().url(testProperty.getUrl()).addActivity(click)
		.configuration(configuration).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(200));
    }

    public void PageTestingRest_textToInsert() throws Exception {

	InsertText insertText = new InsertText();
	insertText.setType("InsertText");
	((InsertText) insertText).setTextToInsert("test");
	insertText.getSelector().setCssSelector("[name='q']");
//	BrowserVersion, DriverVersion, url, activities, InsertText

	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.driverVersion(testProperty.getDriverVersion()).getConfiguration();
	TestCase test = BuilderTestCase.create().configuration(configuration).addActivity(insertText)
		.url(testProperty.getUrl()).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(500));
    }

    public void PageTestingRest_exist() throws Exception {
	Exist exist = new Exist();
	exist.setType("Exist");
	exist.getSelector().setCssSelector("[name='q']");
//	BrowserVersion, DriverVersion, url, activities, Exist
	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.driverVersion(testProperty.getDriverVersion()).getConfiguration();
	TestCase test = BuilderTestCase.create().configuration(configuration).addActivity(exist)
		.url(testProperty.getUrl()).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(200));
    }

    public void PageTestingRest_existText() throws Exception {
	ExistText exist = new ExistText();
	exist.setType("ExistText");
	exist.getSelector().setCssSelector("[name='q']");
//	BrowserVersion, DriverVersion, url, activities, ExistText

	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.driverVersion(testProperty.getDriverVersion()).getConfiguration();
	TestCase test = BuilderTestCase.create().configuration(configuration).addActivity(exist)
		.url(testProperty.getUrl()).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(200));
    }

}
