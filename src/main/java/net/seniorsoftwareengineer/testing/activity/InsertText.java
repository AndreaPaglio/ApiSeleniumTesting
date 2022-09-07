package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
 * InsertText extend Activity for simulate insert text in a inputbox
 *
 */
@Valid
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "InsertText extend Activity for simulate insert text in a inputbox", discriminator = "type")
public class InsertText extends Activity implements ActivityAction, Serializable {
    @ApiModelProperty(position = 1, hidden = false, notes = "InsertText", allowableValues = "InsertText", required = true)
    @JsonProperty("type")
    private String type;

    @NotNull(message = "Text to insert must be not empty")
    @ApiModelProperty(position = 2, hidden = false, notes = "textToInsert", required = true)
    @JsonProperty("textToInsert")
    private String textToInsert;

    @JsonCreator
    public InsertText() {
	this.parentId = new ArrayList<String>();
    }

    @JsonCreator(mode = Mode.DEFAULT)
    public InsertText(@JsonProperty("selector") SelectorCss selector) {
	this.type = TypeActivity.INSERT_TEXT.name();
	this.selector = selector;
    }

    @JsonCreator(mode = Mode.DEFAULT)
    public InsertText(@JsonProperty("type") String type, @JsonProperty("selector") SelectorCss selector) {
	this.type = type;
	this.selector = selector;
    }

    @Override
    public void execute(Optional<WebDriver> driver) throws TestingException {
	setDriver(driver);
	final WebElement webElement = testService.getElement(driver, getSelector().getCssSelector());
	log.info("InsertText: {}", getSelector().getCssSelector());
	try {
	    if ("input".equals(webElement.getTagName())) {
		webElement.sendKeys(textToInsert);
	    } else {
		final Actions actions = new Actions(driver.get());
		actions.moveToElement(webElement, 0, 0);
		actions.moveByOffset(testService.random(5), testService.random(5)).sendKeys(textToInsert).build()
			.perform();
	    }
	} catch (Exception e) {
	    close();
	    throw new TestingException("Cannot insert text in specific element", InsertText.class.getName());
	}
    }

    @Override
    public String toString() {
	return "InsertText [type=" + type + ", textToInsert=" + textToInsert + ", selectorCss=" + selector
		+ ", webElements=" + webElements + ", idx=" + idx + ", parentId=" + parentId + ", pageToTest="
		+ pageToTest + "]";
    }

}
