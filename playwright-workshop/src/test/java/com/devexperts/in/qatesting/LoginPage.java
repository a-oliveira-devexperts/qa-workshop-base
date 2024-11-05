package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    Page page;
    private static final String USERNAME_SELECTOR = "Username";
    private static final String PASSWORD_SELECTOR = "#password";
    private static final String LOGIN_SELECTOR = "Login";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void informUsername(String username){
        Locator inputUsername = page.getByPlaceholder(USERNAME_SELECTOR);
        inputUsername.fill(username);
    }

    public void informPassword(String password){
        Locator inputPassword = page.locator(PASSWORD_SELECTOR);
        inputPassword.fill(password);
    }

    public void clickLogin(){
        Locator buttonLogin = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName(LOGIN_SELECTOR));
        buttonLogin.click();
    }

}
