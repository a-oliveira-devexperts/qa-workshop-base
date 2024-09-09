package com.devexperts.in.qatesting.internship.test;

import com.devexperts.in.qatesting.internship.configuration.PropertiesProvider;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    private static Playwright playwright;
    private static Browser browser;
    protected Page page;

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    public void setup(){
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @AfterEach
    public void tearDown(){
        page.close();
    }

    public void afterAll(){
        browser.close();
        playwright.close();
    }
}
