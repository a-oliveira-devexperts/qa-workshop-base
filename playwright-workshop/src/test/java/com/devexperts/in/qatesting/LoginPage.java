package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.time.Instant;

public class LoginPage {
    Page page;
    private static final String USERNAME_SELECTOR = "Username";
    private static final String PASSWORD_SELECTOR = "#password";
    private static final String LOGIN_SELECTOR = "Login";
    private static final String REGISTRATION_SELECTOR = "Need an account?";
    private static final String EMAIL_SELECTOR = "E-mail (required)";
    private static final String NEW_PASSWORD_SELECTOR = "Password (required)";
    private static final String DEPOSIT_SELECTOR = "#desiredDeposit";
    private static final String REGISTER_SELECTOR = "#registration-button";

    public LoginPage(Page page){
        this.page = page;
    }

    private Locator getInputUsername(){
        return page.getByPlaceholder(USERNAME_SELECTOR);
    }

    private Locator getInputPassword(){
        return page.locator(PASSWORD_SELECTOR);
    }

    private Locator getButtonLogin(){
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LOGIN_SELECTOR));
    }

    public void informUsername(String username){
        getInputUsername().fill(username);
    }

    public void informPassword(String password){
        getInputPassword().fill(password);
    }

    private Locator getRegisterLink(){
        return page.getByText(REGISTRATION_SELECTOR);
    }
    private Locator getInputEmail(){
        return page.getByPlaceholder(EMAIL_SELECTOR);
    }
    private Locator getInputPasswordNew(){
        return page.getByPlaceholder(NEW_PASSWORD_SELECTOR);
    }
    private Locator getInputInitialDeposit(){
        return page.locator(DEPOSIT_SELECTOR);
    }
    private Locator getRegisterButton(){
        return page.locator(REGISTER_SELECTOR);
    }
    public void informEmail(String email){
        getInputEmail().fill(email);
    }
    public void informDeposit(String depositOption)
    {
        getInputInitialDeposit().selectOption(depositOption);
    }

    public void informPasswordNew(String password)
    {
        getInputPasswordNew().fill(password);
    }


    public void toggleRegistration(){ getRegisterLink().click();}
    public void clickRegister(){ getRegisterButton().click();}
    public void clickLogin(){
        getButtonLogin().click();
    }

    public void Login(){
        informUsername(PropertiesProvider.getProperty("test.user"));
        informPassword(PropertiesProvider.getProperty("test.password"));
        clickLogin();
    }

    public String CreateNewUserAndLogin(String depositOption) {
        toggleRegistration();
        String email = PropertiesProvider.getProperty("new_users.prefix") + Instant.now().toEpochMilli() +
            "@" + PropertiesProvider.getProperty("new_users.domain");
        informEmail(email);
        informPasswordNew(PropertiesProvider.getProperty("new_users.password"));
        informDeposit(depositOption);
        clickRegister();
        clickLogin();
        return email;
    }

    public String CreateNewUserAndLogin() {
        return CreateNewUserAndLogin("1.000 BCC");
    }
}
