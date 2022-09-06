package net.seniorsoftwareengineer.testing;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.seniorsoftwareengineer.testing.activity.Activity;
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

    private TestCase message;

    @BeforeEach
    public void start() {
	message = new TestCase();
    }

    @AfterEach
    public void end() {
    }

    @Test
    public void PageTestingRest_configurationOk_url() throws Exception {
	Click click = new Click();
	click.getSelector().setCssSelector("body");
	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.driverVersion(testProperty.getDriverVersion()).getConfiguration();
	TestCase test = BuilderTestCase.create().addActivity(click).url(testProperty.getUrl())
		.configuration(configuration).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void PageTestingRest_configurationNull() throws Exception {
	Click click = new Click();
	click.getSelector().setCssSelector("body");
	TestCase test = BuilderTestCase.create().addActivity(click).getTestCase();
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(test))
		.contentType(testProperty.getApiContentType())).andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.['errors']['configuration.browserVersion']['text']", isA(String.class)));
    }

    @Test
    public void PageTestingRest_browserVersion() throws Exception {
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(message))
		.contentType(testProperty.getApiContentType())).andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.['errors']['configuration.browserVersion']['text']", isA(String.class)));
    }

    @Test
    public void PageTestingRest_urlNull() throws Exception {
	message.getConfiguration().setBrowserVersion(testProperty.getBrowserVersion());
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(message))
		.contentType(testProperty.getApiContentType())).andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.['errors']['url']['text']", isA(String.class)));
    }

    @Test
    public void PageTestingRest_testsimple() throws Exception {
	message.getConfiguration().setBrowserVersion(testProperty.getBrowserVersion());
	message.setUrl(testProperty.getUrl());
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(message))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(200));
    }

    @Test
    public void PageTestingRest_textToInsert() throws Exception {
	message.getConfiguration().setBrowserVersion(testProperty.getBrowserVersion());
	message.getConfiguration().setUseJQuery(true);
	List<Activity> list = new ArrayList<Activity>();
	InsertText insertText = new InsertText();
	insertText.setType("InsertText");
	((InsertText) insertText).setTextToInsert("test");
	insertText.getSelector().setCssSelector("[name='q']");
	message.getElementDOM().getSelector().setCssSelector("[name='q']");

	list.add(insertText);

	message.setActivities(list);
	message.setUrl(testProperty.getUrl());
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(message))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(500));
    }

    @Test
    public void PageTestingRest_click() throws Exception {
	message.getConfiguration().setBrowserVersion(testProperty.getBrowserVersion());
	List<Activity> list = new ArrayList<Activity>();

	Click click = new Click();
	click.setType("Click");
	click.getSelector().setCssSelector("[name='q']");
	message.getElementDOM().getSelector().setCssSelector("[name='q']");

	list.add(click);

	message.setActivities(list);
	message.setUrl(testProperty.getUrl());
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(message))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(200));
    }

    @Test
    public void PageTestingRest_exist() throws Exception {
	message.getConfiguration().setBrowserVersion(testProperty.getBrowserVersion());
	List<Activity> list = new ArrayList<Activity>();

	Exist exist = new Exist();
	exist.setType("Exist");
	exist.getSelector().setCssSelector("[name='q']");
	message.getElementDOM().getSelector().setCssSelector("[name='q']");
	list.add(exist);

	message.setActivities(list);
	message.setUrl(testProperty.getUrl());
	mvc.perform(get(testProperty.getApiTest()).content(JsonUtils.convertObjectToJsonString(message))
		.contentType(testProperty.getApiContentType())).andExpect(status().is(200));
    }

    @Test
    public void PageTestingRest_existText() throws Exception {
	message.getConfiguration().setBrowserVersion(testProperty.getBrowserVersion());
	List<Activity> list = new ArrayList<Activity>();

	ExistText exist = new ExistText();
	exist.setType("ExistText");
	exist.getSelector().setCssSelector("[name='q']");
	message.getElementDOM().getSelector().setCssSelector("[name='q']");
	list.add(exist);

	message.setActivities(list);
	message.setUrl(testProperty.getUrl());
	mvc.perform(get("/test").content(JsonUtils.convertObjectToJsonString(message)).contentType("application/json"))
		.andExpect(status().is(200));
    }

}
