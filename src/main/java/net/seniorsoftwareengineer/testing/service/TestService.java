package net.seniorsoftwareengineer.testing.service;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface TestService {
	public WebElement getElement(Optional<WebDriver> driver, String cssSelectorMainPage);
}
