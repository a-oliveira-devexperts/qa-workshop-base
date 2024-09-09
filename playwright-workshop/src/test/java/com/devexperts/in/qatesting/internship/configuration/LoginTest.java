package com.devexperts.in.qatesting.internship.configuration;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest extends BasicTest {

    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.enterUserName("epashnevab@devexperts.com");
        loginPage.enterPassword("!Q@W3e4r");
        loginPage.clickLogin();

        HomePage homePage = new HomePage(page);
        assertAll("Login Assertion",
                () -> assertThat(homePage.getHeaderPage()).hasText("Home Test Task"),
                () -> assertThat(homePage.getHeaderPanelTitle()).hasText("Trade"));
    }
}
