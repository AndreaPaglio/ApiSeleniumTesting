package net.seniorsoftwareengineer.testing.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.seniorsoftwareengineer.testing.entitydom.TestCase;
import net.seniorsoftwareengineer.testing.exception.TestingException;

/**
 * ExistText class extend Activity class for check if exist in element choosen
 * with css selector specific text
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = "type")
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
    public ExistText(@JsonProperty("type") String type, @JsonProperty("elementHtml") TestCase element,
	    @JsonProperty("info") List<TestCase> info) {
	super(element);
	this.type = type;
    }

    @Override
    public void execute(Optional<WebDriver> driver) throws TestingException {
	this.setDriver(driver);
	final WebElement webElement = testService.getElement(driver, getSelector().getCssSelector());
	if (webElement == null || (text != null && !text.equals(webElement.getText()))) {
	    close();
	    throw new TestingException(getSelector().getCssSelector(), ExistText.class.getName());
	}
    }

}
