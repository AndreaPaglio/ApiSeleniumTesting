package net.seniorsoftwareengineer.testing.builder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.activity.TestingException;
import net.seniorsoftwareengineer.testing.entitydom.Element;

@Slf4j
@Component
public class ChromeTesting implements Test {
	private static final int WAIT_TIME_SLEEPING = 4000;
	private WebDriver driver = null;
	private Configuration configuration;
	
	public ChromeTesting() {
		super();
	}

	public Test configure(ChromeOptions options, Configuration configuration) throws Exception {
		WebDriverManager.chromedriver().browserVersion(configuration.getBrowserVersion()).driverVersion(configuration.getDriverVersion()).setup();
		this.driver = new ChromeDriver(options);
		this.configuration = configuration;
		if(configuration.getEnableThirdPartyCookies()) {
			enableThirdPartyCookies();
		}
		return this;
	}

	@Override
	public Test goInfiniteScroll() {
		final JavascriptExecutor webPage = (JavascriptExecutor) driver;
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
	public void takeSnapShot(String fileWithPath) throws TestingException {
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File destFile = new File(fileWithPath);
			FileUtils.copyFile(srcFile, destFile);
		} catch (Exception e) {
			driver.close();
			throw new TestingException(e.getMessage(), "takeSnapshot");
		}
	}

	@Override
	public Test enableJquery() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"var headID = document.getElementsByTagName('head')[0]; var newScript = document.createElement('script');newScript.type = 'text/javascript'; newScript.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';headID.appendChild(newScript);");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Function<WebDriver, Boolean> jQueryAvailable = WebDriver -> ((Boolean) js
				.executeScript("return (typeof jQuery != \'undefined\')"));
		wait.until(jQueryAvailable);
//		js.executeScript("$.noConflict();");
		return this;
	}

	@Override
	public String executeSelector(String selector) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (StringUtils.isNotBlank(selector)) {
			String w = (String) js.executeScript("return " + selector);
			return w;
		}
		return null;
	}

	@Override
	public Test enableThirdPartyCookies() throws TestingException {
		ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());

		// Activate Browser window
		driver.switchTo().window(driver.getWindowHandle());

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
				if (driver.getWindowHandles().size() > windowHandles.size())
					break;
			}

			// Enable Third Party Cookies
			if (driver.getWindowHandles().size() > windowHandles.size()) {
				driver.close();
				windowHandles = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
				final List list = driver.findElements(By.id("cookie-controls-toggle"));
				final Optional<WebElement> selectedCookieControlsToggle = driver
						.findElements(By.id("cookie-controls-toggle")).stream()
						.filter(x -> x.getAttribute("checked") != null).findFirst();
				Optional.ofNullable(selectedCookieControlsToggle).get().get().click();
			}
		} catch (Exception e) {
			driver.close();
			throw new TestingException("Error when enable Third Parties Cookies", "enableThirdPartyCookies");
		}
		
		return this;
	}

	@Override
	public void analyze(Element page) throws TestingException {
		if(StringUtils.isNotEmpty(page.getUrl()) || StringUtils.isNotEmpty(page.getValue())){
			String url = page.getUrl();
			if (StringUtils.isEmpty(url)) {
				url = page.getValue();
			}
			driver.get(url);
			if (StringUtils.isEmpty(page.getIdx())) {
				page.setIdx(UUID.randomUUID().toString());
			}
			page.setPageToTest(this);
			if (page.getConfiguration().getUseJQuery()) {
				enableJquery();
			}
			
			page.execute(driver);
			driver.close();
		}
	}

	@Override
	public void close() {
		driver.close();
	}


	@Override
	public Boolean goOneStepInfiniteScroll() {
		final JavascriptExecutor webPage = (JavascriptExecutor) driver;
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

}
