package archiduchess.microservice_chess_stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicroserviceChessStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceChessStatsApplication.class, args);
	}

}
