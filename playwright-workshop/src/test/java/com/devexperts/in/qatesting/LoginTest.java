package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME_DATA = "atykhonova@devexperts.com";
    private static final String USERNAME_WRONG_DATA = "atykhonova2@devexperts.com";
    private static final String PASSWORD_DATA = "Greece93!";

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp() {

        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @Test
    public void testSuccessfulLogin() {

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();
        loginPage.homePageChecks();
    }

    @Test
    public void testWithWrongCredentials() {

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_WRONG_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();
        loginPage.loginStatus();
    }

    @Test
    public void checkBalanceValue(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();
        loginPage.balanceNumber();
    }

    @AfterEach
    public void tearDown() {
        page.close();
        browser.close();
    }

    @AfterAll
    public static void afterAll(){
        playwright.close();
    }
}

