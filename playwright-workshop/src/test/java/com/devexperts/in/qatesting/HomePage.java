package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    Page page;
    public HomePage(Page page){
        this.page = page;
    }

    private static final String BALANCE_SELECTOR = "#balanceInfoNumber";

    public Locator getBalanceLocator(){
        return page.locator(BALANCE_SELECTOR);
    }

    public String getBalance() {
         return page.locator(BALANCE_SELECTOR).innerText();
    }
}
