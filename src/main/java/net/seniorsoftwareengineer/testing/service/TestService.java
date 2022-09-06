package net.seniorsoftwareengineer.testing.service;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Test interface for implements a service
 *
 */
public interface TestService {
    public WebElement getElement(Optional<WebDriver> driver, String cssSelectorMainPage);

    public Integer random(int max);

    public void skip(final WebDriver driver, String cssSelector);

    public void timeOut(WebDriver driver);
}
