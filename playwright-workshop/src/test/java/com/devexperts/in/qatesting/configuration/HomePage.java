package com.devexperts.in.qatesting.configuration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HomePage {
    Page page;
    private static final String HOMEPAGE_TITLE_SELECTOR = ".header-title-content";
    private static final String BALANCE_INFO_NUMBER = "#balanceInfoNumber";
    private static final String LOGOUT_BUTTON_SELECTOR = "#logout-button";
    public HomePage(Page page){
        this.page = page;
    }

    private Locator getHeaderHomepage(){
        return page.locator(HOMEPAGE_TITLE_SELECTOR);
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

    public void assertHeaderHomepage(String pageHeader){
        assertAll("Home Page Checks",
                () ->assertThat(getHeaderHomepage()).hasText("Home Test Task"),
                () ->assertThat(getHeaderHomepage()).hasText(pageHeader),
                () ->assertThat(getHeaderHomepage()).isVisible());
    }
}
