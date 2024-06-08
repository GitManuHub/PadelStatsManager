package com.padelstats.stats_manager.utils;

import com.padelstats.stats_manager.entities.Jugadores;
import org.openqa.selenium.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.padelstats.stats_manager.utils.Parsing.*;

public class Scrapping {

    public static String FILENAME = "player_detail_urls.txt";

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

    private static void saveUrlsToFile(List<String> urls, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String joinedUrls = String.join(";", urls);
            writer.write(joinedUrls);
        }
    }

    public static List<String> readUrlsFromFile(String filename) {
        List<String> urls = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] urlArray = line.split(";");
                for (String url : urlArray) {
                    urls.add(url.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error leyendo el archivo");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return urls;
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
            } catch(NoSuchElementException | TimeoutException e) {
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
        String rutaBandera = getRutaBandera(nacionalidad);

        String imagen = driver.findElement(By.cssSelector(".slider__img.player__img > img")).getAttribute("src");

        WebElement infoAdicional = driver.findElement(By.cssSelector(".section__additionalInfo"));

        String clavePareja;
        try {
            clavePareja = getClaveDesdeUrl(infoAdicional.findElement(By.cssSelector(".additionalInfo__paired .content a")).getAttribute("href"));
        } catch (NoSuchElementException e) {
            clavePareja = "sin-pareja";
        }

        String ladoPista = infoAdicional.findElement(By.cssSelector(".additionalInfo__hand .content")).getText();
        double altura = tryParseDouble(infoAdicional.findElement(By.cssSelector(".additionalInfo__height .additionalInfo__data")).getText());
        String lugarNacimiento = infoAdicional.findElement(By.cssSelector(".additionalInfo__born .additionalInfo__data")).getText();
        String fechaNacimiento = infoAdicional.findElement(By.cssSelector(".additionalInfo__birth .additionalInfo__data")).getText();

        WebElement infoPartidos = driver.findElement(By.cssSelector(".section__matchPlayer"));
        int partidosJugados = tryParseInt(infoPartidos.findElement(By.cssSelector(".matchPlayer__played span")).getText());
        int partidosGanados = tryParseInt(infoPartidos.findElement(By.cssSelector(".matchPlayer__won span")).getText());
        int victoriasConsecutivas = tryParseInt(infoPartidos.findElement(By.cssSelector(".matchPlayer__victories span")).getText());

        return new Jugadores(puestoRanking, variacionPuesto,puntosRanking, nombre, nacionalidad, rutaBandera, ladoPista, new Jugadores(clavePareja),
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

}
