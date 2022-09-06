package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;

/**
 * Action have to used if you want search in one DOM element. Useful for cascade
 * search Not use now, for future implementation
 */
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "Action have to used if you want search in one DOM element. Useful for cascade search")
public class GetElement extends Activity implements ActivityAction, Serializable {
    @JsonProperty("type")
    protected String type;

    @JsonCreator
    public GetElement(@JsonProperty("elementHtml") TestCase element) {
	super(element);
	webElements = new ArrayList<WebElement>();
    }

    @JsonCreator
    public GetElement(@JsonProperty("type") String type, @JsonProperty("elementHtml") TestCase element) {
	super(element);
	this.type = type;
	webElements = new ArrayList<WebElement>();
    }

    @Override
    public void execute(Optional<WebDriver> driver) {
	this.setDriver(driver);
	final WebElement webElement = testService.getElement(driver, getSelector().getCssSelector());
	this.webElements.add(webElement);
    }

    @Override
    public String toString() {
	return "GetElementActivity [selectorCss=" + selector + ", webElements=" + webElements + "]";
    }

}
