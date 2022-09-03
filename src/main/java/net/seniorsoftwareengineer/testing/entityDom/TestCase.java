package net.seniorsoftwareengineer.testing.entitydom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.activity.Activity;
import net.seniorsoftwareengineer.testing.activity.ActivityAction;
import net.seniorsoftwareengineer.testing.activity.Click;
import net.seniorsoftwareengineer.testing.activity.Exist;
import net.seniorsoftwareengineer.testing.activity.ExistText;
import net.seniorsoftwareengineer.testing.activity.GetElement;
import net.seniorsoftwareengineer.testing.activity.GetElements;
import net.seniorsoftwareengineer.testing.activity.InsertText;
import net.seniorsoftwareengineer.testing.activity.RetrieveData;
import net.seniorsoftwareengineer.testing.builder.Configuration;
import net.seniorsoftwareengineer.testing.builder.Pagination;
import net.seniorsoftwareengineer.testing.builder.Test;
import net.seniorsoftwareengineer.testing.exception.TestingException;
import net.seniorsoftwareengineer.testing.utility.constants.ConstantsTesting;

/**
 * Choose URL and actions to be performed (Click, insert text, find text)
 *	url: URL to performing test
 *	selector: CSS2 Selector for Jquery use
 */
@Data
@Valid
@Component
@JsonIgnoreProperties(ignoreUnknown = true, value = {"driver", "parentId", "element", "typeElement", "value", "info", "pagination"})
@ApiModel(description = "Choose URL and actions to be performed (Click, insert text, find text)", value = "request body JSON")
@Slf4j
public class TestCase implements ActivityAction, Serializable, Cloneable, ElementToSave {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2986957837909763172L;
	@ApiModelProperty(hidden=true)
	@JsonProperty("idx")
	private String idx;

	@ApiModelProperty(hidden=true)
	private List<String> parentId;
	
	
    @ApiModelProperty(hidden = true)
	@Parameter(description = "CSS2 Selector for Jquery use")
	@NotNull(message = "CSS2 Selector for Jquery use")
	@JsonProperty("selector")
	private SelectorCss selector = new SelectorCss();

    @ApiModelProperty(notes =  "URL to performing test", value = "url", required = true, position = 2, example = "https://www.google.it")
	@NotNull(message = "URL to performing test")
    @NotEmpty(message = "Url cannot be empty")
	@JsonProperty("url")
	private String url;
    
    @JsonIgnore
    @ApiModelProperty(hidden=true)
	private WebElement element;

    @JsonIgnore
    @ApiModelProperty(position = 1, required = false,  example = "URL_TO_TEST", allowableValues = "URL_TO_TEST")
	@JsonProperty("typeElement")
	private TypeElement typeElement;

    @JsonIgnore
	@ApiModelProperty(hidden=true)
	@JsonProperty("value")
	private String value;

	@ApiModelProperty(hidden=true)
	@JsonProperty("isNewPage")
	private Boolean isNewPage = Boolean.TRUE;
	@ApiModelProperty(notes = "Ordered actions to be performed as is",  position = 4, required = true, example = " [{"
			+ "      \"type\": \"Click\",\n"
			+ "      \"elementHtml\": {\n"
			+ "          \"selector\":{\n"
			+ "            \"cssSelector\": \".eolo-offerta-rectangle a.eolo-orange-button\"\n"
			+ "          }\n"
			+ "        \n"
			+ "      }\n"
			+ "    },{"
			+ "      \"type\": \"ExistText\",\n"
			+ "      \"elementHtml\": {\n"
			+ "          \"selector\":{\n"
			+ "            \"cssSelector\": \".eolo-offerta-rectangle a.eolo-orange-button\"\n"
			+ "          }\n"
			+ "        \n"
			+ "      }\n"
			+ "},{\n"
			+ "      \"type\": \"Exist\",\n"
			+ "      \"elementHtml\": {\n"
			+ "          \"selector\":{\n"
			+ "            \"cssSelector\": \".new-funnel-2021--address-content-info img[src*='bollino-fibra']\"\n"
			+ "          }\n"
			+ "      }\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"type\": \"ExistText\",\n"
			+ "      \"text\": \"La tua zona è coperta!\",\n"
			+ "      \"elementHtml\": {\n"
			+ "          \"selector\":{\n"
			+ "            \"cssSelector\": \".new-funnel-2021--configuration.new-funnel-2021--questions h1\"\n"
			+ "          }\n"
			+ "      }\n"
			+ "    }]")
	@NotNull(message = "Ordered actions to be performed as is")
	@JsonProperty("activities")
	List<Activity> activities;

	@ApiModelProperty(hidden=true)
	@JsonProperty("info")
	List<TestCase> info;

	@JsonIgnore
	@ApiModelProperty(hidden=true)
	Test pageToTest;
	
	@JsonIgnore
	@ApiModelProperty(hidden=true)
	@JsonProperty("pagination")
	Pagination pagination;

	@Valid
	@ApiModelProperty(notes = "Special configuration to be tested", position = 3, required = true)
	@NotNull(message = "Special configuration to be tested")
	@JsonProperty("configuration")
	Configuration configuration;

	Optional<WebDriver> driver;
	
	public TestCase() {
		info = new ArrayList<TestCase>();
		parentId = new ArrayList<String>();
		configuration = new Configuration();
		activities = new ArrayList<Activity>();
		driver = Optional.empty();
	}

	public TestCase(String cssSelector) {
		super();
		this.selector = new SelectorCss(null, cssSelector, null, null, null, null, null);
		info = new ArrayList<TestCase>();
		driver = Optional.empty();
	}

	public static void newIdx(TestCase element) {
		if (StringUtils.isEmpty(element.getIdx())) {
			element.setIdx(UUID.randomUUID().toString());
		}
	}

	public TestCase(String cssSelector, String classes, String id, String tag, String attribute, TypeElement typeElement,
			String key) {
		super();
		this.selector = new SelectorCss(key, cssSelector, classes, id, tag, attribute, null);
		this.typeElement = typeElement;
		info = new ArrayList<TestCase>();
		driver = Optional.empty();
	}

	public TestCase(String url, TypeElement typeElement, List<Activity> activities, List<TestCase> info) {
		super();
		this.url = url;
		this.typeElement = typeElement;
		this.info = info;
		this.selector = new SelectorCss();
		driver = Optional.empty();
	}

	public TestCase(WebElement element) {
		super();
		this.selector = new SelectorCss();
		this.element = element;
		info = new ArrayList<TestCase>();
		driver = Optional.empty();
	}

	@Override
	public void execute(Optional<WebDriver> driver) throws TestingException {
		setDriver(driver);
		for (Activity activity : getActivities()) {
			TestCase.newIdx(this);
			activity.getParentId().add(getIdx());
			if (!isNewPage && getIdx() != null) {
				activity.setIdx(getIdx());
			} else {
				Activity.newIdx(activity);
			}
			activity.setPageToTest(getPageToTest());
			activity.execute(driver);
			activity.takeSnapshot(ConstantsTesting.DIR_SNAPSHOT + "/" + getIdx() + "/" + activity.getIdx() + ".png");
		};
	}
	@ApiModelProperty(hidden=true)
	@Override
	public void setElementHtml(TestCase e) {
	}

	@Override
	public List<TestCase> getInfo() {
		return info;
	}

	public List<TestCase> getElementHtmlToTest() {
		final Predicate<? super TestCase> isToTest = e -> {
			return TypeElement.URL_TO_TEST.equals(e.getTypeElement());
		};
		return getElementHtmlToTest(isToTest, TypeElement.URL_TO_TEST);
	}

	@Override
	public List<TestCase> getElementHtmlToSave() {
		final Predicate<? super TestCase> isToTest = e -> {
			return TypeElement.INFO_TO_SAVE.equals(e.getTypeElement());
		};
		return getElementHtmlToSave(isToTest, TypeElement.INFO_TO_SAVE);
	}

	private List<TestCase> getElementHtmlToTest(Predicate<? super TestCase> isToTest, TypeElement type) {
		final List<TestCase> elem = new ArrayList<TestCase>();

		info.stream().filter(isToTest).forEach(e -> {
			elem.addAll(e.getElementHtmlToTest());
		});
		if (type.equals(this.getTypeElement())) {
			elem.add(this);
		}
		return elem;
	}

	private List<TestCase> getElementHtmlToSave(Predicate<? super TestCase> isToTest, TypeElement type) {
		final List<TestCase> elem = new ArrayList<TestCase>();

		info.stream().filter(isToTest).forEach(e -> {
			elem.addAll(e.getElementHtmlToSave());
		});
		if (type.equals(this.getTypeElement())) {
			elem.add(this);
		}
		return elem;
	}

	@Override
	public Object clone() {
		TestCase newElement = null;
		try {
			super.clone();
			newElement = new TestCase();
			TestCase.newIdx(newElement);
			newElement.setSelector(this.getSelector());
			newElement.setTypeElement(this.getTypeElement());
			newElement.setInfo(new ArrayList<TestCase>());
			newElement.setInfo(this.getInfo().stream().map(info -> {
				return (TestCase) info.clone();
			}).collect(Collectors.toList()));
			newElement.setUrl(this.getUrl());
			newElement.setElement(this.getElement());
		} catch (CloneNotSupportedException e) {
			log.error("Error cloning", e);
		}

		return newElement;
	}

	@Override
	public void takeSnapshot(String path) throws TestingException{	
		if (configuration.getMakeSnapshot()) {
				pageToTest.takeSnapShot(path);
		}
	}

	@Override
	public void close() {
		if(driver != null && driver.isPresent()) {
			try {
				this.driver.get().quit();
			} catch (Exception e) {
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}
	
	
}
