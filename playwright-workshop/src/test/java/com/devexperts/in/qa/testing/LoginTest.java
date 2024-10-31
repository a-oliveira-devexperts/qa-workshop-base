package com.devexperts.in.qa.testing;

import com.devexperts.in.qa.testing.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginTest
  {
      private static Playwright playwright;
      private Browser browser;
      private Page page;
      private static final String USERNAME_DATA="bfraga@devexperts.com";
      private static final String PASSWORD_DATA="B-fraga*";

      @BeforeAll
      //Method to set up Playwright
      public static void beforeAll()
        {
           playwright=Playwright.create();
        }

      @BeforeEach
      //Method to set up Browser and Page
      public void setUp()
        {
           browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
           page = browser.newPage();
           page.navigate(PropertiesProvider.getProperty("base.url"));
        }

      @AfterEach
      //Method to close Page and Browser
      public void tearDown()
        {
           page.close();
           browser.close();
        }

      @AfterAll
      //Method to close Playwright
      public static void afterAll()
        {
           playwright.close();
        }

      @Test
      public void testSuccessfulLogin()
        {
           LoginPage loginPage=new LoginPage(page);

           loginPage.informUsername(USERNAME_DATA);
           loginPage.informPassword(PASSWORD_DATA);
           loginPage.clickLogin();

           //Check if we were redirected to the homepage
           Locator homeHeader = page.locator(".header-title-content");
           //assertThat(homeHeader).isVisible();
           //assertThat(homeHeader).hasText("Home Test Task");
           assertAll("Login Checks",
                   ()-> assertThat(homeHeader).hasText("Home Test Task"),
                   ()-> assertThat(homeHeader).isVisible()
                    );
        }

      @Test
      public void testLoginWithWrongCredentials()
         {
             LoginPage loginPage=new LoginPage(page);


             //Test with empty both fields
             loginPage.clickLogin();

             Locator loginStatus= page.locator("#login-status");

             assertThat(loginStatus).hasText("Please enter valid credentials:");

             //Test with empty password
             loginPage.informUsername("X");
             loginPage.clickLogin();

             assertThat(loginStatus).hasText("Please enter valid credentials:");

             //Test with empty username
             loginPage.informPassword("X");
             loginPage.clickLogin();

             assertThat(loginStatus).hasText("Please enter valid credentials:");

             //Test with wrong username and password
             loginPage.informUsername("X");
             loginPage.informPassword("X");
             loginPage.clickLogin();


             assertThat(loginStatus).hasText("Wrong user! User X not found.");

             //Test with wrong password
             loginPage.informUsername(USERNAME_DATA);
             loginPage.informPassword("X");
             loginPage.clickLogin();

             assertThat(loginStatus).hasText("Wrong password! Correct password is: B-fraga*");

             //Test with wrong username
             loginPage.informUsername("X");
             loginPage.informPassword(PASSWORD_DATA);
             loginPage.clickLogin();

             assertThat(loginStatus).hasText("Wrong user! User X not found.");
         }

      @Test
      public void checkBalance()
        {
            LoginPage loginPage=new LoginPage(page);

            loginPage.informUsername(USERNAME_DATA);
            loginPage.informPassword(PASSWORD_DATA);
            loginPage.clickLogin();

            Locator balance= page.locator(".balanceNumber");

            assertThat(balance).hasText("10000.00");
        }
  }

  //Workshop session script
  /* @Test
    public void testSuccessfulLogin(){
        //Setup Playwright, Browser , Page
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://qa-testing.in.devexperts.com/internship/");

        Locator inputUsername = page.getByPlaceholder("Username");
        inputUsername.fill("a-oliveira-group-a@devexperts.com");
        //Locator inputPassword = page.getByPlaceholder("Password" , new Page.GetByPlaceholderOptions().setExact(true));
        Locator inputPassword = page.locator("#password");
        inputPassword.fill("Workshop123@");

        Locator buttonLogin = page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Login"));
        buttonLogin.click();

        Locator homeHeader = page.locator(".header-title-content");

        assertAll("Login Checks",
                ()-> assertThat(homeHeader).hasText("Home Test Task", new LocatorAssertions.HasTextOptions().setIgnoreCase(false)),
                ()-> assertThat(homeHeader).isVisible());

        page.close();
        browser.close();
        playwright.close();
*/