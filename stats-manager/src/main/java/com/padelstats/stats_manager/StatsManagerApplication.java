package com.padelstats.stats_manager;

import com.padelstats.stats_manager.entities.Jugadores;
import com.padelstats.stats_manager.utils.Scrapping;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class StatsManagerApplication {
	public static String FILENAME = "player_detail_urls.txt";

	public static void main(String[] args) {
		SpringApplication.run(StatsManagerApplication.class, args);

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		try {
			//Scrapping.scrapRankingPP(driver);

			List<String> urls = Scrapping.readUrlsFromFile(FILENAME);
			List<String> urlsPrueba = new ArrayList<>();
			urlsPrueba.add("https://www.padelfip.com/player/agustin-tapia/");

			List<Jugadores> infoJugadores = Scrapping.getPlayerInfoFromUrls(urls, driver);

		} finally {
			driver.quit();
		}

	}

}
