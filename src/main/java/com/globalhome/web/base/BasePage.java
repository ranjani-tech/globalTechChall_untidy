package com.globalhome.web.base;

import org.openqa.selenium.WebDriver;

public class BasePage extends BaseAction{

    private WebDriver driver;

    //Constructor
    public BasePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}

