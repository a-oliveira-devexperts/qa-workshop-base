package com.devexperts.in.qatesting;

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
    public static void createPlaywright(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        //Setup: create Browser, Page
        browser = playwright.chromium().launch();
        //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
    }

    @Test
    public void testSuccessfulLogin(){
        //Enter Username and Password and Log in
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("gmshn.edu@gmail.com");
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("autoQAedu@2024");
        Locator loginButton = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        //Check that we are redirected to home page
        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks",
                () ->assertThat(homeHeaderPage).hasText("Home Test Task"),
                () ->assertThat(homeHeaderPage).isVisible());
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