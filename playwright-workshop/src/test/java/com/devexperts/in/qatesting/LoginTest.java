package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTest {

    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME_DATA = "q@q.q";
    private static final String PASSWORD_DATA = "Qw#45678";
    private static final String WRONG_PASSWORD = "Qw#456";
    private static final String STATUS_MESSAGE = "Wrong password! Correct password is: Qw#45678";
    private static final String INITIAL_BALANCE = "10000.00";


    @BeforeAll
    public static void BeforeAll(){
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
        loginPage.informUserName(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();

        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks" ,
                () ->assertThat(homeHeaderPage).hasText("Home Test Task") ,
                () ->assertThat(homeHeaderPage).isVisible());
    }

    @Test
    public void testLoginWithWrongCredentials(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUserName(USERNAME_DATA);
        loginPage.informPassword(WRONG_PASSWORD);
        loginPage.clickLogin();

        Locator errorMessage = page.locator("#login-status");
        assertAll("Error Message Checks" ,
                () ->assertThat(errorMessage).hasText(STATUS_MESSAGE) ,
                () ->assertThat(errorMessage).isVisible());
    }

    @Test
    public void checkBalancesAfterAccountCreation(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUserName(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();

        Locator balance = page.locator("#balanceInfoNumber");
        assertAll("Balance Checks" ,
                () ->assertThat(balance).hasText(INITIAL_BALANCE),
                () ->assertThat(balance).isVisible());
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
