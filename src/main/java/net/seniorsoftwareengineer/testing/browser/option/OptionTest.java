package net.seniorsoftwareengineer.testing.browser.option;

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Class for configuration options test
 *
 */
public interface OptionTest {
    public ChromeOptions configure();

    void setHeadless();

    void setRemoteDebuggingPort();

}
