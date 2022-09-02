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
 * Choose this action if you want click on DOM element
 *
 */
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "Choose this action if you want click on DOM element", discriminator = "type")
public class Click extends Activity implements ActivityAction, Serializable {
	@ApiModelProperty(required = true, notes = "Choose this action if you want click on DOM element", value="Click", example = "Click")
	@JsonProperty("type")
	private String type;
	
	@JsonCreator
	public Click() {
		this.parentId = new ArrayList<String>(); 
	}

	@JsonCreator(mode = Mode.DEFAULT)
	public Click(@JsonProperty("type") String type, @JsonProperty("elementHtml") Element element,
			@JsonProperty("info") List<Element> info) {
		super(element);
		this.type = type;
	}

	@Override
	public void execute(WebDriver driver) throws TestingException {
		final WebElement webElement = testService.getElement(driver,
				getElementHtml().getSelector().getCssSelector());
		try {
			try {
				final Actions actions = new Actions(driver);
				actions.moveToElement(webElement, 0, 0);
				actions.moveByOffset(WebPageUtility.random(5), WebPageUtility.random(5)).click().build().perform();
				
			} catch (Exception e) {
				webElement.click();
			}
		} catch (Exception e) {
			    driver.close();
				throw new TestingException(getElementHtml().getSelector().getCssSelector(), Click.class.getName());
		}
	}

}
