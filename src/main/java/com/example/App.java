package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        setupChromeDriver();

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

    // Ищет chromedriver в кэше Selenium Manager (~/.cache/selenium/chromedriver)
    private static void setupChromeDriver() {
        File cacheDir = new File(System.getProperty("user.home"), ".cache/selenium/chromedriver");
        File driver = findDriver(cacheDir);
        if (driver != null) {
            System.setProperty("webdriver.chrome.driver", driver.getAbsolutePath());
        }
    }

    private static File findDriver(File dir) {
        if (dir == null || !dir.isDirectory()) return null;
        File[] children = dir.listFiles();
        if (children == null) return null;
        for (File child : children) {
            if (child.isDirectory()) {
                File found = findDriver(child);
                if (found != null) return found;
            } else if (child.getName().equals("chromedriver") && child.canExecute()) {
                return child;
            }
        }
        return null;
    }
}
