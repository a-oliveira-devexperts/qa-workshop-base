package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTests {
    public static Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUP(){
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @Test
    public void testSuccessfulLogin(){
        
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(PropertiesProvider.getProperty("test.user"));
        loginPage.informPassword(PropertiesProvider.getProperty("test.password"));
        loginPage.clickLogin();
        HomePage homePage = new HomePage(page);

        assertAll("Login Page Checks",
                () -> assertThat(homePage.getHomePageHeader()).hasText("Home Test Task"),
                () -> assertThat(homePage.getHomePageHeader()).isVisible());
    }

    @Test
    public void testLoginWithWrongCredentials(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(PropertiesProvider.getProperty("test.user"));
        loginPage.informPassword(PropertiesProvider.getProperty("test.wrong.password"));
        loginPage.clickLogin();
        assertThat(LoginPage.getLoginStatus()).containsText("Wrong password!");
    }

    @AfterEach
    public void tearDown(){
        page.close();
        browser.close();
    }

    @AfterAll
    public static void afterAll(){
        playwright.close();
    }
}


