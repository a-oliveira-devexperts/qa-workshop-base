package com.devexperts.in.qatesting.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {

    private static final String USERNAME_SELECTOR = "Username";
    private static final String PASSWORD_SELECTOR = "#password";
    private static final String LOGIN_SELECTOR = "Login";
    private static final String LOGIN_STATUS = "#login-status";

    private final Page page;

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

    public void login(String username, String password){
        informUsername(username);
        informPassword(password);
        clickLogin();
    }

    public Locator getLoginStatus(){
        return page.locator(LOGIN_STATUS);
    }
}