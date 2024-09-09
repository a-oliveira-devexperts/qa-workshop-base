package com.devexperts.in.qatesting.internship.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    public LoginPage (Page page){
        this.page = page;
    }

    public void enterUsername(String username){
        Locator inputPassword = page.getByPlaceholder("Username");
        inputPassword.fill(username);
    }

    public void enterPassword(String password){
        Locator inputPassword = page.locator("#password");
        inputPassword.fill(password);
    }

    public void clickLogin(){
        Locator buttonLogin = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        buttonLogin.click();
    }

    public void login(String username , String password){
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

}
