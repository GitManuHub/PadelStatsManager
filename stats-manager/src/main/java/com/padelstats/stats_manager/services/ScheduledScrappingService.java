package com.padelstats.stats_manager.services;

import com.padelstats.stats_manager.controllers.JugadorController;
import com.padelstats.stats_manager.controllers.PartidosController;
import com.padelstats.stats_manager.controllers.RondasController;
import com.padelstats.stats_manager.controllers.SetsController;
import com.padelstats.stats_manager.controllers.TorneosController;
import com.padelstats.stats_manager.dto.TorneoDTO;
import com.padelstats.stats_manager.entities.Jugadores;
import com.padelstats.stats_manager.entities.Partidos;
import com.padelstats.stats_manager.entities.Sets;
import com.padelstats.stats_manager.utils.Scrapping;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@EnableScheduling
public class ScheduledScrappingService {

    @Autowired
    private JugadorController jugadorController;

    @Autowired
    private TorneosController torneosController;

    @Autowired
    private PartidosController partidosController;

    @Autowired
    private RondasController rondasController;
    @Autowired
    private SetsController setsController;


    /*@Scheduled(cron = "0 0 0 * * MON")
    public void scrapeWeekly() {

    }*/

    @Scheduled(cron = "0 2 12 17 6 *")
    public void scrapeWeekly() {
        System.out.println("Se lanza el scheduled OOOOOOOOOOOKOOOOOOOOOOO");
    }

    @Scheduled(cron = "0 15 13 17 6 *")
    public void scrapeOnSpecificDate() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        List<Jugadores> jugadoresScrappeados = Scrapping.scrappeoCompleto(driver);
        System.out.println(jugadorController.insertarJugadores(jugadoresScrappeados).getStatusCode().is2xxSuccessful() ?
                "Ha ido bien" : "Algo ha fallado");
        driver.quit();
    }

    @Scheduled(cron = "0 43 13 17 6 *")
    public void scrapRondas() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        try {


        String FINAL_URL = "?tab=Results";
        List<TorneoDTO> torneosConId = torneosController.torneosPasadosConId();

        torneosConId.forEach(t -> System.out.println("Ids: " + t.getId()));
        //torneosConId.forEach(t -> Scrapping.scrapTorneoCompleto(driver, t.getUrl() + FINAL_URL));
        List<Partidos> partidosTorneo = new ArrayList<>();
        for (int i = 0; i < torneosConId.size(); i++) {
            partidosTorneo = Scrapping.scrapTorneoCompleto(driver, torneosConId.get(i).getUrl() + FINAL_URL);
            if (partidosTorneo != null && !partidosTorneo.isEmpty()) {
                for (Partidos partido : partidosTorneo) {
                    System.out.println("Partido cuando empezamos a insertar: " + partido);
                    int idRonda = rondasController.getRondaId(torneosConId.get(i).getId(), partido.getRonda().getNombre());
                    partido.getRonda().setId(idRonda);
                    List<Sets> sets = partido.getSets();
                    partido.setSets(new ArrayList<>());
                    ResponseEntity<String> idJ1 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador1().getNombre());
                    ResponseEntity<String> idJ2 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador2().getNombre());
                    ResponseEntity<String> idJ3 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador3().getNombre());
                    ResponseEntity<String> idJ4 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador4().getNombre());

                    if (idJ1.getStatusCode() == HttpStatus.OK && idJ2.getStatusCode() == HttpStatus.OK
                            && idJ3.getStatusCode() == HttpStatus.OK && idJ4.getStatusCode() == HttpStatus.OK) {
                        partido.getJugador1().setId(idJ1.getBody());
                        partido.getJugador2().setId(idJ2.getBody());
                        partido.getJugador3().setId(idJ3.getBody());
                        partido.getJugador4().setId(idJ4.getBody());
                        ResponseEntity<Partidos> partidoInsertado = partidosController.insertarPartido(partido);
                        if (partidoInsertado.getStatusCode() == HttpStatus.CREATED) {
                            for (Sets set : sets) {
                                set.setPartido(partidoInsertado.getBody());
                                ResponseEntity<Sets> setInsertado = setsController.guardar(set);

                                if (setInsertado.getStatusCode() == HttpStatus.CREATED) {
                                    set.setId(Objects.requireNonNull(setInsertado.getBody()).getId());
                                }

                            }

                            partido.setSets(sets);
                            System.out.println("Partido al insertarlo: " + partido);
                            partidosController.insertarPartido(partido);
                        }
                    }
                }
            }
        }
        } catch (NoSuchElementException e) {
            System.out.println("Fallo en scapRondas");
        }
        //Scrapping.scrapTorneoCompleto(driver, "https://www.padelfip.com/events/riyadh-season-premier-padel-p1-2024/" + FINAL_URL);
        driver.quit();
    }

    @Scheduled(cron = "0 0 3 ? * 4")
    public void scrapRondasWeekly() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        try {


            String FINAL_URL = "?tab=Results";
            List<TorneoDTO> torneosConId = torneosController.torneosPasadosConId();

            torneosConId.forEach(t -> System.out.println("Ids: " + t.getId()));
            //torneosConId.forEach(t -> Scrapping.scrapTorneoCompleto(driver, t.getUrl() + FINAL_URL));
            List<Partidos> partidosTorneo = new ArrayList<>();
            for (int i = 0; i < torneosConId.size(); i++) {
                partidosTorneo = Scrapping.scrapTorneoCompleto(driver, torneosConId.get(i).getUrl() + FINAL_URL);
                if (partidosTorneo != null && !partidosTorneo.isEmpty()) {
                    for (Partidos partido : partidosTorneo) {
                        System.out.println("Partido cuando empezamos a insertar: " + partido);
                        int idRonda = rondasController.getRondaId(torneosConId.get(i).getId(), partido.getRonda().getNombre());
                        partido.getRonda().setId(idRonda);
                        List<Sets> sets = partido.getSets();
                        partido.setSets(new ArrayList<>());
                        ResponseEntity<String> idJ1 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador1().getNombre());
                        ResponseEntity<String> idJ2 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador2().getNombre());
                        ResponseEntity<String> idJ3 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador3().getNombre());
                        ResponseEntity<String> idJ4 = jugadorController.getJugadorIdByNombreSimilar(partido.getJugador4().getNombre());

                        if (idJ1.getStatusCode() == HttpStatus.OK && idJ2.getStatusCode() == HttpStatus.OK
                                && idJ3.getStatusCode() == HttpStatus.OK && idJ4.getStatusCode() == HttpStatus.OK) {
                            partido.getJugador1().setId(idJ1.getBody());
                            partido.getJugador2().setId(idJ2.getBody());
                            partido.getJugador3().setId(idJ3.getBody());
                            partido.getJugador4().setId(idJ4.getBody());
                            ResponseEntity<Partidos> partidoInsertado = partidosController.insertarPartido(partido);
                            if (partidoInsertado.getStatusCode() == HttpStatus.CREATED) {
                                for (Sets set : sets) {
                                    set.setPartido(partidoInsertado.getBody());
                                    ResponseEntity<Sets> setInsertado = setsController.guardar(set);

                                    if (setInsertado.getStatusCode() == HttpStatus.CREATED) {
                                        set.setId(Objects.requireNonNull(setInsertado.getBody()).getId());
                                    }

                                }

                                partido.setSets(sets);
                                System.out.println("Partido al insertarlo: " + partido);
                                partidosController.insertarPartido(partido);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Fallo en scapRondas");
        }
        //Scrapping.scrapTorneoCompleto(driver, "https://www.padelfip.com/events/riyadh-season-premier-padel-p1-2024/" + FINAL_URL);
        driver.quit();
    }
}
