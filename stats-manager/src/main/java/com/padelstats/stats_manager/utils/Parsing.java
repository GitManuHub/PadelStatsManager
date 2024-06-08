package com.padelstats.stats_manager.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Parsing {
    public static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static String getVariacionRanking(WebDriver driver) {
        try {
            if (driver.findElement(By.cssSelector(".up")).getText() != null) {
                return "S" + driver.findElement(By.cssSelector(".up")).getText().trim();
            }
        } catch (NoSuchElementException e) {
            System.out.println("No ha subido puestos");
        }

        try {
            if (driver.findElement(By.cssSelector(".down")).getText() != null) {
                return "B" + driver.findElement(By.cssSelector(".down")).getText().trim();
            }
        } catch (NoSuchElementException e) {
            System.out.println("No ha bajado puestos");
        }

        return "0";
    }

    public static String getClaveDesdeUrl(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        int ultimaBarra = url.lastIndexOf("/");
        return url.substring(ultimaBarra + 1);
    }
}
