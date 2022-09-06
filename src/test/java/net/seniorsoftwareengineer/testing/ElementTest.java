package net.seniorsoftwareengineer.testing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;

import net.seniorsoftwareengineer.testing.browser.option.OptionChromeTesting;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.config.TestProperty;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;

@WebMvcTest({ ChromeTesting.class, TestProperty.class })
@TestPropertySource(properties = "testing.properties")
class ElementTest {
    OptionChromeTesting optionsChrome;
    ChromeTesting chromeUseToTesting;
    TestCase message;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
	message = new TestCase();
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    void testElement_execute() throws Exception {
	message.getConfiguration().setBrowserVersion(null);
    }

}
