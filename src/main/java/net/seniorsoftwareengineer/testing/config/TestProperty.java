package net.seniorsoftwareengineer.testing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Testing configuration properties for JUnit test
 *
 */
@Component
@Getter
@PropertySource("classpath:testing.properties")
public class TestProperty {

    @Value("${test.activity.click}")
    String click;

    @Value("${test.activity.exist}")
    String exist;

    @Value("${test.activity.existText}")
    String existText;

    @Value("${test.activity.insertText}")
    String insertText;

    @Value("${test.url}")
    String url;

    @Value("${test.api.test}")
    String apiTest;

    @Value("${test.api.contentType}")
    String apiContentType;

    @Value("${test.configuration.browserVersion}")
    String browserVersion;

    @Value("${test.configuration.userAgent}")
    String userAgent;

    @Value("${test.configuration.driverVersion}")
    String driverVersion;

    @Value("${test.configuration.extraConfiguration}")
    String extraConfiguration;
}
