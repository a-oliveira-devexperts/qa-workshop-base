package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    @Test
    public void testSuccessfulLogin() {
        //Setup Playwright, Browser , Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");

        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("atykhonova@devexperts.com");
        //Locator inputPassword = page.getByPlaceholder("Password" , new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Greece93!");

        Locator buttonLogin = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        buttonLogin.click();

        Locator homeHeader = page.locator(".header-title-content");

        assertAll("Login Checks",
                () -> assertThat(homeHeader).hasText("Home Test Task", new LocatorAssertions.HasTextOptions().setIgnoreCase(false)),
                () -> assertThat(homeHeader).isVisible());

        page.close();
        browser.close();
        playwright.close();
    }
}

