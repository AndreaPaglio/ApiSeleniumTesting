package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.seniorsoftwareengineer.testing.activity.interfaces.Test;
import net.seniorsoftwareengineer.testing.builder.ChromeTesting;
import net.seniorsoftwareengineer.testing.entitydom.SelectorCss;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.service.TestService;
import net.seniorsoftwareengineer.testing.service.TestServiceImpl;

/**
 * All actions class extend Activity class
 *
 */
@Valid
@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Click.class, name = "Click"),
	@JsonSubTypes.Type(value = InsertText.class, name = "InsertText"),
	@JsonSubTypes.Type(value = Exist.class, name = "Exist"),
	@JsonSubTypes.Type(value = ExistText.class, name = "ExistText"),
	@JsonSubTypes.Type(value = InsertText.class, name = "InsertText"),
	@JsonSubTypes.Type(value = GetElement.class, name = "GetElement"),
	@JsonSubTypes.Type(value = GetElements.class, name = "ListElements"),
	@JsonSubTypes.Type(value = RetrieveData.class, name = "RetrieveWebElementInformation") })
@ApiModel(description = "Super class for all actions", discriminator = "type", subTypes = { InsertText.class,
	Exist.class, ExistText.class, Click.class })
public class Activity implements ActivityAction, Serializable {
    @JsonIgnore
    @ApiModelProperty(position = 2, notes = "Values: Click/InsertText, Exist, ExistText", allowableValues = "InsertText, Exist, ExistText,Click")
    private String type;

    @NotNull(message = "Selector css must be not empty")
    @ApiModelProperty(position = 1, example = "DOM to be select by css selector \n\n" + "		{ \n"
	    + "      \"type\": \"InsertText\",\n" + "      \"textToInsert\":\"1\",\n" + "      \"selector\":{\n"
	    + "            \"cssSelector\": \".form-field.field-example #number\"\n" + "      }\n" + "    }"
	    + "    example2: {\n" + "      \"type\": \"Click\",\n" + "      \"selector\":{\n"
	    + "            \"cssSelector\": \".eolo-orange-button.check-coverage-box--check-coverage-btn.js-aidTarget\"\n"
	    + "      }\n" + "    }" + "    example3: {\n" + "      \"type\": \"Exist\",\n" + "      \"selector\":{\n"
	    + "            \"cssSelector\": \".new-funnel-2021--address-content-info img[src*='bollino-fibra']\"\n"
	    + "      }\n" + "    }\n" + "    example4: {\n" + "      \"type\": \"ExistText\",\n"
	    + "      \"text\": \"La tua zona è coperta!\",\n" + "      \"selector\":{\n"
	    + "            \"cssSelector\": \".new-funnel-2021--configuration.new-funnel-2021--questions h1\"\n"
	    + "      }\n" + "    }")
    @JsonProperty("selector")
    protected SelectorCss selector = null;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    protected List<WebElement> webElements;

    @ApiModelProperty(hidden = true)
    @JsonProperty("idx")
    protected String idx;

    @ApiModelProperty(hidden = true)
    @JsonProperty("parentId")
    protected List<String> parentId = new ArrayList<String>();

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    protected Test pageToTest;

    @ApiModelProperty(hidden = true)
    @JsonProperty("isNewPage")
    private Boolean isNewPage = Boolean.TRUE;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    protected TestService testService;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Optional<WebDriver> driver;

    @Override
    public void execute(Optional<WebDriver> driver) throws TestingException {
    }

    public Activity() {
	this.parentId = new ArrayList<String>();
	this.pageToTest = new ChromeTesting();
	this.selector = new SelectorCss();
	this.testService = new TestServiceImpl();
	driver = Optional.empty();
    }

    public static void newIdx(Activity activity) {
	if (StringUtils.isEmpty(activity.getIdx())) {
	    activity.setIdx(UUID.randomUUID().toString());
	}
    }

    public Activity(List<TestCase> info) {
	super();
	driver = Optional.empty();
    }

    public Activity(List<TestCase> info, TestCase element) {
	super();
	this.selector = new SelectorCss();

	this.testService = new TestServiceImpl();
	driver = Optional.empty();
    }

    public void takeSnapshot(String path) throws TestingException {
	this.pageToTest.takeSnapShot(path);
    }

    public void close() {
	if (driver != null && driver.isPresent()) {
	    driver.get().quit();
	}
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Activity other = (Activity) obj;
	if (idx == null) {
	    if (other.idx != null)
		return false;
	} else if (!idx.equals(other.idx))
	    return false;
	if (selector == null) {
	    if (other.selector != null)
		return false;
	} else if (!selector.equals(other.selector))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((idx == null) ? 0 : idx.hashCode());
	result = prime * result + ((selector == null) ? 0 : selector.hashCode());
	return result;
    }

}
