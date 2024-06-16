package com.padelstats.stats_manager.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesManagement {
    public static void saveUrlsToFile(List<String> urls, String fileName) throws IOException {
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
}
