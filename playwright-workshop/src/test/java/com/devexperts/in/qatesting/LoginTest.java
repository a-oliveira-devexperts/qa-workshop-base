package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.logging.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    //private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME_DATA = "iamritaferraz@gmail.com";
    private static final String PASSWORD_DATA = "Internet1!";

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

