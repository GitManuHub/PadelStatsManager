package com.padelstats.stats_manager;

import com.padelstats.stats_manager.utils.Scrapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatsManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatsManagerApplication.class, args);

		Scrapping.scrapRankingPP();

		System.out.println("FIN");
	}

}
