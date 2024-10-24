package com.devexperts.in.qatesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HomePage {

    Page page;

    public HomePage(Page page){
        this.page = page;
    }

    private Locator getHeaderHomepage(){
        return page.locator(".header-title-content");
    }

    public void assertHeaderHomepage(String pageHeader){
        assertAll("Home Page Checks",
                () ->assertThat(getHeaderHomepage()).hasText("Home Test Task"),
                () ->assertThat(getHeaderHomepage()).isVisible());
    }
}
