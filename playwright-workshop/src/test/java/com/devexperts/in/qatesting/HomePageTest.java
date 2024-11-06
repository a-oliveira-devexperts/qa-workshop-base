package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HomePageTest {

    private static Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUP(){
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(PropertiesProvider.getProperty("test.user"));
        loginPage.informPassword(PropertiesProvider.getProperty("test.password"));
        loginPage.clickLogin();
    }

    @Test
    public void checkAccountBalance(){
        HomePage homePage = new HomePage(page);
        assertAll("Account Balance Checks",
                () -> assertThat(homePage.getBalance()).hasText(PropertiesProvider.getProperty("initial.account.balance")),
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