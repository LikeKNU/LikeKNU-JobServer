package ac.knu.likeknujobserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class LikeKnuJobServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikeKnuJobServerApplication.class, args);
	}

}
