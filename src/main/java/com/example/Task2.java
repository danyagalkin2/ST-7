package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {
    public static void run(WebDriver webDriver) throws Exception {
        webDriver.get("https://api.ipify.org/?format=json");
        WebElement pre = webDriver.findElement(By.tagName("pre"));
        String jsonStr = pre.getText();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonStr);
        System.out.println("Your IP: " + obj.get("ip"));
    }
}
