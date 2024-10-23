package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    private Page page;
    private static Playwright playwright;
    Browser browser;

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }
    @BeforeEach
    public void setUp(){
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
    }

    @Test
    public void testSuccessfulLogin(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername("a-oliveira-group-a@devexperts.com");
        loginPage.informPassword("Workshop123@");
        loginPage.clickLogin();

        page.waitForSelector(".header-title-content");
        Locator homeHeader = page.locator(".header-title-content");

        assertAll("Login Checks",
                ()-> assertThat(homeHeader).hasText("Home Test Task", new LocatorAssertions.HasTextOptions().setIgnoreCase(false)),
                ()-> assertThat(homeHeader).isVisible());

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
