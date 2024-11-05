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
    private static final String USERNAME_DATA = "pmitev@devexperts.com";
    private static final String USERNAME_PASSWORD = "Peco0877249094!";
    private static final String WRONG_USERNAME_DATA = "pmitevv@devexperts.com";
    private static final String WRONG_USERNAME_PASSWORD_DATA = "Peco0877249094";


    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();

    }

    @BeforeEach
    public void setUp() {
        //browser = playwright.chromium().launch();
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }
    @Test
    public void testSuccessfulLogin(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(USERNAME_PASSWORD);
        loginPage.clickLogin();
        Locator homeHeaderPage = page.locator(".header-title-content");
        homeHeaderPage.waitFor();
        assertAll("Login Checks",
                () ->assertThat(homeHeaderPage).hasText("Home Test Task"),
                () ->assertThat(homeHeaderPage).isVisible());
    }

    @Test
    public void testLoginWithWrongCredentials(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(WRONG_USERNAME_DATA);
        loginPage.informPassword(WRONG_USERNAME_PASSWORD_DATA);
        loginPage.clickLogin();
        Locator homeHeaderPage = page.locator(".header-title-content");
        //homeHeaderPage.waitFor();
        assertAll("Login Checks",
                () ->assertThat(homeHeaderPage).isHidden());
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


