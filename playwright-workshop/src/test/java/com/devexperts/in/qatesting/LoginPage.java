package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    Page page;
    private static final String USERNAME_FIELD_SELECTOR = "Username";
    private static final String PASSWORD_FIELD_SELECTOR = "#password";
    private static final String LOGIN_BUTTON_SELECTOR = "Login";
    public LoginPage(Page page){
        this.page = page;
    }

    //Method for locating the Username field
    private Locator getInputUsername(){
        return page.getByPlaceholder(USERNAME_FIELD_SELECTOR);
    }

    //Method for locating the Password field
    private Locator getInputPassword(){
        return page.locator(PASSWORD_FIELD_SELECTOR);
    }

    //Method for locating the Login button
    private Locator getLoginButton(){
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LOGIN_BUTTON_SELECTOR));
    }

    //Method for entering the Username
    public void provideUsername(String username){
        getInputUsername().fill(username);
    }

    //Method for entering the Password
    public void providePassword(String password){
        getInputPassword().fill(password);
    }

    //Method for clicking the Login button
    public void clickLogin(){
        getLoginButton().click();
    }

}
