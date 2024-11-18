package com.devexperts.in.qa.testing;

import com.devexperts.in.qa.testing.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;


public class LoginTest
  {
      private static Playwright playwright;
      private Browser browser;
      private Page page;


      @BeforeAll
      public static void beforeAll()
        {
           playwright=Playwright.create();
        }

      @BeforeEach
      public void setUp()
        {
           browser = playwright.chromium().launch();
           page = browser.newPage();
           page.navigate(PropertiesProvider.getProperty("base.url"));
        }

      @AfterEach
      public void tearDown()
        {
           page.close();
           browser.close();
        }

      @AfterAll
      public static void afterAll()
        {
           playwright.close();
        }

      public LoginPage Login()
        {
            LoginPage loginPage=new LoginPage(page);
            loginPage.informUsername(PropertiesProvider.getProperty("test.user"));
            loginPage.informPassword(PropertiesProvider.getProperty("test.password"));
            loginPage.clickLogin();
            return loginPage;
        }

      public LoginPage Login(String username, String password)
        {
            LoginPage loginPage=new LoginPage(page);
            loginPage.informUsername(username);
            loginPage.informPassword(password);
            loginPage.clickLogin();
            return loginPage;
        }

      @Test
      public void testSuccessfulLogin()
        {
            PlaywrightAssertions.assertThat(Login().getHomeHeader()).hasText("Home Test Task");
        }

      @Test
      public void testLoginWithWrongCredentials()
         {

             String username="";
             String password="";

             PlaywrightAssertions.assertThat(Login(username,password).getLoginStatus()).hasText("Please enter valid credentials:");

             username="X";
             password="";

             PlaywrightAssertions.assertThat(Login(username, password).getLoginStatus()).hasText("Please enter valid credentials:");

             username="";
             password="X";

             PlaywrightAssertions.assertThat(Login(username, password).getLoginStatus()).hasText("Please enter valid credentials:");

             username="X";
             password="X";

             PlaywrightAssertions.assertThat(Login(username, password).getLoginStatus()).hasText("Wrong user! User "+username+ " not found.");

             username="bfraga@devexperts.com";
             password="X";

             PlaywrightAssertions.assertThat(Login(username, password).getLoginStatus()).hasText("Wrong password! Correct password is: "+PropertiesProvider.getProperty("test.password"));

             username="X";
             password="B-fraga*";

             PlaywrightAssertions.assertThat(Login(username, password).getLoginStatus()).hasText("Wrong user! User "+username+ " not found.");
         }

  }
