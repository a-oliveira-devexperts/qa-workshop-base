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

    @BeforeAll
    public static void beforeAll(){
        //Create Playwright, Browser, Page
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setup(){
        //Setup
        browser = playwright.chromium().launch();
        page = browser.newPage();
        //Open Login Page
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @Test
    public void testSuccessfulLogin(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(PropertiesProvider.getProperty("test.user"));
        loginPage.informPassword(PropertiesProvider.getProperty("test.password"));
        loginPage.clickLogin();
        HomePage homePage = new HomePage(page);

        //Type1
            //assertThat(homeHeaderPage).hasText("Home TestD Task");
            //assertThat(homeHeaderPage).isVisible();

        //Type2
            //assertAll("Login Page Checks",
            //        () -> assertEquals("Home Test Task", homeHeaderPage.innerText()),
            //        () -> assertTrue(homeHeaderPage.isVisible()));

        //Type3
            assertAll("Login Page Checks",
                    () -> assertThat(HomePage.getHomePageHeader()).hasText("Home Test Task"),
                    () -> assertThat(HomePage.getHomePageHeader()).isVisible());
    }

    @Test
    public void testLoginWithWrongCredentials(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(PropertiesProvider.getProperty("test.user"));
        loginPage.informPassword(PropertiesProvider.getProperty("test.wrong.password"));
        loginPage.clickLogin();
        assertThat(LoginPage.getLoginStatus()).containsText("Wrong password!");
    }

    @AfterEach
    public void tearDown(){
        //Close everything
        page.close();
        browser.close();
    }

    @AfterAll
    public static void afterAll(){
        playwright.close();
    }

}
