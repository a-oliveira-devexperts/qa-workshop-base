package com.devexperts.in.qatesting.testSteps;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.devexperts.in.qatesting.pages.HomePage;
import com.devexperts.in.qatesting.pages.LoginPage;
import com.devexperts.in.qatesting.pages.RegistrationPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;


import static com.devexperts.in.qatesting.pages.RegistrationPage.EmailGenerator.generateNewEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceCheckTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String PASSWORD = PropertiesProvider.getProperty("test.password");
    private static final String NEW_USERNAME = generateNewEmail();

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
    public void testBalanceMatching(){
        LoginPage loginPage = new LoginPage(page);
        RegistrationPage registrationPage = new RegistrationPage(page);
        HomePage homePage = new HomePage(page);

        loginPage.OpenRegistration();
        registrationPage.provideDesiredUsername(NEW_USERNAME);
        registrationPage.provideDesiredPassword(PASSWORD);
        registrationPage.selectDeposit();
        //registrationPage.clickRegister();
        registrationPage.loginAfterRegistration();
        page.waitForTimeout(1000);

        assertEquals("1000.00", homePage.getBalanceOnHomePage().textContent());
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
