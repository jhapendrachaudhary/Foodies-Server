package in.Jhapuu.foodiesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FoodiesapiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.out.println("Java version: " + System.getProperty("java.version"));
		SpringApplication.run(FoodiesapiApplication.class, args);
	}

}
