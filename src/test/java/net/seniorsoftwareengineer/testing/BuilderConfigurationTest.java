package net.seniorsoftwareengineer.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;

import net.seniorsoftwareengineer.testing.builder.BuilderConfiguration;
import net.seniorsoftwareengineer.testing.config.TestProperty;
import net.seniorsoftwareengineer.testing.configuration.Configuration;

@WebMvcTest({ TestProperty.class })
@TestPropertySource(properties = "testing.properties")
public class BuilderConfigurationTest {
    Configuration configuration;

    @Autowired
    TestProperty testProperty;

    @Test
    public void create() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.getConfiguration(), new Configuration());
    }

    @Test
    public void useJQuery() {
	BuilderConfiguration builder = BuilderConfiguration.create().useJQuery();
	assertEquals(builder.getConfiguration().getUseJQuery(), Boolean.TRUE);
    }

    @Test
    public void makeSnapshot() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.getConfiguration().getMakeSnapshot(), Boolean.FALSE);
	assertEquals(builder.makeSnapshot().getConfiguration().getMakeSnapshot(), Boolean.TRUE);
    }

    @Test
    public void remoteDebuggingPort() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.getConfiguration().getRemoteDebuggingPort(), Boolean.FALSE);
	assertEquals(builder.remoteDebuggingPort().getConfiguration().getRemoteDebuggingPort(), Boolean.TRUE);
    }

    @Test
    public void headless() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.getConfiguration().getHeadless(), Boolean.FALSE);
	assertEquals(builder.headless().getConfiguration().getHeadless(), Boolean.TRUE);
    }

    @Test
    public void browserVersion() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.browserVersion(testProperty.getBrowserVersion()).getConfiguration().getBrowserVersion(),
		testProperty.getBrowserVersion());
    }

    @Test
    public void enableThirdPartyCookies() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.getConfiguration().getEnableThirdPartyCookies(), Boolean.FALSE);
	assertEquals(builder.enableThirdPartyCookies().getConfiguration().getEnableThirdPartyCookies(), Boolean.TRUE);
    }

    @Test
    public void userAgent() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.userAgent(testProperty.getUserAgent()).getConfiguration().getUserAgent(),
		testProperty.getUserAgent());
    }

    @Test
    public void driverVersion() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.driverVersion(testProperty.getDriverVersion()).getConfiguration().getDriverVersion(),
		testProperty.getDriverVersion());
    }

    @Test
    public void singleProcess() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(Boolean.TRUE, builder.singleProcess().getConfiguration().getSingleProcess());
    }

    @Test
    public void extraConfiguration() {
	BuilderConfiguration builder = BuilderConfiguration.create();
	assertEquals(builder.extraConfiguration(testProperty.getExtraConfiguration()).getConfiguration()
		.getExtraConfiguration(), testProperty.getExtraConfiguration());
    }

}
