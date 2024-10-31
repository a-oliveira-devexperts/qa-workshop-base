package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME_DATA = PropertiesProvider.getProperty("test.user");
    private static final String PASSWORD_DATA = PropertiesProvider.getProperty("test.password");
    private static final String USERNAME_WRONG_DATA = PropertiesProvider.getProperty("wrong.test.user");
    private static final String PASSWORD_WRONG_DATA = PropertiesProvider.getProperty("wrong.test.password");

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
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();

        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks",
                ()->assertThat(homeHeaderPage).hasText("Home Test Task"),
                ()->assertThat(homeHeaderPage).isVisible());
    }

    @Test
    public void testLoginWithWrongCredentials(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_WRONG_DATA);
        loginPage.informPassword(PASSWORD_WRONG_DATA);
        loginPage.clickLogin();
        Locator loginStatus = page.locator("#login-status");
        assertAll("Login Wrong Credentials",
                ()->assertThat(loginStatus).isVisible(),
                ()->assertThat(loginStatus).hasText("Wrong user! User a; not found."));
    }

    @Test
    public void testLoginWithWrongPassword(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_WRONG_DATA);
        loginPage.clickLogin();
        Locator loginStatus = page.locator("#login-status");
        assertAll("Login Wrong Password",
                ()->assertThat(loginStatus).isVisible(),
                ()->assertThat(loginStatus).hasText("Wrong password! Correct password is: Internet1!"));
    }

    @Test
    public void testLoginWithEmptyCredentials(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_WRONG_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();
        Locator loginStatus = page.locator("#login-status");
        assertAll("Login Empty Credentials",
                ()->assertThat(loginStatus).isVisible(),
                ()->assertThat(loginStatus).hasText("Please enter valid credentials:"));
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

