package net.seniorsoftwareengineer.testing.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface TestService {
	public WebElement getElement(WebDriver driver, String cssSelectorMainPage);
}
