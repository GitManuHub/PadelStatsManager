package com.padelstats.stats_manager.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrapping {
    public static void scrapRankingPP() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();

            driver.get("https://www.padelfip.com/ranking-male/");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,275)", "");

            Thread.sleep(5000);

            List<WebElement> playerElements = driver.findElements(By.cssSelector(".slider__name a"));
            playerElements.forEach(e -> System.out.println(e.getText()));

            List<String> playerDetailUrls = new ArrayList<>();

            for (int i = 1; i < 11; i++) {
                WebElement linkElement = playerElements.get(i);
                String detailUrl = linkElement.getAttribute("href");
                playerDetailUrls.add(detailUrl);
                System.out.println();
            }

            saveUrlsToFile(playerDetailUrls, "player_detail_urls.txt");

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
            }

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
            // Cerrar el navegador
            driver.quit();
        }
    }

    private static void saveUrlsToFile(List<String> urls, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String joinedUrls = String.join(";", urls);
            writer.write(joinedUrls);
        }
    }

//    private static void saveUrlsToFile(List<String> urls, String fileName) throws IOException {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            for (String url : urls) {
//                writer.write(url);
//                writer.newLine();
//            }
//        }
//    }

}
