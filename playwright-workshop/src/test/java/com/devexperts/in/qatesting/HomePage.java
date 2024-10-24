package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HomePage {

    Page page;
    private static final String HOMEPAGE_TITLE_SELECTOR = ".header-title-content";

    public HomePage(Page page){
        this.page = page;
    }

    private Locator getHeaderHomepage(){
        return page.locator(HOMEPAGE_TITLE_SELECTOR);
    }

    public void assertHeaderHomepage(String pageHeader){
        assertAll("Home Page Checks",
                () ->assertThat(getHeaderHomepage()).hasText(pageHeader),
                () ->assertThat(getHeaderHomepage()).isVisible());
    }
}
