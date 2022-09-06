package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

import net.seniorsoftwareengineer.testing.entitydom.SelectorCss;
import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * Interface contains all method can you apply
 *
 */
public interface ActivityAction extends Serializable {
    public void execute(Optional<WebDriver> driver) throws TestingException;

    public void setSelector(SelectorCss css);

    public void takeSnapshot(String path) throws TestingException;

    public void close();

}
