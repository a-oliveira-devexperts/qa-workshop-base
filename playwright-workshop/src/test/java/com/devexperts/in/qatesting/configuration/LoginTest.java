package com.devexperts.in.qatesting.configuration;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    private static Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        //Create Browser, Page
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        page = browser.newPage();
        //Open Login Page
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
    }

    @Test
    public void testSuccessfulLogin(){

        //Inform username
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("kpevzner@devexperts.com");
        //Inform password
        //Locator inputPassword = page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Evangelion2223!");
        //Click login button
        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        //Check if we were redirected to the homepage
        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks",
                () ->assertThat(homeHeaderPage).hasText("Home Test Task"),
                () ->assertThat(homeHeaderPage).isVisible());
    }

    @Test
    public void testLoginWrongCredentials(){

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
