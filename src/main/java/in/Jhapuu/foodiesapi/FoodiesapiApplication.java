package in.Jhapuu.foodiesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodiesapiApplication {

	public static void main(String[] args) {
		System.out.println("Java version: " + System.getProperty("java.version"));
		SpringApplication.run(FoodiesapiApplication.class, args);
	}

}
