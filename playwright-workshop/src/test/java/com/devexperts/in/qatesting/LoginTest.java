package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    @Test
    public void testSuccessfulLogin(){
        //Setup
        //Create Playwright, Browser, Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        //Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        //Open Login Page
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
        //Inform username
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("gmshn.edu@gmail.com");
        //Inform password
        Locator inputPassword = page.locator("#password");
        //Locator inputPassword = page.getByPlaceholder("Password" , new Page.GetByPlaceholderOptions().setExact(true));
        inputPassword.fill("autoQAedu@2024");
        //Click Login Button
        Locator loginButton = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        //Check if we were redirected to the home
        Locator homeHeaderPage = page.locator(".header-title-content");
        //assertThat(homeHeaderPage).isVisible();
        //assertThat(homeHeaderPage).hasText("Home Test Task");
        assertAll("Login Page Checks",
                () ->assertThat(homeHeaderPage).hasText("Home Test Task"),
                () ->assertThat(homeHeaderPage).isVisible());
        //Close everything
        page.close();
        browser.close();
        playwright.close();
    }
}