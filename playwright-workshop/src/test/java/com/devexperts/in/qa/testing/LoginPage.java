package com.devexperts.in.qa.testing;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage
  {
      private Page page;
      private static final String USERNAME_SELECTOR="#name";
      private static final String PASSWORD_SELECTOR="#password";
      private static final String LOGIN_SELECTOR="Login";
      private static final String HomeHeader_SELECTOR=".header-title-content";
      private static final String Balance_SELECTOR=".balanceNumber";
      private static final String LoginStatus_SELECTOR="#login-status";

      public LoginPage(Page page)
        {
           this.page=page;
        }

      private Locator getInputUsername()
        {
           return page.locator(USERNAME_SELECTOR);
        }

      public void informUsername(String username)
        {
           getInputUsername().fill(username);
        }


      private Locator getInputPassword()
        {
            return page.locator(PASSWORD_SELECTOR);
        }

      public void informPassword(String password)
        {
           getInputPassword().fill(password);
        }

      private Locator getButtonLogin()
        {
          return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LOGIN_SELECTOR));
        }

      public Locator getHomeHeader()
        {
            return this.page.locator(HomeHeader_SELECTOR);
        }

      public Locator getBalance()
        {
            return this.page.locator(Balance_SELECTOR);
        }

      public Locator getLoginStatus()
        {
            return this.page.locator(LoginStatus_SELECTOR);
        }

      public void clickLogin()
        {
            getButtonLogin().click();
        }
  }         