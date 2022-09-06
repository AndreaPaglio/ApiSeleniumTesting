package net.seniorsoftwareengineer.testing;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;

import net.seniorsoftwareengineer.testing.activity.Click;
import net.seniorsoftwareengineer.testing.builder.BuilderTestCase;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.config.TestProperty;
import net.seniorsoftwareengineer.testing.configuration.Configuration;
import net.seniorsoftwareengineer.testing.controller.PageTestingRest;
import net.seniorsoftwareengineer.testing.exception.TestingException;

@WebMvcTest({ PageTestingRest.class, ChromeTesting.class, TestProperty.class })
@TestPropertySource(properties = "testing.properties")
class FactoryTest {
    @Autowired
    TestProperty testProperty;

    @Test
    void test() throws Exception {
	Configuration configuration = new Configuration();
	configuration.setBrowserVersion(testProperty.getBrowserVersion());
	Click click = new Click();
	click.getSelector().setCssSelector("body");
	assertThatExceptionOfType(TestingException.class).isThrownBy(() -> {
	    BuilderTestCase.create().configuration(configuration).url("d").addActivity(click).addActivity(click)
		    .execute();
	});

    }

}
