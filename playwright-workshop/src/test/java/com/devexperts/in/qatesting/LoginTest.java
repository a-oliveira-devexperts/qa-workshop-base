package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testSuccessfulLogin(){
        //Setup
        //Create Playwright, Browser, Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        //Open Login Page
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
        //Inform Username
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("kmarkov@devexperts.com");
        //Inform Password
        //Locator inputPassword = page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Aa12345678!");
        //Click Login Button
        Locator loginButton = page.getByRole( AriaRole.BUTTON,  new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        //Check if we were redirected to the home page
        Locator homeHeaderPage = page.locator(".header-title-content");

        //Type1
            //assertThat(homeHeaderPage).hasText("Home TestD Task");
            //assertThat(homeHeaderPage).isVisible();

        //Type2
            //assertAll("Login Page Checks",
            //        () -> assertEquals("Home Test Task", homeHeaderPage.innerText()),
            //        () -> assertTrue(homeHeaderPage.isVisible()));

        //Type3
            assertAll("Login Page Checks",
                    () -> assertThat(homeHeaderPage).hasText("Home Test Task"),
                    () -> assertThat(homeHeaderPage).isVisible());

        //Close everything
        page.close();
        browser.close();
        playwright.close();
    }

}
