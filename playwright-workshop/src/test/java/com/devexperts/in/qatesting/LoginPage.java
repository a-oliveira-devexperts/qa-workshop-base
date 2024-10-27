package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    static Page page;
    private static final String USERNAME_SELECTOR = "Username";
    private static final String PASSWORD_SELECTOR = "#password";
    private static final String LOGIN_BUTTON_SELECTOR = "Login";
    private static final String LOGIN_STATUS_SELECTOR = "#login-status";

    public LoginPage(Page page){
        this.page = page;
    }

    private Locator getInputUsername(){
        return page.getByPlaceholder(USERNAME_SELECTOR);
    }

    private Locator getInputPassword(){
        return page.locator(PASSWORD_SELECTOR);
    }

    private Locator getLoginButton(){
        return page.getByRole(AriaRole.BUTTON,  new Page.GetByRoleOptions().setName(LOGIN_BUTTON_SELECTOR));
    }

    public static Locator getLoginStatus(){
        return page.locator(LOGIN_STATUS_SELECTOR);
    }

    public void informUsername(String username){
        getInputUsername().fill(username);
    }

    public void informPassword(String password){
        getInputPassword().fill(password);
    }

    public void clickLogin(){
        getLoginButton().click();
    }
}
