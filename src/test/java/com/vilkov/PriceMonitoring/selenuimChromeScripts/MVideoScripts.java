package com.vilkov.PriceMonitoring.selenuimChromeScripts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class MVideoScripts {
    public WebDriver driver;

    @Test
    public void pageScriptsTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        driver = new ChromeDriver();
        driver.get("https://mvideo.ru");

        String title = driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        Assertions.assertEquals("М.Видео - интернет-магазин цифровой и бытовой техники и электроники, низкие цены, большой каталог, отзывы.", title);

//        WebElement searchBox = driver.findElement(By.className("_ngcontent-serverapp-c81"));
        WebElement searchBox = driver.findElement(By.tagName("input"));
//        WebElement searchButton = driver.findElement(By.name("span"));

        searchBox.sendKeys("");
//        searchButton.click();
//
//        String value = searchBox.getAttribute("price");

        driver.quit();
    }
}
