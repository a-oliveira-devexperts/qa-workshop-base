package com.devexperts.in.qatesting.internship.configuration;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    @Test
    public void testLoginSuccess() {
        //Setup
        //Create Playwright
        Playwright playwright = Playwright.create();
        //Create Browser
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //Create Page
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");

        //Test Cases
        //set Username
        Locator inputUserName = page.getByPlaceholder("Username");
        inputUserName.fill("epashnevab@devexperts.com");
        //Locator inputPassword = page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        //set password
        inputPassword.fill("!Q@W3e4r");
        //Click in login Button
        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        //Check if the login button redirected to the home page
        Locator homePageHeader = page.locator(".header-title-content");
        Locator tradePanelHeader = page.locator("#trade-title");

        //hard assertion
        //assertThat(homePageHeader).hasText("Home Test fhfjTask");
        //assertThat(tradePanelHeader).hasText("Tradefgjf");
        //soft assertion
        assertAll("Login Assertion",
                () -> assertThat(homePageHeader).hasText("Home Test Task"),
                () -> assertThat(tradePanelHeader).hasText("Trade"));

        //Close everything
        page.close();
        browser.close();
        playwright.close();
    }
}
