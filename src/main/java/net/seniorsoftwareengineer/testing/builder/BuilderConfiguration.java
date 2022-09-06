package net.seniorsoftwareengineer.testing.builder;

import javax.validation.Valid;

import lombok.Data;
import net.seniorsoftwareengineer.testing.configuration.Configuration;

/**
 * Builder configuration Define if you want inject JQuery script for performing
 * css selecting, headless mode, define browser version installed on environment
 *
 */
@Data
@Valid
public class BuilderConfiguration {
    Configuration configuration;

    public static BuilderConfiguration create() {
	BuilderConfiguration builderConfiguration = new BuilderConfiguration();
	builderConfiguration.configuration = new Configuration();
	return builderConfiguration;
    }

    public BuilderConfiguration useJQuery() {
	configuration.setUseJQuery(Boolean.TRUE);
	return this;
    }

    public BuilderConfiguration makeSnapshot() {
	configuration.setMakeSnapshot(Boolean.TRUE);
	return this;
    }

    public BuilderConfiguration remoteDebuggingPort() {
	configuration.setRemoteDebuggingPort(Boolean.TRUE);
	return this;
    }

    public BuilderConfiguration headless() {
	configuration.setHeadless(Boolean.TRUE);
	return this;
    }

    public BuilderConfiguration browserVersion(String browserVersion) {
	configuration.setBrowserVersion(browserVersion);
	return this;
    }

    public BuilderConfiguration enableThirdPartyCookies() {
	configuration.setEnableThirdPartyCookies(Boolean.TRUE);
	return this;
    }

    public BuilderConfiguration userAgent(String userAgent) {
	configuration.setUserAgent(userAgent);
	return this;
    }

    public BuilderConfiguration driverVersion(String driverVersion) {
	configuration.setDriverVersion(driverVersion);
	return this;
    }

    public BuilderConfiguration singleProcess() {
	configuration.setSingleProcess(Boolean.TRUE);
	return this;
    }

    public BuilderConfiguration extraConfiguration(String extraConfiguration) {
	configuration.setExtraConfiguration(extraConfiguration);
	return this;
    }

    public Configuration getConfiguration() {
	return configuration;
    }

}
