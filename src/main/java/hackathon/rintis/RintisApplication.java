package hackathon.rintis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RintisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RintisApplication.class, args);
	}

}
