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
 * ExistText class extend Activity class for check if exist in  element choosen with css selector
 * specific text
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(parent = Activity.class, description = "Azione da usare se si vuole controllare l'esistenza di un testo in un certo elemento del DOM")
public class ExistText extends Activity implements ActivityAction, Serializable {
	@ApiModelProperty(required = true, notes = "ExistText se hai bisogno che venga controllato se esiste nell'elemento DOM il testo indicato", allowableValues = "ExistText", value = "ExistText")
	@JsonProperty("type")
	private String type;
	
	@ApiModelProperty(required = true, notes = "Testo che deve essere controllato se esiste nell'elemento DOM")
	@JsonProperty("text")
	protected String text;
	@JsonCreator
	public ExistText() {
		this.parentId = new ArrayList<String>();
	}

	@JsonCreator(mode = Mode.DEFAULT)
	public ExistText(@JsonProperty("type") String type, @JsonProperty("elementHtml") Element element,
			@JsonProperty("info") List<Element> info) {
		super(element);
		this.type = type;
	}

	@Override
	public void execute(WebDriver driver) throws TestingException {
		final WebElement webElement = OffuscateService.getElement(driver,
				getElementHtml().getSelector().getCssSelector());
		if(webElement == null || (text != null && !text.equals(webElement.getText()))) {
			driver.close();
			throw new TestingException(getElementHtml().getSelector().getCssSelector(), ExistText.class.getName());
		}
	}

}
