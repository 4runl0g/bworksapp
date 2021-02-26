package com.sample.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverConfig {

    public WebDriver getChromeDriver() {
        WebDriver chromeDriver = new ChromeDriver();
        return chromeDriver;
    }
}