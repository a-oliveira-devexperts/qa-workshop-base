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

    private Locator getInputPassword(){
        return page.locator("#password");
    }

    private Locator getLoginButton(){
        return page.getByRole(AriaRole.BUTTON,  new Page.GetByRoleOptions().setName("Login"));
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
