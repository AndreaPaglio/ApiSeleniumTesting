package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * Actions to use if you want list all elements for one css selector. Useful for cascade search
 * Not use now, for future implementation
 */
@Data
@Slf4j
@Configurable
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "Actions to use if you want list all elements for one css selector. Useful for cascade search")
public class GetElements extends Activity implements ActivityAction, Serializable {
	@JsonProperty("type")
	protected String type;

	@JsonProperty("childActivities")
	protected List<Activity> childActivities = new ArrayList<Activity>();

	@JsonCreator
	public GetElements() {
		this.parentId = new ArrayList<String>();
	}

	@JsonCreator
	public GetElements(@JsonProperty("type") String type, @JsonProperty("elementHtml") TestCase element,
			@JsonProperty("childActivities") List<Activity> childActivities) {
		super(element);
		this.childActivities = childActivities;
		this.type = type;
	}

	@Override
	public void execute(Optional<WebDriver> driver) throws TestingException {
		Integer counter = 0;
		this.setDriver(driver);
		WebDriver chromeDriver = driver.get();
		do {
			
			webElements = new ArrayList<WebElement>();
			if (StringUtils.isNotEmpty(getElementHtml().getSelector().getCssSelector())) {
				webElements = chromeDriver.findElements(By.cssSelector(getElementHtml().getSelector().getCssSelector()));
			} else if (StringUtils.isNotEmpty(getElementHtml().getSelector().getClasses())) {
				webElements = chromeDriver.findElements(By.className(getElementHtml().getSelector().getClasses()));
			} else if (StringUtils.isNotEmpty(getElementHtml().getSelector().getId())) {
				webElements = chromeDriver.findElements(By.id(getElementHtml().getSelector().getId()));
			}
			List<WebElement> analyzeElements = new ArrayList<WebElement>();
			analyzeElements = webElements.subList(counter, webElements.size() - 1);
			for (WebElement el : analyzeElements) {
				TestCase e = null;

				e = (TestCase) getElementHtml().clone();
				e.setElement(el);
				counter++;
				for (Activity act : childActivities) {
					if (!getIsNewPage()) {
						act.setIdx(getIdx());
					} else {
						act.setIdx(UUID.randomUUID().toString());
					}

					act.setParentId(getParentId());
					act.setElementHtml(e);
					act.getParentId().add(getIdx());
					act.setPageToTest(getPageToTest());
					act.execute(driver);
				}
			}
		} while (getElementHtml().getPagination().getIsInfiniteScroll() && getPageToTest().goOneStepInfiniteScroll());

	}

	@Override
	public String toString() {
		return "GetElements [type=" + type + ", childActivities=" + childActivities + "]";
	}

}
