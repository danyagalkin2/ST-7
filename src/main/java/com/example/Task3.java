package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void run(WebDriver webDriver) throws Exception {
        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=56&longitude=44"
                + "&hourly=temperature_2m,rain"
                + "&timezone=Europe%2FMoscow"
                + "&forecast_days=1";
        webDriver.get(url);
        WebElement pre = webDriver.findElement(By.tagName("pre"));
        String jsonStr = pre.getText();

        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject) parser.parse(jsonStr);
        JSONObject hourly = (JSONObject) root.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temps = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        String header = String.format("| %-4s | %-20s | %-11s | %-12s |",
                "№", "Дата/время", "Температура", "Осадки (мм)");
        String sep   = "| ---- | -------------------- | ----------- | ------------ |";

        System.out.println(header);
        System.out.println(sep);

        File resultDir = new File("result");
        resultDir.mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter("result/forecast.txt"))) {
            pw.println(header);
            pw.println(sep);
            for (int i = 0; i < times.size(); i++) {
                String time = (String) times.get(i);
                Object tempObj = temps.get(i);
                Object rainObj = rains.get(i);
                double temp = tempObj instanceof Double ? (Double) tempObj : ((Long) tempObj).doubleValue();
                double rain = rainObj instanceof Double ? (Double) rainObj : ((Long) rainObj).doubleValue();
                String row = String.format("| %-4d | %-20s | %-11.1f | %-12.2f |",
                        i + 1, time, temp, rain);
                System.out.println(row);
                pw.println(row);
            }
        }
        System.out.println("Прогноз сохранён в result/forecast.txt");
    }
}
