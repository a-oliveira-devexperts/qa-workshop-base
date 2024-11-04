package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
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
        Locator inputUsername = page.locator("#name");
        inputUsername.fill("pmitev@devexperts.com");
        //Inform password
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Peco0877249094!");
        //Click in Login button
        Locator buttonLogin = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        buttonLogin.click();
        //Check if we were redirected to the home page
        Locator homeHeader = page.locator(".header-title-content");
        assertAll("Login Checks",
                ()-> assertThat(homeHeader).hasText("Home Test Task"),
                ()-> assertThat(homeHeader).isVisible());
                //()-> assertThat(homeHeader).hasId()
        //Close Page, Browser and Playwright
        //page.close();
        //browser.close();
        //playwright.close();
    }
}
