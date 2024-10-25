package com.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
    }

    @Test
    public void testSuccessfulLogin() {
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("iamritaferraz@gmail.com");
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Internet1!");
        Locator buttonLogin = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")); //find elements by their role on the page. find a button that is named logjn
        buttonLogin.click();
        Locator homeHeader = page.locator(".header-title-content");
        assertAll("Login Checks",
                ()-> assertThat(homeHeader).hasText("Home Test Task"),
                ()-> assertThat(homeHeader).isVisible());
    }

    @Test
    public void testLoginWithWrongCredentials(){

    }
    @AfterEach
    public void tearDown(){
        page.close();
        browser.close();
    }
    @AfterAll
    public static void afterAll(){
        playwright.close();
    }
}

