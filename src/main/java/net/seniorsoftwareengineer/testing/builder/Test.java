package net.seniorsoftwareengineer.testing.builder;

import java.util.NoSuchElementException;

import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;

public interface Test {
	public Test goInfiniteScroll() throws NoSuchElementException;

	public Boolean goOneStepInfiniteScroll() throws NoSuchElementException;

	public Test enableThirdPartyCookies() throws Exception, NoSuchElementException;

	public void analyze(TestCase page) throws TestingException, NoSuchElementException;

	public void close() throws NoSuchElementException;

	Test enableJquery() throws NoSuchElementException;

	String executeSelector(String jsExecutive) throws NoSuchElementException;
	
	public void takeSnapShot(String fileWithPath) throws TestingException, NoSuchElementException;
}
