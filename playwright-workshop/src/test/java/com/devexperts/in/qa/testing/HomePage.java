package com.devexperts.in.qa.testing;

import com.devexperts.in.qa.testing.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

public class HomePage
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

    @Test
    public void checkBalance()
      {
         PlaywrightAssertions.assertThat(Login().getBalance()).hasText("10000.00");
      }


}
