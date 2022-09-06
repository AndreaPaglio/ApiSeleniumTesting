package net.seniorsoftwareengineer.testing.configuration;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Configuration for one Test Define if you want inject JQuery script for
 * performing css selecting, headless mode, define browser version installed on
 * environment
 *
 */
@Data
@Valid
public class Configuration implements Serializable {
    @ApiModelProperty(notes = "If true JQuery not present you must inject JQuery script", example = "false", position = 1)
    @JsonProperty("useJQuery")
    private Boolean useJQuery;

    @ApiModelProperty(notes = "Enable screenshot", example = "false", position = 2)
    @JsonProperty("makeSnapshot")
    private Boolean makeSnapshot;

    @ApiModelProperty(notes = "Enable remote Debugging Port", example = "false", position = 3)
    @JsonProperty("remoteDebuggingPort")
    private Boolean remoteDebuggingPort;

    @ApiModelProperty(notes = "Enable headless", example = "false", position = 4)
    @JsonProperty("headless")
    private Boolean headless;

    @Valid
    @NotNull(message = "Browser Version must be not empty")
    @ApiModelProperty(notes = "Browser Version", example = "104.0.5112.79", position = 5, required = true)
    @JsonProperty("browserVersion")
    private String browserVersion;

    @ApiModelProperty(notes = "Enable Third Party Cookies", example = "false", position = 6, required = false)
    @JsonProperty("enableThirdPartyCookies")
    private Boolean enableThirdPartyCookies;

    @ApiModelProperty(notes = "User Agent", example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36.", position = 7, required = false)
    @JsonProperty("userAgent")
    private String userAgent;

    @ApiModelProperty(notes = "Driver version", example = "104.0.5112.79", position = 8, required = false)
    @JsonProperty("driverVersion")
    private String driverVersion;

    @ApiModelProperty(notes = "Single Process", example = "false", position = 9, required = false)
    @JsonProperty("singleProcess")
    private Boolean singleProcess = Boolean.FALSE;

    @ApiModelProperty(notes = "extraConfiguration", example = "--disable-dev-shm-usage", position = 10, required = false)
    @JsonProperty("extraConfiguration")
    private String extraConfiguration;

    public Configuration() {
	super();
	headless = Boolean.FALSE;
	remoteDebuggingPort = Boolean.FALSE;
	useJQuery = Boolean.FALSE;
	makeSnapshot = Boolean.FALSE;
	singleProcess = Boolean.FALSE;
	enableThirdPartyCookies = Boolean.FALSE;

    }

    @Override
    public String toString() {
	return "Configuration [useJQuery=" + useJQuery + ", makeSnapshot=" + makeSnapshot + ", remoteDebuggingPort="
		+ remoteDebuggingPort + ", headless=" + headless + ", browserVersion=" + browserVersion
		+ ", enableThirdPartyCookies=" + enableThirdPartyCookies + ", userAgent=" + userAgent + "]";
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Configuration other = (Configuration) obj;
	if (browserVersion == null) {
	    if (other.browserVersion != null)
		return false;
	} else if (!browserVersion.equals(other.browserVersion))
	    return false;
	if (driverVersion == null) {
	    if (other.driverVersion != null)
		return false;
	} else if (!driverVersion.equals(other.driverVersion))
	    return false;
	if (enableThirdPartyCookies == null) {
	    if (other.enableThirdPartyCookies != null)
		return false;
	} else if (!enableThirdPartyCookies.equals(other.enableThirdPartyCookies))
	    return false;
	if (extraConfiguration == null) {
	    if (other.extraConfiguration != null)
		return false;
	} else if (!extraConfiguration.equals(other.extraConfiguration))
	    return false;
	if (headless == null) {
	    if (other.headless != null)
		return false;
	} else if (!headless.equals(other.headless))
	    return false;
	if (makeSnapshot == null) {
	    if (other.makeSnapshot != null)
		return false;
	} else if (!makeSnapshot.equals(other.makeSnapshot))
	    return false;
	if (remoteDebuggingPort == null) {
	    if (other.remoteDebuggingPort != null)
		return false;
	} else if (!remoteDebuggingPort.equals(other.remoteDebuggingPort))
	    return false;
	if (singleProcess == null) {
	    if (other.singleProcess != null)
		return false;
	} else if (!singleProcess.equals(other.singleProcess))
	    return false;
	if (useJQuery == null) {
	    if (other.useJQuery != null)
		return false;
	} else if (!useJQuery.equals(other.useJQuery))
	    return false;
	if (userAgent == null) {
	    if (other.userAgent != null)
		return false;
	} else if (!userAgent.equals(other.userAgent))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((browserVersion == null) ? 0 : browserVersion.hashCode());
	result = prime * result + ((driverVersion == null) ? 0 : driverVersion.hashCode());
	result = prime * result + ((enableThirdPartyCookies == null) ? 0 : enableThirdPartyCookies.hashCode());
	result = prime * result + ((extraConfiguration == null) ? 0 : extraConfiguration.hashCode());
	result = prime * result + ((headless == null) ? 0 : headless.hashCode());
	result = prime * result + ((makeSnapshot == null) ? 0 : makeSnapshot.hashCode());
	result = prime * result + ((remoteDebuggingPort == null) ? 0 : remoteDebuggingPort.hashCode());
	result = prime * result + ((singleProcess == null) ? 0 : singleProcess.hashCode());
	result = prime * result + ((useJQuery == null) ? 0 : useJQuery.hashCode());
	result = prime * result + ((userAgent == null) ? 0 : userAgent.hashCode());
	return result;
    }
}
