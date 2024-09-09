package com.devexperts.in.qatesting.internship.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage{

    public HomePage(Page page){
        this.page = page;
    }

    public Locator getHeaderPage(){
        return page.locator(".header-title-content");
    }

    public Locator getTraderPanelTitle(){
        return page.locator("#trade-title");
    }


}
