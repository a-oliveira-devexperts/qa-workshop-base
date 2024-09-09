package com.devexperts.in.qatesting.internship.configuration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    Page page;
    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUserName(String username) {
        Locator inputUserName = page.getByPlaceholder("Username");
        inputUserName.fill(username);
    }

    public void enterPassword(String password) {
        Locator inputPassword = page.locator("#password");
        inputPassword.fill(password);
    }

    public void clickLogin() {
        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
    }
}
