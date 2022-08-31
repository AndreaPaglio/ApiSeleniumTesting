package net.seniorsoftwareengineer;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Class contain start entry point
 *
 */
@SpringBootApplication(scanBasePackages = { "net.seniorsoftwareengineer", "net.seniorsoftwareengineer.testing.config", "net.seniorsoftwareengineer.testing.service.entity",
		"net.seniorsoftwareengineer.testing.builder", "net.seniorsoftwareengineer.testing.controller" })
@EntityScan({ "net.seniorsoftwareengineer", "net.seniorsoftwareengineer.testing.config", "net.seniorsoftwareengineer.testing.service.entity",
		"net.seniorsoftwareengineer.testing.builder", "net.seniorsoftwareengineer.testing.controller" })
@EnableSwagger2WebMvc
public class TestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setMaxPayloadLength(64000);
		return loggingFilter;
	}
}
