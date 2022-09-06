package net.seniorsoftwareengineer.testing.entitydom;

import java.util.List;

/**
 * Method for save an element
 *
 */
@FunctionalInterface
public interface ElementToSave {
    public List<TestCase> getElementHtmlToSave();
}
