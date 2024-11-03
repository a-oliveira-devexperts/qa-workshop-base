package com.devexperts.in.qatesting.configuration;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HomePageBalanceTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;
    private static final String USERNAME_DATA = PropertiesProvider.getProperty("username");
    private static final String PASSWORD_DATA = PropertiesProvider.getProperty("password");
    private static final String BALANCE_DATA = PropertiesProvider.getProperty("account.balance");

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setupAndLogin(){

        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
        //Login
        com.devexperts.in.qatesting.LoginPage loginPage = new com.devexperts.in.qatesting.LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();
    }

    @Test
    public void checkAccountBalance(){
        com.devexperts.in.qatesting.LoginPage loginPage = new com.devexperts.in.qatesting.LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();
        HomePage homePage = new HomePage(page);
        assertAll("Account Balance Checks",
                () -> assertThat(homePage.getBalance()).hasText(BALANCE_DATA),
                () -> assertThat(homePage.getBalance()).isVisible());
    }

    @AfterEach
    public void tearDown(){

        HomePage homePage = new HomePage(page);
        homePage.clickLogout();

        page.close();
        browser.close();
    }
    @AfterAll
    public static void afterAll(){

        playwright.close();
    }
}
