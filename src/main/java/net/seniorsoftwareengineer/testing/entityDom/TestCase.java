package net.seniorsoftwareengineer.testing.entitydom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.seniorsoftwareengineer.testing.activity.Activity;
import net.seniorsoftwareengineer.testing.activity.interfaces.TestCaseAction;
import net.seniorsoftwareengineer.testing.configuration.Configuration;
import net.seniorsoftwareengineer.testing.configuration.Pagination;

/**
 * Choose URL and actions to be performed (Click, insert text, find text) url:
 * URL to performing test selector: CSS2 Selector for Jquery use
 */
@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true, value = { "driver", "parentId", "element", "typeElement", "value", "info",
	"pagination", "elementDOM" })
@ApiModel(description = "Choose URL and actions to be performed (Click, insert text, find text)", value = "request body JSON")
@Slf4j
public class TestCase implements TestCaseAction, Serializable, Cloneable {
    /**
     * 
     */
    private static final long serialVersionUID = 2986957837909763172L;
    @ApiModelProperty(hidden = true)
    @JsonProperty("idx")
    private String idx;

    @ApiModelProperty(hidden = true)
    private List<String> parentId;

    @Valid
    @ApiModelProperty(notes = "URL to performing test", value = "url", required = true, position = 2, example = "https://www.google.it")
    @NotNull(message = "Url cannot be empty")
    @JsonProperty("url")
    private String url;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private WebElement element;

    @JsonIgnore
    @ApiModelProperty(position = 1, required = false, example = "URL_TO_TEST", allowableValues = "URL_TO_TEST")
    @JsonProperty("typeElement")
    private TypeElement typeElement;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @JsonProperty("elementDOM")
    Element elementDOM;

    @Valid
    @ApiModelProperty(notes = "Ordered actions to be performed as is", position = 4, required = true, example = " [ {"
	    + "      \"type\": \"Click\",\n" + "          \"selector\":{\n"
	    + "            \"cssSelector\": \".eolo-offerta-rectangle a.eolo-orange-button\"\n" + "          }\n"
	    + "    },{" + "      \"type\": \"ExistText\",\n" + "          \"selector\":{\n"
	    + "            \"cssSelector\": \".eolo-offerta-rectangle a.eolo-orange-button\"\n" + "          }\n"
	    + "		},{\n" + "      \"type\": \"Exist\",\n" + "          \"selector\":{\n"
	    + "            \"cssSelector\": \".new-funnel-2021--address-content-info img[src*='bollino-fibra']\"\n"
	    + "          }\n" + "    },\n" + "    {\n" + "      \"type\": \"ExistText\",\n"
	    + "      \"text\": \"La tua zona è coperta!\",\n" + "          \"selector\":{\n"
	    + "            \"cssSelector\": \".new-funnel-2021--configuration.new-funnel-2021--questions h1\"\n"
	    + "          }\n" + "    } ]")
    @JsonProperty("activities")
    List<Activity> activities;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @JsonProperty("pagination")
    Pagination pagination;

    @Valid
    @ApiModelProperty(notes = "Special configuration to be tested", position = 3, required = true)
    @NotNull(message = "Configuration must set")
    @JsonProperty("configuration")
    Configuration configuration;

    public TestCase() {
	parentId = new ArrayList<String>();
	configuration = new Configuration();
	activities = new ArrayList<Activity>();
	elementDOM = new Element();
	idx = UUID.randomUUID().toString();
    }

    public static void newIdx(TestCase element) {
	if (StringUtils.isEmpty(element.getIdx())) {
	    element.setIdx(UUID.randomUUID().toString());
	}
    }

    @Override
    public Object clone() {
	TestCase newElement = null;
	try {
	    super.clone();
	    newElement = new TestCase();
	    TestCase.newIdx(newElement);
	    newElement.setTypeElement(this.getTypeElement());
	    newElement.setUrl(this.getUrl());
	    newElement.setElement(this.getElement());
	} catch (CloneNotSupportedException e) {
	    log.error("Error cloning", e);
	}

	return newElement;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TestCase other = (TestCase) obj;
	if (activities == null) {
	    if (other.activities != null)
		return false;
	} else if (!activities.equals(other.activities))
	    return false;
	if (configuration == null) {
	    if (other.configuration != null)
		return false;
	} else if (!configuration.equals(other.configuration))
	    return false;
	if (elementDOM == null) {
	    if (other.elementDOM != null)
		return false;
	} else if (!elementDOM.equals(other.elementDOM))
	    return false;
	if (idx == null) {
	    if (other.idx != null)
		return false;
	} else if (!idx.equals(other.idx))
	    return false;
	if (pagination == null) {
	    if (other.pagination != null)
		return false;
	} else if (!pagination.equals(other.pagination))
	    return false;
	if (parentId == null) {
	    if (other.parentId != null)
		return false;
	} else if (!parentId.equals(other.parentId))
	    return false;
	if (typeElement != other.typeElement)
	    return false;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((activities == null) ? 0 : activities.hashCode());
	result = prime * result + ((configuration == null) ? 0 : configuration.hashCode());
	result = prime * result + ((elementDOM == null) ? 0 : elementDOM.hashCode());
	result = prime * result + ((idx == null) ? 0 : idx.hashCode());
	result = prime * result + ((pagination == null) ? 0 : pagination.hashCode());
	result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
	result = prime * result + ((typeElement == null) ? 0 : typeElement.hashCode());
	result = prime * result + ((url == null) ? 0 : url.hashCode());
	return result;
    }

}
