package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.controllers.JugadorController;
import com.padelstats.stats_manager.entities.Jugadores;
import com.padelstats.stats_manager.utils.Scrapping;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class ScheduledScrappingService {

    @Autowired
    private JugadorController jugadorController;

    /*@Scheduled(cron = "0 0 0 * * MON")
    public void scrapeWeekly() {

    }*/

    @Scheduled(cron = "30 38 20 5 6 *")
    public void scrapeWeekly() {
        System.out.println("Se lanza el scheduled OOOOOOOOOOOKOOOOOOOOOOO");
    }

    @Scheduled(cron = "0 5 21 5 6 *")
    public void scrapeOnSpecificDate() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        List<Jugadores> jugadoresScrappeados = Scrapping.scrappeoCompleto(driver);
        System.out.println(jugadorController.insertarJugadores(jugadoresScrappeados).getStatusCode().is2xxSuccessful() ?
                "Ha ido bien" : "Algo ha fallado");
        driver.quit();
    }
}
