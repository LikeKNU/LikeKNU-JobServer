package ac.knu.likeknujobserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LikeKnuJobServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikeKnuJobServerApplication.class, args);
	}

}
