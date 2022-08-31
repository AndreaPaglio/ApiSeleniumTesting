package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.WebDriver;

import net.seniorsoftwareengineer.testing.entitydom.Element;

/**
 * Interface contains all method can you apply
 *
 */
public interface ActivityAction extends Serializable {
	public void execute(WebDriver driver) throws TestingException;

	public void setElementHtml(Element e);

	public List<Element> getInfo();
	
	public void takeSnapshot(String path) throws TestingException;

}
