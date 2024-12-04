package com.devexperts.in.qatesting.testSteps;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.devexperts.in.qatesting.pages.LoginPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidCredentialsLoginTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String WRONG_USERNAME = PropertiesProvider.getProperty("test.wronguser");
    private static final String PASSWORD = PropertiesProvider.getProperty("test.password");

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
    public void testLoginWithWrongUsername(){
        LoginPage loginPage= new LoginPage(page);
        loginPage.provideUsername(WRONG_USERNAME);
        loginPage.providePassword(PASSWORD);
        loginPage.clickLogin();
        page.waitForTimeout(2000);
        loginPage.getInvalidStatusCheck();

        //Verify that the unsuccessful login status check is displayed and that it contains the wrong username provided
        assertEquals(loginPage.getInvalidStatusCheck().textContent(), "Wrong user! User " + WRONG_USERNAME + " not found.");

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


