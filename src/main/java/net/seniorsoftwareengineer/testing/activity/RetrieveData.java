package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.entitydom.Element;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * Usefully class for retrieve data for specific css selector Not use now, for
 * future implementation
 */
@Slf4j
@Service
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "Used to retrieve info from css selector")
public class RetrieveData extends Activity implements ActivityAction, Serializable {

    @JsonProperty("type")
    protected String type;

    @JsonProperty("info")
    protected List<Element> info;

    @JsonCreator
    RetrieveData() {
	this.parentId = new ArrayList<String>();
    }

    @JsonCreator
    public RetrieveData(@JsonProperty("type") String type, @JsonProperty("elementHtml") TestCase element,
	    @JsonProperty("info") List<Element> info) {
	super(element);
	this.info = info;
    }

    @Override
    public void execute(Optional<WebDriver> driver) throws TestingException {
	Activity.newIdx(this);
	setDriver(driver);
	WebDriver chromeDriver = driver.get();
	info.stream().map(e -> {
	    try {
		WebElement temp = null;
//				getElementHtml() != null && getElementHtml().getElement() != null
		if (false) {
//					temp = getElementHtml().getElement();
		} else {
		    temp = chromeDriver.findElement(By.tagName("body"));
		    String result = pageToTest.executeSelector(e.getSelector().getJquerySelector());
		    log.info("{}", result);
		}
		if (getIdx() != null) {
//					e.setParentId(this.getParentId());
		}
		e.setIdx(getIdx());
		if (StringUtils.isNotEmpty(e.getSelector().getClasses())) {
		    temp = temp.findElement(By.className(e.getSelector().getClasses()));
		}
		if (StringUtils.isNotEmpty(e.getSelector().getId())) {
		    temp = temp.findElement(By.id(e.getSelector().getId()));
		}
		if (StringUtils.isNotEmpty(e.getSelector().getCssSelector())) {
		    temp = temp.findElement(By.cssSelector(e.getSelector().getCssSelector()));
		}
		if (StringUtils.isNotEmpty(e.getSelector().getTag())) {
		    temp = temp.findElement(By.cssSelector(e.getSelector().getTag()));
		}

		if (StringUtils.isNotEmpty(e.getSelector().getAttribute())) {
		    e.setText(temp.getAttribute(e.getSelector().getAttribute()));
		} else {
		    e.setText(temp.getText());
		}
	    } catch (final Exception e1) {
		log.error("Retrieve Data", e1);
	    }

	    return e;
	}).collect(Collectors.toList());
    }

}
