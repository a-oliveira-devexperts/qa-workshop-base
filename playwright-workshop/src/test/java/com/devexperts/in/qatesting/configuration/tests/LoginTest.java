package com.devexperts.in.qatesting.configuration.tests;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.devexperts.in.qatesting.configuration.pages.HomePage;
import com.devexperts.in.qatesting.configuration.pages.LoginPage;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    private static final String USERNAME_DATA = PropertiesProvider.getProperty("test.user");
    private static final String PASSWORD_DATA = PropertiesProvider.getProperty("test.password");

    private static Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeAll
    public static void createPlaywright(){
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
        loginPage.login(USERNAME_DATA, PASSWORD_DATA);

        HomePage homePage = new HomePage(page);
        assertThat(homePage.getHeaderHomepage()).hasText("Home Test Task");
    }

    @Test
    public void testUnsuccessfulLoginWithBlankUsername(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("", "Test1234!");

        Locator errorText = page.locator("#login-status");
        assertThat(errorText).hasText("Please enter valid credentials:");
    }

    //testUnsuccessfulLoginWithBlankPassword
    //testUnsuccessfulLoginWithInvalidUsername
    //testUnsuccessfulLoginWithInvalidPassword

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
