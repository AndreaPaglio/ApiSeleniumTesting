package net.seniorsoftwareengineer.testing.entitydom;

import io.swagger.annotations.ApiModelProperty;

/**
 * Define if you want make one test at url or you can retrieving information (future implementation)
 *
 */
public enum TypeElement {
	@ApiModelProperty(notes = "Define if you want make one test at url or you can retrieving information (future implementation)", hidden=true, allowableValues = "URL_TO_TEST", example = "URL_TO_TEST")
	INFO_TO_SAVE, 
	URL_TO_TEST
}
