
# Selenium API
It's Selenium API, you can test simple call one API, for all end-2-end test case only deploying on your server API. 
**IMPORTANT**. If you want to deploy on cloud you must configure properly selenium as well browser driver.
I realize a proof of concept for Chrome on AWS Ec2. It's work like a charm!

# Use library
You can call API many way: preparing json request and sending, use Builder in library.
If you use maven you should include

    <dependency>
	    <groupId>net.seniorsoftwareengineer.testing</groupId>
	    <artifactId>selenium-api-testing</artifactId>
        <version>1.3.4-alpha</version>
    </dependency>

After that can call API with

    String serverTest = "http://localhost:8081/test";
	String url = "https://www.google.com";
	String driverVersion = "104.0.5112.20";
	String browserVersion = "104.0.5112.79";
	String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36.";
	Configuration configuration = BuilderConfiguration.create().browserVersion(browserVersion).driverVersion(driverVersion).userAgent(userAgent).headless().getConfiguration();
	Click click =  new Click();
	click.getSelector().setCssSelector("body");
	TestCase test = test = BuilderTestCase.create().url(url).configuration(configuration).addActivity(click).getTestCase();
	RestTemplate restTemplate = new RestTemplate();
	HttpEntity<TestCase> request = new HttpEntity<TestCase>(test);
	ResultTesting resultTesting = restTemplate.postForObject(serverTest, request, ResultTesting.class);

## Troubleshooting
If you have runtime error regarding log4j can resolve adding in pom in import of library

    <exclusions>
    	<exclusion>
    	    <groupId>org.apache.logging.log4j</groupId>
    	    <artifactId>log4j-to-slf4j</artifactId>
    	</exclusion>
    </exclusions>

# Documentation API

Documentation REST API is automagically with Swagger library.
If you go at ./swagger-ui/index.html you can dinamically see request and response API structure.