package com.devexperts.in.qatesting.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    Page page;
    private static final String HOME_PAGE_TITLE = ".header-title-content";
    private static final String BALANCE_SELECTOR = "balanceInfoNumber";
    public HomePage(Page page){
        this.page = page;
    }

    //Method for locating the Title
    private Locator getHomePageTitle(){
        return page.locator(HOME_PAGE_TITLE);
    }

    //Method for locating the balance (BCC balance)
    public Locator getBalanceOnHomePage(){
        return page.locator(BALANCE_SELECTOR);
    }

}
