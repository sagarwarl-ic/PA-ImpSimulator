package fb.pricingAnalytics.PricingAnalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = "fb.pricingAnalytics")
@EntityScan(basePackages = "fb.pricingAnalytics")
public class ImpactSimulatorApplication 
// ****Uncomment this code when depolying in stand alone tomcat server**** extends SpringBootServletInitializer

{

	public static void main(String[] args) {
		SpringApplication.run(ImpactSimulatorApplication.class, args);
	}
	
	/*
	 * This code has to be uncommented when deploying in the stand alone tomcat server
	 * 
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder builder) { return
	 * builder.sources(ImpactSimulatorApplication.class); }
	 */

}
