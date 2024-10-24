package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    private static Playwright playwright;
    private Browser browser;
    private Page page;

    private static final String USERNAME_DATA = "gmshn.edu@gmail.com";
    private static final String PASSWORD_DATA = "autoQAedu@2024";

    @BeforeAll
    public static void createPlaywright(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        //Setup: create Browser, Page
        browser = playwright.chromium().launch();
        //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @Test
    public void testSuccessfulLogin(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();

        HomePage homePage = new HomePage(page);
        homePage.assertHeaderHomepage("Home Test Task");
    }

    @AfterEach
    public void tearDown(){
        page.close();
        browser.close();
    }

    @AfterAll
    public static void closePlaywright(){
        playwright.close();
    }
}