package io.github.guilherme_eira.money_tracker_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MoneyTrackerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyTrackerAppApplication.class, args);
	}

}
