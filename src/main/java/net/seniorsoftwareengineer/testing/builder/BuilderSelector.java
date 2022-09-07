package net.seniorsoftwareengineer.testing.builder;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import net.seniorsoftwareengineer.testing.entitydom.SelectorCss;

/**
 * Builder for create Selector
 *
 */

@Data
@ApiModel(description = "Builder for create Selector", value = "")
public class BuilderSelector {
    SelectorCss selector;

    public static BuilderSelector create() {
	BuilderSelector builder = new BuilderSelector();
	builder.selector = new SelectorCss();
	return builder;
    }

    public SelectorCss key(String key) {
	this.selector.setKey(key);
	return this.selector;
    }

    public SelectorCss cssSelector(String cssSelector) {
	this.selector.setCssSelector(cssSelector);
	return this.selector;
    }

    public SelectorCss classes(String classes) {
	this.selector.setClasses(classes);
	return this.selector;
    }

    public SelectorCss id(String id) {
	this.selector.setId(id);
	return this.selector;
    }

    public SelectorCss tag(String tag) {
	this.selector.setTag(tag);
	return this.selector;
    }

    public SelectorCss attribute(String attribute) {
	this.selector.setAttribute(attribute);
	return this.selector;
    }

    public SelectorCss jquerySelector(String jquerySelector) {
	this.selector.setJquerySelector(jquerySelector);
	return this.selector;
    }
}
