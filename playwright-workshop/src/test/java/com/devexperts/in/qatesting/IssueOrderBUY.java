package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class IssueOrderBUY {
    @Test
public void testIssueOrderBuy(){
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
    //assertThat(homeHeader).hasText("HOME TEST TASK");
    //assertThat(homeHeader).isVisible();
    //assertAll("Login Checks",
            //()-> assertThat(homeHeader).hasText("Home Test Task"),
            //()-> assertThat(homeHeader).isVisible());
    page.click("#BUY_WOC");
    Locator plusButton = page.locator("a[onclick*='multiplyInput']");
    plusButton.click();
        try {
            Thread.sleep(3600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    page.click("#orderConfirm");
    //Locator expand = page.locator("i.material-icons.position-expand");
    //expand.click();

    try {
         Thread.sleep(360000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    //()-> assertThat(homeHeader).hasId()
    //Close Page, Browser and Playwright
    //page.close();
    //browser.close();
    //playwright.close();
}
}
