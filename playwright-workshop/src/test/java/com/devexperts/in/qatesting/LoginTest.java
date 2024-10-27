package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
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
        page.navigate(PropertiesProvider.getProperty("base.url"));
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill(PropertiesProvider.getProperty("test.user"));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill(PropertiesProvider.getProperty("test.password"));
        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks",
            () -> assertThat(homeHeaderPage).hasText("Home Test Task"),
            () -> assertThat(homeHeaderPage).isVisible());
        page.close();
        browser.close();
        playwright.close();
    }
}
