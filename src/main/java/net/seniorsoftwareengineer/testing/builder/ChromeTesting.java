package net.seniorsoftwareengineer.testing.builder;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.activity.Activity;
import net.seniorsoftwareengineer.testing.activity.interfaces.Test;
import net.seniorsoftwareengineer.testing.configuration.Configuration;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.utility.ConstantsTesting;

/**
 * Test implementation for chrome Have to chrome drivers installed on
 * environment
 *
 */
@Slf4j
@Component
public class ChromeTesting implements Test {
    private static final int WAIT_TIME_SLEEPING = 4000;
    private Optional<WebDriver> driver;
    private WebDriver driverChrome;

    public ChromeTesting() {
	super();
	this.driver = Optional.empty();
    }

    public Test configure(ChromeOptions options, Configuration configuration) throws Exception {
	WebDriverManager.chromedriver().browserVersion(configuration.getBrowserVersion())
		.driverVersion(configuration.getDriverVersion()).setup();
	this.driver = Optional.of(new ChromeDriver(options));
	if (configuration.getEnableThirdPartyCookies()) {
	    enableThirdPartyCookies();
	}
	return this;
    }

    @Override
    public Test goInfiniteScroll() throws NoSuchElementException {
	driverChrome = driver.get();
	final JavascriptExecutor webPage = (JavascriptExecutor) driverChrome;
	long heightWindow = (long) webPage.executeScript("return document.body.scrollHeight", "");
	while (true) {
	    webPage.executeScript("window.scrollTo(0,document.body.scrollHeight)", "");
	    try {
		Thread.sleep(WAIT_TIME_SLEEPING);
	    } catch (final InterruptedException e) {
		log.error("Infinite scrolling error {}", e);
	    }

	    final long currentLength = (long) webPage.executeScript("return document.body.scrollHeight", "");
	    if (heightWindow == currentLength) {
		break;
	    }
	    heightWindow = currentLength;
	}
	return this;
    }

    @Override
    public void takeSnapShot(String fileWithPath) throws TestingException, NoSuchElementException {
	try {
	    driverChrome = driver.get();
	    TakesScreenshot scrShot = ((TakesScreenshot) driverChrome);
	    File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
	    File destFile = new File(fileWithPath);
	    FileUtils.copyFile(srcFile, destFile);
	} catch (Exception e) {
	    close();
	    throw new TestingException(e.getMessage(), "takeSnapshot");
	}
    }

    @Override
    public Test enableJquery() throws NoSuchElementException {
	driverChrome = driver.get();
	JavascriptExecutor js = (JavascriptExecutor) driverChrome;
	js.executeScript(
		"var headID = document.getElementsByTagName('head')[0]; var newScript = document.createElement('script');newScript.type = 'text/javascript'; newScript.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';headID.appendChild(newScript);");
	WebDriverWait wait = new WebDriverWait(driverChrome, 30);
	Function<WebDriver, Boolean> jQueryAvailable = WebDriver -> ((Boolean) js
		.executeScript("return (typeof jQuery != \'undefined\')"));
	wait.until(jQueryAvailable);
	return this;
    }

    @Override
    public String executeSelector(String selector) throws NoSuchElementException {
	driverChrome = driver.get();
	JavascriptExecutor js = (JavascriptExecutor) driverChrome;
	if (StringUtils.isNotBlank(selector)) {
	    String w = (String) js.executeScript("return " + selector);
	    return w;
	}
	return null;
    }

    @Override
    public Test enableThirdPartyCookies() throws TestingException, NoSuchElementException {
	driverChrome = driver.get();
	ArrayList<String> windowHandles = new ArrayList<String>(driverChrome.getWindowHandles());

	// Activate Browser window
	driverChrome.switchTo().window(driverChrome.getWindowHandle());

	// Open New Tab Ctrl + T
	Robot robot;
	try {
	    robot = new Robot();
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_T);
	    robot.keyRelease(KeyEvent.VK_T);
	    robot.keyRelease(KeyEvent.VK_CONTROL);

	    // Wait for open new tab
	    final int retries = 100;
	    for (int i = 0; i < retries; i++) {
		Thread.sleep(100);
		if (driverChrome.getWindowHandles().size() > windowHandles.size())
		    break;
	    }

	    // Enable Third Party Cookies
	    if (driverChrome.getWindowHandles().size() > windowHandles.size()) {
		close();
		windowHandles = new ArrayList<String>(driverChrome.getWindowHandles());
		driverChrome.switchTo().window(windowHandles.get(windowHandles.size() - 1));
		final List list = driverChrome.findElements(By.id("cookie-controls-toggle"));
		final Optional<WebElement> selectedCookieControlsToggle = driverChrome
			.findElements(By.id("cookie-controls-toggle")).stream()
			.filter(x -> x.getAttribute("checked") != null).findFirst();
		Optional.ofNullable(selectedCookieControlsToggle).get().get().click();
	    }
	} catch (Exception e) {
	    close();
	    throw new TestingException("Error when enable Third Parties Cookies", "enableThirdPartyCookies");
	}

	return this;
    }

    @Override
    public void close() {
	if (driver != null && driver.isPresent()) {
	    try {
		driver.get().close();
		driver.get().quit();
	    } catch (Exception e) {
		log.error("Error closing session", e);
	    }
	}
    }

    @Override
    public Boolean goOneStepInfiniteScroll() throws NoSuchElementException {
	driverChrome = driver.get();
	final JavascriptExecutor webPage = (JavascriptExecutor) driverChrome;
	long heightWindow = (long) webPage.executeScript("return document.body.scrollHeight", "");
	webPage.executeScript("window.scrollTo(0,document.body.scrollHeight)", "");
	try {
	    Thread.sleep(WAIT_TIME_SLEEPING);
	} catch (final InterruptedException e) {
	    log.error("Infinite scrolling error {}", e);
	}

	final long currentLength = (long) webPage.executeScript("return document.body.scrollHeight", "");
	if (heightWindow == currentLength) {
	    return Boolean.FALSE;
	}
	heightWindow = currentLength;

	return Boolean.TRUE;
    }

    @Override
    public Optional<WebDriver> getDriver() {
	return driver;
    }

    @Override
    public void execute(String id, List<Activity> activities) throws TestingException {
	for (Activity activity : activities) {
	    Activity.newIdx(activity);
	    activity.setPageToTest(this);
	    activity.execute(driver);
	    activity.takeSnapshot(ConstantsTesting.DIR_SNAPSHOT + "/" + id + "/" + activity.getIdx() + ".png");
	}
    }

}
