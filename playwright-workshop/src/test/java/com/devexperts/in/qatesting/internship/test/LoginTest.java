package com.devexperts.in.qatesting.internship.test;

import com.devexperts.in.qatesting.internship.page.HomePage;
import com.devexperts.in.qatesting.internship.page.LoginPage;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    private static final String USERNAME = "a.oliveira@devexperts.com";
    private static final String PASSWORD = "Andre123456@";


    @Test
    public void testLoginSuccess(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(USERNAME , PASSWORD );

        HomePage homePage = new HomePage(page);
        assertAll("Login Assertions" ,
                () -> assertThat(homePage.getHeaderPage()).hasText("Home Test Task"),
                () -> assertThat(homePage.getTraderPanelTitle()).hasText("Trade"));
    }

    public void testLoginSuccessOld(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");

        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("a.oliveira@devexperts.com");

        //Locator inputPassword = page.getByPlaceholder("Password");
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Andre123456@");

        Locator buttonLogin = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        buttonLogin.click();

        Locator headerHomePage = page.locator(".header-title-content");
        Locator titleTraderPanel = page.locator("#trade-title");

        //hard assertion
        assertThat(headerHomePage).hasText("Home Test Task");
        assertThat(titleTraderPanel).hasText("Trade");

        //soft assertion
        assertAll("Login Assertions" ,
                () -> assertThat(headerHomePage).hasText("Home Test Task"),
                () -> assertThat(titleTraderPanel).hasText("Trade"));
    }

    @Test
    public void testLoginWrongCredentials(){

    }

}
