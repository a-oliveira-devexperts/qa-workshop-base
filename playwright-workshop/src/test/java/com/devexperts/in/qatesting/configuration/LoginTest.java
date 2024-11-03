package com.devexperts.in.qatesting.configuration;

import com.devexperts.in.qatesting.LoginPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;


public class LoginTest {

    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME_DATA = PropertiesProvider.getProperty("username");
    private static final String PASSWORD_DATA = PropertiesProvider.getProperty("password");
    private static final String USERNAME_WRONG_DATA = PropertiesProvider.getProperty("wrong_username");
    private static final String PASSWORD_WRONG_DATA = PropertiesProvider.getProperty("wrong_password");


    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @Test
    public void testSuccessfulLogin(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();
        HomePage homePage = new HomePage(page);
        homePage.assertHeaderHomepage("Home Test Task");
    }

    @Test
    public void testLoginWithWrongUser(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_WRONG_DATA);
        loginPage.informPassword(PASSWORD_WRONG_DATA);
        loginPage.clickLogin();
        // Checking error about wrong user
        loginPage.assertWrongUserStatus(USERNAME_WRONG_DATA);
    }

    @Test
    public void testLoginWithWrongPassword(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_WRONG_DATA);
        loginPage.clickLogin();
        // Checking error about wrong password
        loginPage.assertWrongPasswordStatus(PASSWORD_DATA);
    }

    @Test
    public void testLoginWithoutUserData(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername("");
        loginPage.informPassword("");
        loginPage.clickLogin();
        // Checking error about invalid credentials
        loginPage.assertInvalidCredentialsStatus();
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
