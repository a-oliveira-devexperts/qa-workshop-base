package com.devexperts.in.qatesting.internship;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    @Test
    public void testLoginSuccess(){
        //Setup
        //Create Playwright
        Playwright playwright = Playwright.create();
        //Create Browser
        Browser browser = playwright.chromium().launch();
        //Create Page
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");

        //Test Cases
        //set Username
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("a.oliveira@devexperts.com");
        //set password
        //Locator inputPassword = page.getByPlaceholder("Password" , new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Andre123456@");
        //click in Login Button
        Locator loginButton = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        //Check if the login button redirected to the home page.

        Locator homePageHeader = page.locator(".header-title-content");
        Locator tradePanelHeader = page.locator("#trade-title");

        //hard assertion
        //assertThat(homePageHeader).hasText("Home Test FFF Task");
        //assertThat(tradePanelHeader).hasText("TrFFFFade");
        //soft assertion
        assertAll("Login Assertion" ,
                () -> assertThat(homePageHeader).hasText("Home Test Task"),
                () -> assertThat(tradePanelHeader).hasText("Trade"));

        //close everything
        page.close();
        browser.close();
        playwright.close();

    }
}
