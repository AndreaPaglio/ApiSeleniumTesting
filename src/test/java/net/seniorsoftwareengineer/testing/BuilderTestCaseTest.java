package net.seniorsoftwareengineer.testing;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;

import net.seniorsoftwareengineer.testing.activity.Click;
import net.seniorsoftwareengineer.testing.builder.BuilderConfiguration;
import net.seniorsoftwareengineer.testing.builder.BuilderTestCase;
import net.seniorsoftwareengineer.testing.config.TestProperty;
import net.seniorsoftwareengineer.testing.configuration.Configuration;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;

@WebMvcTest({ TestProperty.class })
@TestPropertySource(properties = "testing.properties")
class BuilderTestCaseTest {

    @Autowired
    TestProperty testProperty;

    @Test
    public void create() {
	BuilderTestCase factory = BuilderTestCase.create();
	assertNotNull(factory);
    }

    @Test
    public void createTestCase() {
	TestCase test = new TestCase();
	String id = UUID.randomUUID().toString();
	test.setIdx(id);
	BuilderTestCase factory = BuilderTestCase.create(test);
	assertTrue(id.equals(factory.getTestCase().getIdx()));
    }

    @Test
    public void url() throws Exception {
	BuilderTestCase factory = BuilderTestCase.create().url(testProperty.getUrl());
	assertNotNull(factory.getTestCase());
	assertTrue(testProperty.getUrl().equals(factory.getTestCase().getUrl()));
    }

    @Test
    public void configuration() throws Exception {
	BuilderConfiguration builder = BuilderConfiguration.create();
	BuilderTestCase factory = BuilderTestCase.create().configuration(builder.getConfiguration());
	assertEquals(builder.getConfiguration(), factory.getTestCase().getConfiguration());
    }

    @Test
    public void addActivity() throws TestingException {
	Click click = new Click();
	String id = UUID.randomUUID().toString();
	click.setIdx(id);
	BuilderTestCase factory = BuilderTestCase.create().addActivity(click);
	assertEquals(1, factory.getTestCase().getActivities().size());
	assertEquals(click, factory.getTestCase().getActivities().get(0));
    }

    public void execute() throws TestingException {
	assertThatExceptionOfType(TestingException.class).isThrownBy(() -> {
	    BuilderTestCase.create().execute();
	});

	Configuration configuration = BuilderConfiguration.create().browserVersion(testProperty.getBrowserVersion())
		.getConfiguration();
	TestCase testCase = BuilderTestCase.create().url(testProperty.getUrl()).configuration(configuration)
		.getTestCase();
	assertThatNoException().isThrownBy(() -> {
	    BuilderTestCase.create(testCase).execute();
	});

	assertEquals(testCase.getUrl(), testProperty.getUrl());
	assertEquals(configuration, testCase.getConfiguration());

    }

    @Test
    public void getTestCase() {
	BuilderTestCase builder = BuilderTestCase.create();
	TestCase testCase = builder.getTestCase();
	assertNotNull(testCase);
    }

    @Test
    public void getException() {
	BuilderTestCase testCase = BuilderTestCase.create();
	assertNull(testCase.getException());
    }
}
