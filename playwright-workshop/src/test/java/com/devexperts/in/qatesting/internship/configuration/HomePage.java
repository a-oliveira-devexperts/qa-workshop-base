package com.devexperts.in.qatesting.internship.configuration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    Page page;
    public HomePage(Page page) {
        this.page = page;
    }

    public Locator getHeaderPage() {
        return page.locator(".header-title-content");
    }

    public Locator getHeaderPanelTitle() {
        return page.locator("#trade-title");
    }
}
