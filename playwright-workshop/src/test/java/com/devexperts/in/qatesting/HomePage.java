package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    static Page page;
    private static final String HOME_PAGE_HEADER_SELECTOR = ".header-title-content";

    public HomePage(Page page){
        this.page = page;
    }

    public static Locator getHomePageHeader(){
        return page.locator(HOME_PAGE_HEADER_SELECTOR);
    }
}