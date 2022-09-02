package net.seniorsoftwareengineer.testing.builder;

import java.io.IOException;

import net.seniorsoftwareengineer.testing.entitydom.Element;
import net.seniorsoftwareengineer.testing.exception.TestingException;

public interface Test {
	public Test goInfiniteScroll();

	public Boolean goOneStepInfiniteScroll();

	public Test enableThirdPartyCookies() throws Exception;

	public void analyze(Element page) throws TestingException;

	public void close();

	Test enableJquery();

	String executeSelector(String jsExecutive);
	
	public void takeSnapShot(String fileWithPath) throws TestingException;
}
