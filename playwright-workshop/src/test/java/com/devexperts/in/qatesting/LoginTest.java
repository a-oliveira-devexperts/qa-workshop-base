package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {

    @Test
    public void testSuccessfulLogin(){
        //Setup Playwright, Browser, Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
        //Inform username
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("edermendzhieva@devexperts.com");
        //Inform password
        //Locator inputPassword = page.getByPlaceholder("Password" , new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("E42ac45D@");
        //Click in Login Button
        Locator buttonLogin = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        buttonLogin.click();
        //Check if we were redirected to the homepage
        Locator homeHeader = page.locator(".header-title-content");
        //assertThat(homeHeader).hasText("Home Test sdagda Task");
        //assertThat(homeHeader).isVisible();
        assertAll("Login Checks",
                ()-> assertThat(homeHeader).hasText("Home Test Task"),
                ()-> assertThat(homeHeader).isVisible());

        //Close Page, Browser, Playwright
        page.close();
        browser.close();
        playwright.close();
    }
}
