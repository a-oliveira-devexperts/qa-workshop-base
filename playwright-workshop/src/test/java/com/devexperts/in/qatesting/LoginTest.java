package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    private static Playwright playwright;
    private Browser browser;
    private Page page;

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
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
    }

    @Test
    public void testSuccessfulLogin(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername("gmshn.edu@gmail.com");
        loginPage.informPassword("autoQAedu@2024");
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