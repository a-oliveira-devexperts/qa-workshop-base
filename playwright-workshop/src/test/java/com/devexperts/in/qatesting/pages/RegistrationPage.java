package com.devexperts.in.qatesting.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.UUID;

public class RegistrationPage {
    Page page;
    private static final String DESIRED_NAME_SELECTOR = "#desiredName";
    private static final String DESIRED_PASSWORD_SELECTOR = "#desiredPassword";
    private static final String REGISTER_BUTTON_SELECTOR = "#registration-button";
    private static final String DESIRED_BALANCE_SELECTOR = "#desiredDeposit";
    private static final String LOGIN_BUTTON_SELECTOR = "#login-button";


    public RegistrationPage(Page page) {
        this.page = page;
    }

    //Method for locating the New username field
    private Locator getNewUsernameField() {
        return page.locator(DESIRED_NAME_SELECTOR);
    }

    //Method for locating the New password field
    private Locator getNewPasswordField() {
        return page.locator(DESIRED_PASSWORD_SELECTOR);
    }


    //Method for locating the desired balance field
    public Locator getDesiredBalanceField() {
        return page.locator(DESIRED_BALANCE_SELECTOR);
    }


    //Method for locating the Register button
    private Locator getRegisterButton() {
        return page.locator(REGISTER_BUTTON_SELECTOR);
    }

    //Method to locate the Login button after registration
    public Locator getLoginButtonAfterRegistration() {
        Locator locator = page.locator(LOGIN_BUTTON_SELECTOR);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return locator;
    }



    //Method to provide the new user's username
    public void provideDesiredUsername(String desiredUsername) {
        getNewUsernameField().fill(desiredUsername);
    }

    //Method for entering the Password
    public void provideDesiredPassword(String desiredPassword) {
        getNewPasswordField().fill(desiredPassword);
    }

    //Method to choose the deposit
    public void selectDeposit() {
        getDesiredBalanceField().click(new Locator.ClickOptions().setForce(true));
        getDesiredBalanceField().selectOption("1.000 BCC");
        page.keyboard().press("Enter");
    }

    //Method for clicking the Register button
    public void clickRegister() {
        getRegisterButton().click();
    }

    //Method to click the Login button that appears after registering
    public void loginAfterRegistration() {
        getLoginButtonAfterRegistration().click();
    }

    //Method to generate a new email address for each time a new user is being registered (each test run)
    public class EmailGenerator {

        public static String generateNewEmail() {
            String uniquePrefix = UUID.randomUUID().toString().substring(0, 8);
            return uniquePrefix + "@example.com";
        }

        public static void main(String[] args) {
            System.out.println(generateNewEmail());
        }
    }


}
