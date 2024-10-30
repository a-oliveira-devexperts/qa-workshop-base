package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public  class SuccessfulLoginTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME = "pkaramanova@example.com";
    private static final String PASSWORD = "MyPass1234!";

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
        LoginPage loginPage= new LoginPage(page);
        loginPage.provideUsername(USERNAME);
        loginPage.providePassword(PASSWORD);
        loginPage.clickLogin();

        //Verify the Home page for logged user is displayed
        Locator homePageTitle = page.locator(".header-title-content");

        assertAll("Login Page Check",
                () -> assertThat(homePageTitle).isVisible(),
                () -> assertThat(homePageTitle).hasText("Home Test Task"));
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
