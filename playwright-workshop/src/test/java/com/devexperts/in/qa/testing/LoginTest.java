package com.devexperts.in.qa.testing;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest
  {
      @Test
     public void testSuccessfullLogin()
       {
            //Setup Playwright, Browser, Page
           Playwright playwright = Playwright.create();
           Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
           Page page = browser.newPage();
           page.navigate("https://qa-testing.in.devexperts.com/internship/");
           //Inform username
           Locator inputUsername = page.locator("#name");
           inputUsername.fill("bfraga@devexperts.com");
           //Inform password
           Locator inputPassword=page.locator("#password");
           inputPassword.fill("B-fraga*");
           //Click in Login button
           Locator buttonLogin = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
           buttonLogin.click();
           //Check if we were redirected to hte homepage
           Locator homeHeader = page.locator(".header-title-content");
           //assertThat(homeHeader).isVisible();
           //assertThat(homeHeader).hasText("Home Test Task");
           assertAll("Login Checks",
                   ()-> assertThat(homeHeader).hasText("Home Test Task"),
                   ()-> assertThat(homeHeader).isVisible()
                    );

           //Close Page, Browser and Playwright
           page.close();
           browser.close();
           playwright.close();
       }
  }

  //Workshop session script
  /* @Test
    public void testSuccessfulLogin(){
        //Setup Playwright, Browser , Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");

        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("a-oliveira-group-a@devexperts.com");
        //Locator inputPassword = page.getByPlaceholder("Password" , new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Workshop123@");

        Locator buttonLogin = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        buttonLogin.click();

        Locator homeHeader = page.locator(".header-title-content");

        assertAll("Login Checks",
                ()-> assertThat(homeHeader).hasText("Home Test Task", new LocatorAssertions.HasTextOptions().setIgnoreCase(false)),
                ()-> assertThat(homeHeader).isVisible());

        page.close();
        browser.close();
        playwright.close();
*/