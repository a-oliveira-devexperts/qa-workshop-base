package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {

    Page page;

    public LoginPage(Page page){
        this.page = page;
    }

    private Locator getInputUsername(){
        return page.getByPlaceholder("Username");
    }

    public void informUsername(String username){
        getInputUsername().fill(username);
    }

    private Locator getInputPassword(){
        return page.locator("#password");
    }

    public void informPassword(String password){
        getInputPassword().fill(password);
    }

    private Locator getButtonLogin(){
        return page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
    }
    public void clickLogin(){
        getButtonLogin().click();
    }

}
