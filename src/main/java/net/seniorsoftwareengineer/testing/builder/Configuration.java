package net.seniorsoftwareengineer.testing.builder;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Configuration for one Test
 * Define if you want inject JQuery script for performing css selecting, headless mode, define browser version installed on environment
 *
 */
@Data
@Valid
public class Configuration implements Serializable {
	@ApiModelProperty(notes = "If true JQuery not present you must inject JQuery script", example = "false", position = 1)
	@JsonProperty("useJQuery")
	private Boolean useJQuery = Boolean.FALSE;
	
	@ApiModelProperty(notes = "Enable screenshot", example = "false", position = 2)
	@JsonProperty("makeSnapshot")
	private Boolean makeSnapshot = Boolean.FALSE;

	@ApiModelProperty(notes = "Enable remote Debugging Port", example = "false", position = 3)
	@JsonProperty("remoteDebuggingPort")
	private Boolean remoteDebuggingPort = Boolean.FALSE;
	
	@ApiModelProperty(notes = "Enable headless", example = "false", position = 4)
	@JsonProperty("headless")
	private Boolean headless = Boolean.FALSE;
	
	@Valid	
	@NotEmpty(message = "Browser Version must be not empty")
	@ApiModelProperty(notes = "Browser Version", example = "104.0.5112.79", position = 5, required = true)
	@JsonProperty("browserVersion")
	private String browserVersion;
	
	@ApiModelProperty(notes = "Enable Third Party Cookies", example = "false", position = 6, required = false)
	@JsonProperty("enableThirdPartyCookies")
	private Boolean enableThirdPartyCookies = Boolean.FALSE;
	
	@ApiModelProperty(notes = "User Agent", example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36.", position = 7, required = false)
	@JsonProperty("userAgent")
	private String userAgent;
	
	@ApiModelProperty(notes = "Driver version", example = "", position = 8, required = false)
	@JsonProperty("driverVersion")
	private String driverVersion;
	
	@ApiModelProperty(notes = "Single Process", example = "false", position = 9, required = false)
	@JsonProperty("singleProcess")
	private Boolean singleProcess = Boolean.FALSE;
	
	@ApiModelProperty(notes = "extraConfiguration", example = "", position = 10, required = false)
	@JsonProperty("extraConfiguration")
	private String extraConfiguration;
	
	public Configuration(Boolean useJQuery, Boolean makeSnapshot) {
		super();
		this.useJQuery = useJQuery;
	}

	public Configuration() {
		super();
		useJQuery = Boolean.FALSE;
		makeSnapshot = Boolean.FALSE;
	}

	@Override
	public String toString() {
		return "Configuration [useJQuery=" + useJQuery + ", makeSnapshot=" + makeSnapshot + ", remoteDebuggingPort="
				+ remoteDebuggingPort + ", headless=" + headless + ", browserVersion=" + browserVersion
				+ ", enableThirdPartyCookies=" + enableThirdPartyCookies + ", userAgent=" + userAgent + "]";
	}
}
