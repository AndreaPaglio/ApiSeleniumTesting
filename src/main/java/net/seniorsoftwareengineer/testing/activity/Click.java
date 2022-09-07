package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.entitydom.SelectorCss;
import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * Choose this action if you want click on DOM element
 *
 */

@Valid
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "Choose this action if you want click on DOM element", discriminator = "type")
public class Click extends Activity implements ActivityAction, Serializable {
    @ApiModelProperty(required = true, notes = "Choose this action if you want click on DOM element", value = "Click", example = "Click")
    @JsonProperty("type")
    private String type;

    @JsonCreator
    public Click() {
	this.type = TypeActivity.CLICK.name();
	this.parentId = new ArrayList<String>();
    }

    @JsonCreator
    public Click(@JsonProperty("selector") SelectorCss selector) {
	this.type = TypeActivity.CLICK.name();
	this.parentId = new ArrayList<String>();
	this.selector = selector;
    }

    @JsonCreator(mode = Mode.DEFAULT)
    public Click(@JsonProperty("type") String type, @JsonProperty("selector") SelectorCss selector) {
	this.type = type;
	this.selector = selector;
    }

    @Override
    public void execute(Optional<WebDriver> driver) throws TestingException {
	this.setDriver(driver);
	final WebElement webElement = testService.getElement(driver, getSelector().getCssSelector());
	try {
	    try {
		final Actions actions = new Actions(driver.get());
		actions.moveToElement(webElement, 0, 0);
		actions.moveByOffset(testService.random(5), testService.random(5)).click().build().perform();

	    } catch (Exception e) {
		webElement.click();
	    }
	} catch (Exception e) {
	    close();
	    throw new TestingException(getSelector().getCssSelector(), Click.class.getName());
	}
    }

}
