package net.seniorsoftwareengineer.testing.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * Configuration swagger
 *
 */
@Configuration
public class DocumentationConfig {

    private ApiInfo apiInfo() {
	return new ApiInfo("Selenium Testing End-to-End", null, null, null, null, "Example json testing",
		"./../json-testing-file", Collections.emptyList());
    }

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
		.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).build();
    }

    @Bean
    UiConfiguration uiConfig() {
	return UiConfigurationBuilder.builder().deepLinking(true).displayOperationId(false).defaultModelsExpandDepth(-1)
		.defaultModelExpandDepth(-1).defaultModelRendering(ModelRendering.EXAMPLE).displayRequestDuration(false)
		.docExpansion(DocExpansion.NONE).filter(false).maxDisplayedTags(null)
		.operationsSorter(OperationsSorter.ALPHA).showExtensions(false).tagsSorter(TagsSorter.ALPHA)
		.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).validatorUrl(null).build();
    }

}
