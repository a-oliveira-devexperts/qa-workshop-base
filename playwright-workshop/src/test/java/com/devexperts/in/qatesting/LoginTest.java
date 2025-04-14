package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.awt.SystemColor.text;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME_DATA = "vsousa@devexperts.com";
    private static final String PASSWORD_DATA = "198881^^ZXcvb";
    private static final String INCORRECT_USERNAME_DATA = "vsou@devexperts.com";
    private static final String INCORRECT_PASSWORD_DATA = "123456^^ASdfg";


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
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();

        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks" ,
                () -> assertThat(homeHeaderPage).hasText("Home Test Task"),
                () -> assertThat(homeHeaderPage).isVisible());
    }


    @Test
    public void testLoginWithWrongCredentials() {

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(INCORRECT_USERNAME_DATA);
        loginPage.informPassword(INCORRECT_PASSWORD_DATA);
        loginPage.clickLogin();

        Locator errorMessage = page.locator("#login-status");
        assertAll("Login Failure Checks",
                () -> assertTrue(errorMessage.isVisible()),
                () -> assertThat(errorMessage).hasText("Wrong user! User " + INCORRECT_USERNAME_DATA + " not found."));

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