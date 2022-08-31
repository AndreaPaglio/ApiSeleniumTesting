package net.seniorsoftwareengineer.testing.option;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.chrome.ChromeOptions;

import net.seniorsoftwareengineer.testing.browser.option.OptionTest;
import net.seniorsoftwareengineer.testing.builder.Configuration;


/**
 * Option test for Chrome Browser
 * Implement OptionTest interface like all class browser specific
 *
 */
public class OptionChromeUseToTesting implements OptionTest {
	private final ChromeOptions options;
	private Configuration configuration;
	
	public OptionChromeUseToTesting(Configuration configuration) {
		super();
		this.options = new ChromeOptions();
		this.configuration = configuration;
	}

	@Override
	public ChromeOptions configure() {
		options.addArguments("start-maximized");
		options.addArguments("--no-sandbox");
		if(configuration.getRemoteDebuggingPort()) {
			setRemoteDebuggingPort();
		}
		if (StringUtils.isNotEmpty(configuration.getUserAgent())) {
			options.addArguments("user-agent=\"" + configuration.getUserAgent() + "\"");
		}
		options.addArguments("--disable-gpu");

		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
		options.addArguments("lang=en-GB");
		options.addArguments("-incognito");

		if(configuration.getHeadless()) {
			setHeadless();
		}
		
		if(configuration.getSingleProcess()) {
			setSingleProcess();
		}
		
		options.addArguments("start-maximized");
		options.addArguments("--disable-blink-features");
//		options.addArguments("--whitelisted-ips");
		options.addArguments("--disable-blink-features=AutomationControlled");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("source", "Object.defineProperty(navigator, 'webdriver', { get: () => undefined })");
		options.addArguments("--incognito", "--disable-blink-features=AutomationControlled");
		
		if(StringUtils.isNotEmpty(configuration.getExtraConfiguration())) {
			String[] extraConfig = configuration.getExtraConfiguration().split(";");
			for (int i = 0; i < extraConfig.length; i++) {
				options.addArguments(extraConfig[i]);
			}
		}
		
		return options;
	}
	
	
	public void setSingleProcess() {
		options.addArguments("--single-process");
	}
	
	@Override
	public void setRemoteDebuggingPort() {
		options.addArguments("--remote-debugging-port=9222");
	}
	
	@Override
	public void setHeadless() {
		options.addArguments("--headless");
	}
}
