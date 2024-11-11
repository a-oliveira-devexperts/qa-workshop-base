package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BalanceTest {
    private static Playwright playwright;
    private Page page;
    private static final String USERNAME_DATA = PropertiesProvider.getProperty("test.user");
    private static final String PASSWORD_DATA = PropertiesProvider.getProperty("test.password");

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        Browser browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @Test
    public void testBalanceEqualInitial(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.informUsername(USERNAME_DATA);
        loginPage.informPassword(PASSWORD_DATA);
        loginPage.clickLogin();

        //Locator balanceSection = page.locator(".section-container section-balance");
        Locator balanceValue = page.locator(".balanceNumber");
        assertAll ("Balance Checks",
            ()->assertThat(balanceValue).isVisible(),
            ()->assertThat(balanceValue).containsText("10000.00"));
    }
}
