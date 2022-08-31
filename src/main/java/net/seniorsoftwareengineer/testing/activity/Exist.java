package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.seniorsoftwareengineer.testing.entitydom.Element;
import net.seniorsoftwareengineer.testing.service.offuscate.OffuscateService;

/**
 * Exist class extend Activity class for check if exist in dom one element selected by css selector
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "Azione da usare se si vuole controllare l'esistenza in un certo elemento del DOM")
public class Exist extends Activity implements ActivityAction, Serializable {
	@ApiModelProperty(required=true, value = "Exist", example = "Exist")
	@JsonProperty("type")
	private String type;

	@JsonCreator
	public Exist() {
		this.parentId = new ArrayList<String>();
	}

	@JsonCreator(mode = Mode.DEFAULT)
	public Exist(@JsonProperty("type") String type, @JsonProperty("elementHtml") Element element,
			@JsonProperty("info") List<Element> info) {
		super(element);
		this.type = type;
	}

	@Override
	public void execute(WebDriver driver) throws TestingException {
		final WebElement webElement = OffuscateService.getElement(driver,
				getElementHtml().getSelector().getCssSelector());
		if(webElement == null) {
			driver.close();
			throw new TestingException(getElementHtml().getSelector().getCssSelector(), Exist.class.getName());
		}
	}

}
