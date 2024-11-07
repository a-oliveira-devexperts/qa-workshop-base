package com.devexperts.in.qatesting.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

    private static final String HOMEPAGE_TITLE_SELECTOR = ".header-title-content";
    private static final String BALANCE_SELECTOR = "#balanceInfoNumber";
    private static final String TOTAL_FUNDS_SELECTOR = "#balanceTotalNumber";

    private final Page page;

    public HomePage(Page page){
        this.page = page;
    }

    public Locator getHeaderHomepage(){
        return page.locator(HOMEPAGE_TITLE_SELECTOR);
    }

    // Balance Section

    public Locator getBalance(){
        return page.locator(BALANCE_SELECTOR);
    }

    public Locator getTotalFunds(){
        return page.locator(TOTAL_FUNDS_SELECTOR);
    }
}