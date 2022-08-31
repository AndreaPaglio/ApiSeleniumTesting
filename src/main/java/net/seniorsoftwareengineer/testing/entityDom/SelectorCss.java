package net.seniorsoftwareengineer.testing.entitydom;

import java.io.Serializable;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "Find a DOM element from css selector, id, tag, attribute", value = "Find a DOM element from css selector, id, tag, attribute")
public class SelectorCss implements Serializable {
	@ApiModelProperty(hidden=true)
	@JsonProperty("key")
	private String key;
	
    @ApiModelProperty(name = "CSS selector to be performed with JQuery (max CSS2)", value = "CSS selector to be performed with JQuery (max CSS2)", required = true, position = 1)
	@JsonProperty("cssSelector")
	private String cssSelector;

	@ApiModelProperty(hidden=true)
	@JsonProperty("classes")
	private String classes;

	@ApiModelProperty(hidden=true)
	@JsonProperty("id")
	private String id;
	
	@ApiModelProperty(hidden=true)
	@JsonProperty("tag")
	private String tag;
	
	@ApiModelProperty(hidden=true)
	@JsonProperty("attribute")
	private String attribute;

	@ApiModelProperty(hidden=true)
	@JsonProperty("jquerySelector")
	private String jquerySelector;

	@Override
	public String toString() {
		return "SelectorCss [key=" + key + ", cssSelector=" + cssSelector + ", classes=" + classes + ", id=" + id
				+ ", tag=" + tag + ", attribute=" + attribute + ", jquerySelector=" + jquerySelector + "]";
	}

}
