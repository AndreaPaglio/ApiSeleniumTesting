package net.seniorsoftwareengineer.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.seniorsoftwareengineer.testing.activity.Activity;
import net.seniorsoftwareengineer.testing.activity.Click;
import net.seniorsoftwareengineer.testing.activity.Exist;
import net.seniorsoftwareengineer.testing.activity.ExistText;
import net.seniorsoftwareengineer.testing.activity.InsertText;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.controller.PageTestingRest;
import net.seniorsoftwareengineer.testing.entitydom.Element;
import net.seniorsoftwareengineer.testing.service.utility.JsonUtils;

@RunWith(SpringRunner.class)
@WebMvcTest({PageTestingRest.class, ChromeTesting.class})
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void PageTestingRest_configurationNull() throws Exception {
        Element message = new Element();
        message.setConfiguration(null);
        message.setUrl("www.google.com");
        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
          .contentType("application/json"))
          .andExpect(status().is5xxServerError())
          .andExpect(jsonPath("$.['errors']['configuration']", is("Special configuration to be tested")));
    }

    @Test
    public void PageTestingRest_browserVersion()
      throws Exception {
        Element message = new Element();
        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
          .contentType("application/json"))
          .andExpect(status().is5xxServerError())
          .andExpect(jsonPath("$.['errors']['configuration.browserVersion']", is("Browser Version must be not empty")));
    }
    
    @Test
    public void PageTestingRest_urlNull() throws Exception {
        Element message = new Element();
        message.getConfiguration().setBrowserVersion("104.0.5112.102");

        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
          .contentType("application/json"))
          .andExpect(status().is5xxServerError())
          .andExpect(jsonPath("$.['errors']['url']", is("Url cannot be empty")));
    }
    
    @Test
    public void PageTestingRest_testsimple() throws Exception {
        Element message = new Element();
        message.getConfiguration().setBrowserVersion("104.0.5112.102");
        message.setUrl("https://www.google.com");
        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
          .contentType("application/json"))
          .andExpect(status().is(200));
    }
    
    @Test
    public void PageTestingRest_textToInsert() throws Exception {
        Element message = new Element();
        message.getConfiguration().setBrowserVersion("104.0.5112.102");
        message.getConfiguration().setUseJQuery(true);
        List<Activity> list = new ArrayList<Activity>();
        InsertText insertText = new InsertText();
        insertText.setType("InsertText");
        ((InsertText) insertText).setTextToInsert("test");
        insertText.getElementHtml().getSelector().setCssSelector("[name='q']");
        message.getSelector().setCssSelector("[name='q']");
        
        list.add(insertText);
        
		message.setActivities(list);
        message.setUrl("https://www.google.com");
        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
         .contentType("application/json")
          )
          .andExpect(status().is(500));
    }
    
    @Test
    public void PageTestingRest_click() throws Exception {
        Element message = new Element();
        message.getConfiguration().setBrowserVersion("104.0.5112.102");
        List<Activity> list = new ArrayList<Activity>();
        
        Click click = new Click();
        click.setType("Click");
        click.getElementHtml().getSelector().setCssSelector("[name='q']");
        message.getSelector().setCssSelector("[name='q']");
        
        list.add(click);
        
		message.setActivities(list);
        message.setUrl("https://www.google.com");
        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
         .contentType("application/json")
          )
          .andExpect(status().is(200));
    }
    
    @Test
    public void PageTestingRest_exist() throws Exception {
        Element message = new Element();
        message.getConfiguration().setBrowserVersion("104.0.5112.102");
        List<Activity> list = new ArrayList<Activity>();
        
        Exist exist = new Exist();
        exist.setType("Exist");
        exist.getElementHtml().getSelector().setCssSelector("[name='q']");
        message.getSelector().setCssSelector("[name='q']");
        list.add(exist);
        
		message.setActivities(list);
        message.setUrl("https://www.google.com");
        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
         .contentType("application/json")
          )
          .andExpect(status().is(200));
    }
    
    @Test
    public void PageTestingRest_existText() throws Exception {
        Element message = new Element();
        message.getConfiguration().setBrowserVersion("104.0.5112.102");
        List<Activity> list = new ArrayList<Activity>();
        
        ExistText exist = new ExistText();
        exist.setType("ExistText");
        exist.getElementHtml().getSelector().setCssSelector("[name='q']");
        message.getSelector().setCssSelector("[name='q']");
        list.add(exist);
        
		message.setActivities(list);
        message.setUrl("https://www.google.com");
        mvc.perform(get("/test")
          .content(JsonUtils.convertObjectToJsonString(message))
         .contentType("application/json")
          )
          .andExpect(status().is(200));
    }
    
}
