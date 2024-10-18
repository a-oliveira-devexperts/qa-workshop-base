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
        //Setup Playwright, Browser, Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();

        //navigate to the page we want
        page.navigate("https://qa-testing.in.devexperts.com/internship/");

        //Inform username
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("iamritaferraz@gmail.com");

        //Inform password
        //Locator inputPassword = page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)); --by exact placeholder name
        //as there are two or find per the id attribute, which is unique
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Internet1!");

        //Click Login button
        Locator buttonLogin = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")); //find elements by their role on the page. find a button that is named logjn
        buttonLogin.click();

        //Check if we are redirected to the homepage
        Locator homeHeader = page.locator(".header-title-content");
        //soft assertions, will only fail after all checks
        assertAll("Login Checks",
                ()-> assertThat(homeHeader).hasText("Home Test Task"),
                ()-> assertThat(homeHeader).isVisible());
        //Close page, browser and playwright
        page.close();
        browser.close();
        playwright.close();
    }
}

