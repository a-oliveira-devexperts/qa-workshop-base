package com.devexperts.in.qatesting.configuration.tests;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.devexperts.in.qatesting.configuration.pages.HomePage;
import com.devexperts.in.qatesting.configuration.pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BalanceTest {

    private static final String USERNAME_DATA = PropertiesProvider.getProperty("test.user");
    private static final String PASSWORD_DATA = PropertiesProvider.getProperty("test.password");
    private static final String DEPOSIT_DATA = PropertiesProvider.getProperty("test.deposit");

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
    public void testInitialBalancesCheck(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.login(USERNAME_DATA, PASSWORD_DATA);

        HomePage homePage = new HomePage(page);
        assertAll("Initial Balances are equal to selected Deposit",
                () -> assertThat(homePage.getBalance()).hasText(DEPOSIT_DATA),
                () -> assertThat(homePage.getTotalFunds()).hasText(DEPOSIT_DATA));
    }

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
