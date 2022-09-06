package net.seniorsoftwareengineer.testing.activity.interfaces;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

import net.seniorsoftwareengineer.testing.activity.Activity;
import net.seniorsoftwareengineer.testing.exception.TestingException;

public interface Test {
    public Test goInfiniteScroll() throws NoSuchElementException;

    public Boolean goOneStepInfiniteScroll() throws NoSuchElementException;

    public Test enableThirdPartyCookies() throws Exception, NoSuchElementException;

    public void close() throws NoSuchElementException;

    Test enableJquery() throws NoSuchElementException;

    String executeSelector(String jsExecutive) throws NoSuchElementException;

    public void takeSnapShot(String fileWithPath) throws TestingException, NoSuchElementException;

    public Optional<WebDriver> getDriver();

    public void execute(String id, List<Activity> activities) throws TestingException;
}
