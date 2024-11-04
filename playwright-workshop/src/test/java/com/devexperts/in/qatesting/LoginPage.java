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

    public LoginPage(Page page){
        this.page = page;
    }

    private Locator getInputUsername(){
        return page.getByPlaceholder(USERNAME_SELECTOR);
    }

    private Locator getInputPassword(){
        return page.locator(PASSWORD_SELECTOR);
    }

    private Locator getButtonLogin() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LOGIN_SELECTOR));
    }

    private Locator getHomeHeader() {
        return page.locator(".header-title-content");
    }

    private Locator getLoginStatus() {
        return page.locator ("#login-status");
    }

    private Locator getBalanceInfoNumber() {
        return page.locator ("#balanceInfoNumber");
    }

    public void informUsername(String username) {
        getInputUsername().fill(username);
    }

    public void informPassword(String password) {
        getInputPassword().fill(password);
    }

    public void clickLogin() {
        getButtonLogin().click();
    }

    public void homePageChecks() {
        assertAll("Login Page Checks",
                ()-> assertThat(getHomeHeader()).hasText("Home Test Task"),
                ()-> assertThat(getHomeHeader()).isVisible());
    }

    public void loginStatus() {
        assertAll("Login Status Checks",
                ()-> assertThat(getLoginStatus()).containsText("Wrong user"),
                ()-> assertThat(getLoginStatus()).isVisible());
    }

    public void balanceNumber() {
        assertAll("Balance Number Checks",
                ()-> assertThat(getBalanceInfoNumber()).hasText("10000.00"),
                ()-> assertThat(getBalanceInfoNumber()).isVisible());
    }
}

