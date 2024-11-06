package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String HEADER_TITLE_SELECTOR = ".header-title-content";
    private static final String LOGIN_STATUS_SELECTOR = "#login-status";


    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){

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

        Locator homeHeaderPage = page.locator(HEADER_TITLE_SELECTOR);
        assertAll("Login Page Checks",
            () -> assertThat(homeHeaderPage).hasText("Home Test Task"),
            () -> assertThat(homeHeaderPage).isVisible());
    }

    @Test
    public void testLoginWithWrongCredentials() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername("not_existing_user@devexperts.com");
        loginPage.informPassword("password");
        loginPage.clickLogin();

        Locator loginStatus = page.locator(LOGIN_STATUS_SELECTOR);
        assertAll("Login Page Checks",
            () -> assertThat(loginStatus).hasText("Wrong user! User not_existing_user@devexperts.com not found."),
            () -> assertThat(loginStatus).isVisible());
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
