package net.seniorsoftwareengineer.testing.browser.option;

import org.openqa.selenium.chrome.ChromeOptions;

public interface OptionTest {
	public ChromeOptions configure();

	void setHeadless();

	void setRemoteDebuggingPort();

}
