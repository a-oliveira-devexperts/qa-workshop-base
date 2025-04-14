package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class RegistrationPage {
    Page page;
    private String selectedBalance;
    private static final String EMAIL_SELECTOR = "#desiredName";
    private static final String CREATE_PASSWORD_SELECTOR = "#desiredPassword";
    private static final String REAL_NAME_SELECTOR = "#desiredRealname";
    private static final String PHONE_SELECTOR = "#desiredPhone";
    private static final String BALANCE_SELECTOR = "#desiredDeposit";
    private static final String OPEN_FORM_BUTTON = ".registrationToggle";
    private static final String REGISTER_BUTTON = "#registration-button";

    public RegistrationPage(Page page){
        this.page = page;
    }

    private Locator getOpenFormButton() {
        return page.locator(OPEN_FORM_BUTTON, new Page.LocatorOptions().setHasText("Need an account?"));
    }

    private Locator getInputEmail(){
        return page.locator(EMAIL_SELECTOR);
    }

    private Locator getInputPassword(){
        return page.locator(CREATE_PASSWORD_SELECTOR);
    }

    private Locator getInputRealName(){
        return page.locator(REAL_NAME_SELECTOR);
    }

    private Locator getInputPhone(){
        return page.locator(PHONE_SELECTOR);
    }

    private Locator getBalanceDropdown() {
        return page.locator(BALANCE_SELECTOR);
    }

    private Locator getButtonRegister(){
        return page.locator(REGISTER_BUTTON);
    }

    public void clickOpenForm() {
        getOpenFormButton().click();
    }


    public void fillEmail(String email) {
        getInputEmail().fill(email);
    }

    public void fillPassword(String password) {
        getInputPassword().fill(password);
    }

    public void fillRealName(String realName) {
        getInputRealName().fill(realName);
    }

    public void fillPhone(String phone) {
        getInputPhone().fill(phone);
    }

    public void selectRandomBalance() {
        Map<String, String> labelToValue = new HashMap<>();
        labelToValue.put("1.000 BCC", "100000");
        labelToValue.put("5.000 BCC", "500000");
        labelToValue.put("10.000 BCC", "1000000");

        String[] balances = {"1.000 BCC", "5.000 BCC", "10.000 BCC"};
        String selectedLabel = balances[new Random().nextInt(balances.length)];

        this.selectedBalance = labelToValue.get(selectedLabel);

        Locator dropdown = getBalanceDropdown();
        dropdown.selectOption(new SelectOption().setLabel(selectedLabel));
    }

    public String getSelectedBalance() {
        return selectedBalance;
    }

    public void clickRegister() {
        getButtonRegister().click();
    }

    public void waitForPasswordFieldToBeVisible() {
        page.waitForSelector(CREATE_PASSWORD_SELECTOR, new Page.WaitForSelectorOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
    }


}