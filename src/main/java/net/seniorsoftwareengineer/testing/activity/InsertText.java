package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.entitydom.Element;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.service.TestService;
import net.seniorsoftwareengineer.testing.service.TestServiceImpl;
import net.seniorsoftwareengineer.testing.service.utility.WebPageUtility;

/**
 *  InsertText extend Activity for simulate insert text in a inputbox
 *
 */
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "InsertText extend Activity for simulate insert text in a inputbox",discriminator = "type")
public class InsertText extends Activity implements ActivityAction, Serializable {
	@ApiModelProperty(position = 1, hidden =  false, notes = "InsertText", allowableValues = "InsertText", required = true)
	@JsonProperty("type")
	private String type;
	
	@ApiModelProperty(position = 2, hidden=false, notes = "textToInsert", required = true)
	@JsonProperty("textToInsert")
	private String textToInsert;

	@JsonCreator
	public InsertText() {
		this.parentId = new ArrayList<String>();
	}

	@JsonCreator(mode = Mode.DEFAULT)
	public InsertText(@JsonProperty("type") String type, @JsonProperty("elementHtml") Element element,
			@JsonProperty("info") List<Element> info) {
		super(element);
		this.type = type;
	}

	@Override
	public void execute(WebDriver driver) throws TestingException {
		final WebElement webElement = testService.getElement(driver,
				getElementHtml().getSelector().getCssSelector());
		log.info("InsertText: {}", getElementHtml().getSelector().getCssSelector());
		try {
			if("input".equals(webElement.getTagName())){
				webElement.sendKeys(textToInsert);
			} 
			else{
				final Actions actions = new Actions(driver);
				actions.moveToElement(webElement, 0, 0);
				actions.moveByOffset(WebPageUtility.random(5), WebPageUtility.random(5)).sendKeys(textToInsert).build().perform();
			}
		} catch (Exception e) {
			driver.close();
			throw new TestingException("Cannot insert text in specific element", InsertText.class.getName());
		}
	}

	@Override
	public String toString() {
		return "InsertText [type=" + type + ", textToInsert=" + textToInsert + ", elementHtml=" + elementHtml
				+ ", webElements=" + webElements + ", idx=" + idx + ", parentId=" + parentId + ", info=" + info
				+ ", pageToTest=" + pageToTest + "]";
	}

	
	
}
