package archiduchess.microservice_chess_stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
@EnableFeignClients("archiduchess.microservice_chess_stats")
@EnableSwagger2
public class MicroserviceChessStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceChessStatsApplication.class, args);
	}

}
