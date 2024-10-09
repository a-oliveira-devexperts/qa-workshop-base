package come.devexperts.in.qatesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testSuccessfulLogin() {
        // Setup
        // Create Playwrite, Browser, Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        // Open Login Page
        page.navigate("https://qa-testing.in.devexperts.com/internship/");
        // Inform username
        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("kazaryan@devexperts.com");
        // Inform password
//        Locator inputPassword = page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("GB#gdAcgbaZ5r");
        // Click Login button
        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        loginButton.click();
        // Check if were redirected to the home
        Locator homeHeaderPage = page.locator(".header-title-content");
        //assertThat(homeHeaderPage).hasText("Home Testdd Task");
        //assertThat(homeHeaderPage).isVisible();
        assertAll("Login Page Checks",
                () -> assertThat(homeHeaderPage).hasText("Home Test Task"),
                () -> assertThat(homeHeaderPage).isVisible()
                );

        //Close everything
        page.close();
        browser.close();
        playwright.close();
    }
}

