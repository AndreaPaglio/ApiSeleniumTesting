package net.seniorsoftwareengineer.testing.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Service used for: - time out request - be more kindly with browser request -
 * get element
 *
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    public TestServiceImpl() {
    }

    public Integer random(int max) {
	final Double random = new Double(Math.random() * max);
	if (random < 1) {
	    return 1;
	}
	return random.intValue();
    }

    public void skip(final WebDriver driver, String cssSelector) {
	final Actions actions = new Actions(driver);
	actions.moveToElement(driver.findElement(By.cssSelector(cssSelector)), random(5), random(5));
	actions.moveByOffset(random(5), random(5)).click().build().perform();
    }

    public void timeOut(WebDriver driver) {
	try {
	    final long timeout = (random(20000));
//			final WebDriverWait wait = new WebDriverWait(driver, timeout);
//			wait.pollingEvery(Duration.ofSeconds(timeout));

	    Thread.sleep(timeout);
	} catch (final InterruptedException e) {
	    log.error("Thread Sleep!");
	}
    }

    public WebElement getElement(Optional<WebDriver> driver, String cssSelectorMainPage) {
	try {
	    WebDriver chromeDriver = driver.get();
	    final Wait<WebDriver> wait = new FluentWait<WebDriver>(chromeDriver).withTimeout(15, TimeUnit.SECONDS)
		    .pollingEvery(1, TimeUnit.SECONDS).ignoring(Exception.class);
	    final WebElement webElement = wait.until(new Function<WebDriver, WebElement>() {
		@Override
		public WebElement apply(WebDriver driver) {
		    return driver.findElement(By.cssSelector(cssSelectorMainPage));
		}
	    });
	    return webElement;
	} catch (final Exception e) {
	    return null;
	}
    }

}
