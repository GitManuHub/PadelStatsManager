package com.padelstats.stats_manager.utils;

import com.padelstats.stats_manager.entities.Jugador;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Scrapping {

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

            js.executeScript("""
                        window.scrollBy({
                            top: 200,
                            left: 0,
                            behavior: 'smooth'
                        });
                    """);
            Thread.sleep(3000);

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
        } finally {
            driver.quit();
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
        }
        finally {
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

    public static List<Jugador> getPlayerInfoFromUrls(List<String> urls, WebDriver driver) {
        List<Jugador> playersInfo = new ArrayList<>();
        driver.manage().window().maximize();
        for (String url : urls) {
            driver.get(url);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String textoRanking = driver.findElement(By.cssSelector(".player__number")).getText().split("\\r?\\n")[0];
            int ranking = textoRanking != null ? Integer.parseInt(textoRanking) : 0;
            int points = Integer.parseInt(driver.findElement(By.cssSelector(".player__pointTNumber")).getText());
            String name = driver.findElement(By.cssSelector(".player__name")).getText();
            String country = driver.findElement(By.cssSelector(".player__country")).getText();
            Jugador j = new Jugador(ranking, points, name, country);
            playersInfo.add(j);
            System.out.println(j);
            /*String side = driver.findElement(By.cssSelector(".player__name")).getText();
            //String partner = driver.findElement(By.cssSelector(".player__name")).getText();
            String birthDate = driver.findElement(By.cssSelector(".player__name")).getText();
            String birthPlace = driver.findElement(By.cssSelector(".player__name")).getText();
            String height = driver.findElement(By.cssSelector(".player__name")).getText();
            String playedMatches = driver.findElement(By.cssSelector(".player__name")).getText();
            int wonMatches = Integer.parseInt(driver.findElement(By.cssSelector(".player__name")).getText());
            int lostMatches = Integer.parseInt(driver.findElement(By.cssSelector(".player__name")).getText());
            int winningStreak = Integer.parseInt(driver.findElement(By.cssSelector(".player__name")).getText());
            //double effectivity = Double.parseDouble(driver.findElement(By.cssSelector(".player__name")).getText());
            List<WebElement> countableRounds = driver.findElements(By.cssSelector(".player__name"));*/



            /*private int posicionRanking;
    private int puntos;
    private String nacionalidad;
    private String posicionPista;
    private String nombre;
    @OneToOne
    @JoinColumn(name = "pareja_id")
    private Jugador pareja;
    private String fechaNacimiento;
    private double altura;
    private String lugarNac;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosPerdidos;
    private int victoriasConsecutivas;
    private double efectividad;
    @OneToMany
    private List<RondaPuntuable> desglosePuntos;
*/
            //Jugador playerInfo = new Jugador(playerName, playerRanking);
            //playersInfo.add(playerInfo);
        }
        return playersInfo;
    }

}
