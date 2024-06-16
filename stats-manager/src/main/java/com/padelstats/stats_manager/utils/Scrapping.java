package com.padelstats.stats_manager.utils;

import ch.qos.logback.core.joran.conditional.ThenAction;
import com.padelstats.stats_manager.entities.Jugadores;
import com.padelstats.stats_manager.entities.Partidos;
import com.padelstats.stats_manager.entities.Rondas;
import com.padelstats.stats_manager.entities.Sets;
import com.padelstats.stats_manager.entities.Torneos;
import com.padelstats.stats_manager.services.TorneosService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.padelstats.stats_manager.utils.FilesManagement.readUrlsFromFile;
import static com.padelstats.stats_manager.utils.FilesManagement.saveUrlsToFile;
import static com.padelstats.stats_manager.utils.Parsing.*;

public class Scrapping {

    public static String FILENAME = "player_detail_urls.txt";

    @Autowired
    private TorneosService torneosService;

    public static List<Jugadores> scrappeoCompleto(WebDriver driver) {
        scrapRankingPP(driver);
        List<String> urls = readUrlsFromFile(FILENAME);
        return getPlayerInfoFromUrls(urls, driver);
    }

    public static void scrapRankingPP(WebDriver driver) {
        try {
            driver.manage().window().maximize();

            driver.get("https://www.padelfip.com/ranking-male/");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("""
                        window.scrollBy({
                            top: 250,
                            left: 0,
                            behavior: 'smooth'
                        });
                    """);

            Thread.sleep(5000);

            List<WebElement> playerElements = driver.findElements(By.cssSelector(".slider__name a"));

            List<String> playerDetailUrls = new ArrayList<>();

            for (int i = 1; i < 11; i++) {
                WebElement linkElement = playerElements.get(i);
                String detailUrl = linkElement.getAttribute("href");
                playerDetailUrls.add(detailUrl);
            }

            saveUrlsToFile(playerDetailUrls, "player_detail_urls.txt");

            js.executeScript("""
                        window.scrollBy({
                            top: 500,
                            left: 0,
                            behavior: 'smooth'
                        });
                    """);
            Thread.sleep(3000);

            js.executeScript("""
                        window.scrollBy({
                            top: 500,
                            left: 0,
                            behavior: 'smooth'
                        });
                    """);
            Thread.sleep(3000);

            js.executeScript("""
                        window.scrollBy({
                            top: 500,
                            left: 0,
                            behavior: 'smooth'
                        });
                    """);
            Thread.sleep(3000);

            js.executeScript("""
                        window.scrollBy({
                            top: 1200,
                            left: 0,
                            behavior: 'smooth'
                        });
                    """);
            Thread.sleep(3000);

            playerElements = driver.findElements(By.cssSelector(".playerGrid__name a"));

            for (int i = 0; i < 10; i++) {
                WebElement linkElement = playerElements.get(i);
                String detailUrl = linkElement.getAttribute("href");
                playerDetailUrls.add(detailUrl);
            }

            saveUrlsToFile(playerDetailUrls, "player_detail_urls.txt");

            WebElement loadMoreButton = driver.findElement(By.cssSelector("a.loadMoreRanking"));

            for (int i = 0; i < 3; i++) {
                loadMoreButton.click();
                Thread.sleep(3000);
                js.executeScript("""
                            window.scrollBy({
                                top: 1200,
                                left: 0,
                                behavior: 'smooth'
                            });
                        """);
            }

            /*js.executeScript("""
                        window.scrollBy({
                            top: 200,
                            left: 0,
                            behavior: 'smooth'
                        });
                    """);
            Thread.sleep(3000);*/

            playerElements = driver.findElements(By.cssSelector(".data-title"));

            for (int i = 0; i < 80; i++) {
                WebElement linkElement = playerElements.get(i);
                String detailUrl = linkElement.getAttribute("href");
                playerDetailUrls.add(detailUrl);
                System.out.println(detailUrl);
            }

            saveUrlsToFile(playerDetailUrls, "player_detail_urls.txt");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Jugadores> getPlayerInfoFromUrls(List<String> urls, WebDriver driver) {
        List<Jugadores> playersInfo = new ArrayList<>();
        driver.get(urls.get(0));
        driver.manage().window().maximize();

        for (String url : urls) {
            driver.navigate().to(url);
            //driver.manage().window().maximize();
            try {
                //Thread.sleep(200);
                Jugadores jugador = extractPlayerInfo(driver);
                jugador.setId(getClaveDesdeUrl(url));
                System.out.println(jugador);
                playersInfo.add(jugador);
                //} catch (InterruptedException e) {
                //Thread.currentThread().interrupt();
                //e.printStackTrace();
                // Handle specific exceptions or log them as needed
            } catch (NoSuchElementException | TimeoutException e) {
                e.printStackTrace();
            }
        }

        return playersInfo;
    }

    private static Jugadores extractPlayerInfo(WebDriver driver) {

        int puestoRanking = parseRanking(driver.findElement(By.cssSelector(".player__number")).getText());

        String variacionPuesto = getVariacionRanking(driver);

        int puntosRanking = Integer.parseInt(driver.findElement(By.cssSelector(".player__pointTNumber")).getText());
        String nombre = driver.findElement(By.cssSelector(".player__name")).getText();
        String nacionalidad = driver.findElement(By.cssSelector(".player__country")).getText();
        String rutaBandera = driver.findElement(By.cssSelector("div.slider__nation.player__nation > div > img")).getAttribute("src");

        String imagen = driver.findElement(By.cssSelector(".slider__img.player__img > img")).getAttribute("src");

        WebElement infoAdicional = driver.findElement(By.cssSelector(".section__additionalInfo"));

        String clavePareja;
        try {
            clavePareja = getClaveDesdeUrl(infoAdicional.findElement(By.cssSelector(".additionalInfo__paired .content a")).getAttribute("href"));
        } catch (NoSuchElementException e) {
            clavePareja = null;
        }

        String ladoPista = infoAdicional.findElement(By.cssSelector(".additionalInfo__hand .content")).getText();
        double altura = tryParseDouble(infoAdicional.findElement(By.cssSelector(".additionalInfo__height .additionalInfo__data")).getText());
        String lugarNacimiento = infoAdicional.findElement(By.cssSelector(".additionalInfo__born .additionalInfo__data")).getText();
        String fechaNacimiento = infoAdicional.findElement(By.cssSelector(".additionalInfo__birth .additionalInfo__data")).getText();

        WebElement infoPartidos = driver.findElement(By.cssSelector(".section__matchPlayer"));
        int partidosJugados = tryParseInt(infoPartidos.findElement(By.cssSelector(".matchPlayer__played span")).getText());
        int partidosGanados = tryParseInt(infoPartidos.findElement(By.cssSelector(".matchPlayer__won span")).getText());
        int victoriasConsecutivas = tryParseInt(infoPartidos.findElement(By.cssSelector(".matchPlayer__victories span")).getText());

        return new Jugadores(puestoRanking, variacionPuesto, puntosRanking, nombre, nacionalidad, rutaBandera, ladoPista, new Jugadores(clavePareja),
                fechaNacimiento, altura, lugarNacimiento, partidosJugados, partidosGanados, victoriasConsecutivas, imagen);

    }

    private static int parseRanking(String textoRanking) {
        String ranking = textoRanking.split("\\r?\\n")[0];
        return ranking != null ? Integer.parseInt(ranking) : 0;
    }

    private static String getRutaBandera(String nac) {
        String rutaBandera = "img/banderas/";
        switch (nac) {
            case "ESP":
                rutaBandera += "Spain";
                break;
            case "ARG":
                rutaBandera += "Argentina";
                break;
            case "BRA":
                rutaBandera += "Brasil";
                break;
            case "BEL":
                rutaBandera += "Belgica";
                break;
            case "CHL":
                rutaBandera += "Chile";
                break;
            case "EGY":
                rutaBandera += "Egipto";
                break;
            case "FRA":
                rutaBandera += "Francia";
                break;
            case "ITA":
                rutaBandera += "Italia";
                break;
            case "NLD":
                rutaBandera += "PaisesBajos";
                break;
            case "PRT":
                rutaBandera += "Portugal";
                break;
            case "QAT":
                rutaBandera += "Catar";
                break;
            case "GBR":
                rutaBandera += "ReinoUnido";
                break;
            case "SWE":
                rutaBandera += "Suecia";
                break;
            default:
                rutaBandera += "NotFound";
                break;
        }
        return rutaBandera;

    }


    public static List<Partidos> scrapTorneoCompleto(WebDriver driver, String urlTorneo) {
        String urlRonda = scrapRounds(driver, urlTorneo);
        List<String> urlsRondas = parseUrlRonda(urlRonda);
        List<Partidos> partidos = new ArrayList<>();

        List<Partidos> partidosRonda = new ArrayList<>();
        for (String ronda : urlsRondas) {
            partidos = scrapContenidoRonda(driver, ronda);
            if (partidos != null) {
                partidosRonda.addAll(partidos);
            }
        }

        partidosRonda.forEach(p -> System.out.println("Partidos Ronda: " + p));
        System.out.println(partidosRonda.size());

        return !partidosRonda.isEmpty() ? partidosRonda : null;



    }

    public static void scrapPartidosDeUnaRonda(WebDriver driver, String urlRondas) {
        driver.get(urlRondas);

    }

    public static String scrapRounds(WebDriver driver, String url) {
        String resultUrl = null;
        try {
            driver.get(url);
            driver.manage().window().maximize();
            resultUrl = driver.findElement(By.cssSelector("#event-tab-content-7 > iframe")).getAttribute("src");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        return resultUrl;
    }

    public static List<Partidos> scrapContenidoRonda(WebDriver driver, String url) {
        List<Partidos> partidos = new ArrayList<>();
        try {
            driver.get(url);
            driver.manage().window().maximize();

            List<WebElement> partidosElements = driver.findElements(By.cssSelector("#container > div:nth-child(2) > div"));
            for (WebElement partidoElement : partidosElements) {
                Partidos partido = new Partidos();


                WebElement ronda = partidoElement.findElement(By.cssSelector("small"));
                WebElement categoria = ronda.findElement(By.cssSelector("b"));
                String nombreRonda = ronda.getText().replace(categoria.getText(), "").trim();

                Rondas rondaPartido = new Rondas();
                rondaPartido.setNombre(nombreRonda);
                partido.setRonda(rondaPartido);

                List<WebElement> jugadores = partidoElement.findElements(By.cssSelector(".line-thin"));

                String jugador1 = Parsing.quitarRanking(jugadores.get(0).getText());
                String jugador2 = Parsing.quitarRanking(jugadores.get(1).getText());
                String jugador3 = Parsing.quitarRanking(jugadores.get(2).getText());
                String jugador4 = Parsing.quitarRanking(jugadores.get(3).getText());

                partido.setJugador1(new Jugadores("", jugador1));
                partido.setJugador2(new Jugadores("", jugador2));
                partido.setJugador3(new Jugadores("", jugador3));
                partido.setJugador4(new Jugadores("", jugador4));

                List<WebElement> sets = partidoElement.findElements(By.cssSelector(".set"));

                List<WebElement> setsPareja1 = partidoElement.findElements(By.cssSelector("tr.scorebox-sep-bottom .set-completed"));
                List<WebElement> setsPareja2 = partidoElement.findElements(By.cssSelector("tr:nth-of-type(3) .set-completed"));
                List<Sets> setsList = new ArrayList<>();
                for (int i = 0; i < setsPareja1.size(); i++) {
                    Sets set = new Sets();
                    set.setSetNumero(i + 1);
                    set.setPuntosPareja1(Integer.parseInt(setsPareja1.get(i).getText().replaceAll("[^\\d]", "").substring(0, 1)));
                    set.setPuntosPareja2(Integer.parseInt(setsPareja2.get(i).getText().replaceAll("[^\\d]", "").substring(0, 1)));
                    set.setPartido(partido);
                    setsList.add(set);
                }

                partido.setSets(setsList);
                int parejaGanadoraId;
                try {
                    WebElement parejaGanadora = partidoElement.findElements(By.cssSelector("tr")).get(1);
                    parejaGanadoraId = parejaGanadora.findElement(By.cssSelector(".fa-check.check-primary")) != null ? 1 : 2;
                } catch (NoSuchElementException e) {
                    parejaGanadoraId = 2;
                }

                //int parejaGanadoraId = partidoElement.findElement(By.cssSelector(".fa-check.check-primary")) != null ? 1 : 2;
                partido.setParejaGanadoraId(parejaGanadoraId);

                /*System.out.println(partido);

                partido.getSets().forEach(System.out::println);*/
                partidos.add(partido);
            }

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return partidos;
    }

    private static Sets getSets(List<WebElement> sets, int i, Partidos partido) {
        String parejaUno = sets.get(i).getText();
        if (sets.get(i).getText().contains("-")) {
            parejaUno = parejaUno.replace("-", "0");
        }

        String parejaDos = sets.get(i).getText();
        if (sets.get(i).getText().contains("-")) {
            parejaDos = parejaDos.replace("-", "0");
        }
        Sets set = new Sets();
        set.setSetNumero((i / 2) + 1);
        set.setPuntosPareja1(Integer.parseInt(parejaUno.replaceAll("[^\\d]", "").substring(0, 1)));
        set.setPuntosPareja2(Integer.parseInt(parejaDos.replaceAll("[^\\d]", "").substring(0, 1)));
        set.setPartido(partido);
        return set;
    }
}
