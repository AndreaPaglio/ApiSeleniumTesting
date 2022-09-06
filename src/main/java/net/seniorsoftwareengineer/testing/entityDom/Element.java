package net.seniorsoftwareengineer.testing.entitydom;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Valid
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Represent Element DOM: identify with selector can have text, html")
@Slf4j
public class Element implements Serializable {

    @ApiModelProperty(hidden = true)
    @JsonProperty("idx")
    private String idx;

    @JsonProperty("text")
    private String text;

    @JsonProperty("html")
    private String html;

    @JsonProperty("selector")
    protected SelectorCss selector = null;

    public Element() {
	selector = new SelectorCss();
    }
}
