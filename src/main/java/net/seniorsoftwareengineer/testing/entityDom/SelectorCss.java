package net.seniorsoftwareengineer.testing.entitydom;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Find a DOM element from css selector, id, tag, attribute
 *
 */
@Valid
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "Find a DOM element from css selector, id, tag, attribute", value = "")
public class SelectorCss implements Serializable {
    @Deprecated
    @ApiModelProperty(hidden = true)
    @JsonProperty("key")
    private String key;

    @NotNull(message = "Css Selector must be not empty")
    @ApiModelProperty(name = "CSS selector to be performed with JQuery (max CSS2)", value = "CSS selector to be performed with JQuery (max CSS2)", required = true, position = 1)
    @JsonProperty("cssSelector")
    private String cssSelector;

    @Deprecated
    @ApiModelProperty(hidden = true)
    @JsonProperty("classes")
    private String classes;

    @Deprecated
    @ApiModelProperty(hidden = true)
    @JsonProperty("id")
    private String id;

    @Deprecated
    @ApiModelProperty(hidden = true)
    @JsonProperty("tag")
    private String tag;

    @Deprecated
    @ApiModelProperty(hidden = true)
    @JsonProperty("attribute")
    private String attribute;

    @Deprecated
    @ApiModelProperty(hidden = true)
    @JsonProperty("jquerySelector")
    private String jquerySelector;

    @Override
    public String toString() {
	return "SelectorCss [key=" + key + ", cssSelector=" + cssSelector + ", classes=" + classes + ", id=" + id
		+ ", tag=" + tag + ", attribute=" + attribute + ", jquerySelector=" + jquerySelector + "]";
    }

}
