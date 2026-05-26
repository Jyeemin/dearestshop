package dearest.dearestshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DearestshopApplication {

	public static void main(String[] args) {

		SpringApplication.run(DearestshopApplication.class, args);
	}

}
