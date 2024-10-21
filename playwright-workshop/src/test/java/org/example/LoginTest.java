package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.awt.SystemColor.text;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("rcosta@devexperts.com");
        Locator inputPassowrd = page.locator("#password");
        inputPassowrd.fill("Workshop123@");
        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions(). setName("Login"));
        loginButton.click();
        Locator homeHeaderPage = page.locator(".header-title-content");
        assertAll("Login Page Checks" ,
                () ->assertThat(homeHeaderPage).hasText ("Home Test Task"),
                () ->assertThat(homeHeaderPage).isVisible());
        page.close();
        browser.close();
        playwright.close();
    }
}

