package net.seniorsoftwareengineer.testing.entitydom;

import java.util.List;

@FunctionalInterface
public interface ElementToSave {
	public List<TestCase> getElementHtmlToSave();
}
