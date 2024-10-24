package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginPage {

    Page page;
    private static final String USERNAME_SELECTOR = "Username";
    private static final String PASSWORD_SELECTOR = "#password";
    private static final String LOGIN_SELECTOR = "Login";
    private static final String LOGIN_STATUS = "#login-status";

    public LoginPage(Page page){
        this.page = page;
    }

    private Locator getInputUsername(){
        return page.getByPlaceholder(USERNAME_SELECTOR);
    }

    public void informUsername(String username){
        getInputUsername().fill(username);
    }

    private Locator getInputPassword(){
        return page.locator(PASSWORD_SELECTOR);
    }

    public void informPassword(String password){
        getInputPassword().fill(password);
    }

    private Locator getButtonLogin(){
        return page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName(LOGIN_SELECTOR));
    }
    public void clickLogin(){
        getButtonLogin().click();
    }

    // Login Status Checks (aka error messages)

    private Locator getLoginStatus(){
        return page.locator(LOGIN_STATUS);
    }

    public void assertInvalidCredentialsStatus(){
        assertAll("Invalid credentials message check",
                () ->assertThat(getLoginStatus()).hasText("Please enter valid credentials:"),
                () ->assertThat(getLoginStatus()).isVisible());
    }

    public void assertWrongUserStatus(String username){
        assertAll("Wrong user message check",
                () ->assertThat(getLoginStatus()).hasText("Wrong user! User " + username + " not found."),
                () ->assertThat(getLoginStatus()).isVisible());
    }

    public void assertWrongPasswordStatus(String password){
        assertAll("Wrong password message check",
                () ->assertThat(getLoginStatus()).hasText("Wrong password! Correct password is: " + password),
                () ->assertThat(getLoginStatus()).isVisible());
    }
}
