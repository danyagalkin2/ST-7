package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String[] args) throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver webDriver = new ChromeDriver(options);

        try {
            // Задание №1: генератор паролей
            webDriver.get("https://www.calculator.net/password-generator.html");
            Thread.sleep(2000);
            webDriver.findElement(By.xpath("//input[@type='submit']")).click();
            Thread.sleep(2000);
            WebElement passwordElem = webDriver.findElement(By.xpath("//div[contains(@class,'verybigtext')]/b"));
            System.out.println("Generated password: " + passwordElem.getText());

            // Задание №2: IP-адрес
            Task2.run(webDriver);

            // Задание №3: прогноз погоды
            Task3.run(webDriver);
        } finally {
            webDriver.quit();
        }
    }
}
