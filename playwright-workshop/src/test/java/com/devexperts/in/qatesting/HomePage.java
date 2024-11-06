package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    static Page page;
    private static final String HOME_PAGE_HEADER_SELECTOR = ".header-title-content";
    private static final String BALANCE_INFO_NUMBER = "#balanceInfoNumber";
    private static final String LOGOUT_BUTTON_SELECTOR = "#logout-button";

    public HomePage(Page page){
        this.page = page;
    }

    public Locator getHomePageHeader(){
        return page.locator(HOME_PAGE_HEADER_SELECTOR);
    }

    public Locator getBalance(){
        return page.locator(BALANCE_INFO_NUMBER);
    }

    private Locator getLogoutButton(){
        return page.locator(LOGOUT_BUTTON_SELECTOR);
    }

    public void clickLogout(){
        getLogoutButton().click();
    }
}
