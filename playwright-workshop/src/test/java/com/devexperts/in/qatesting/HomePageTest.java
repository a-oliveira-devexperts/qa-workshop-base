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
        //Create Playwright
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setupAndLogin(){
        //Create Browser, Page
        //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browser = playwright.chromium().launch();
        page = browser.newPage();
        //Open Login Page
        page.navigate(PropertiesProvider.getProperty("base.url"));
        //Login
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
        //Logout
        HomePage homePage = new HomePage(page);
        homePage.clickLogout();
        //Close page and browser
        page.close();
        browser.close();
    }

    @AfterAll
    public static void afterAll(){
        //Close playwright
        playwright.close();
    }

}
