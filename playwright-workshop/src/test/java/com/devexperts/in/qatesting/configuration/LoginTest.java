package com.devexperts.in.qatesting.configuration;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    @Test
    public void testSuccessfulLogin(){

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("t@t.com");
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Test1234!");
        Locator loginButton = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks" ,
                () ->assertThat(homeHeaderPage).hasText("Home Test Task"),
                () ->assertThat(homeHeaderPage).isVisible());

        page.close();
        browser.close();
        playwright.close();
    }

    @Test
    public void testUnsuccessfulLoginWithBlankUsername(){
        //setup: Create playwrightinstance, browser, page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        //open login page
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
        //inform th username, blank
        // pass
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Test1234!");
        //click login button
        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        //  check if we were not redirected to the homepage
        //login-status
        Locator errorText = page.locator("#login-status");
        assertThat(errorText).hasText("Please enter valid credentials:");
        // close everything
        page.close();
        browser.close();
        playwright.close();
    }
}
