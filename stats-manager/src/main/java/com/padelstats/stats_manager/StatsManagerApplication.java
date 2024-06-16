package com.padelstats.stats_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class StatsManagerApplication {
	public static String FILENAME = "player_detail_urls.txt";

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StatsManagerApplication.class, args);

	}

}
