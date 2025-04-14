package com.devexperts.in.qatesting;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.*;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountCreationTest {
    private static Playwright playwright;
    private Browser browser;
    private Page page;

    private static final String CREATE_PASSWORD = "245871^^ABcvb";
    private static final String REAL_NAME = "Ana";

    private String generatedEmail;
    private String generatedPhone;

    private String generateRandomEmail() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        return "testuser_" + uuid + "@example.com";
    }

    private String generatePortuguesePhone() {
        Random random = new Random();
        int prefix = 91 + random.nextInt(2); // 91 ou 92
        int number = 1000000 + random.nextInt(9000000);
        return "+351" + prefix + number;
    }


    @BeforeAll
    public static void beforeAll(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
        generatedEmail = generateRandomEmail();
        generatedPhone = generatePortuguesePhone();
    }


    @Test
    public void testAccountCreationAndBalanceVerification(){
        RegistrationPage registrationPage = new RegistrationPage(page);
        registrationPage.clickOpenForm();
        registrationPage.fillEmail(generatedEmail);
        registrationPage.waitForPasswordFieldToBeVisible();
        registrationPage.fillPassword(CREATE_PASSWORD);
        registrationPage.fillRealName(REAL_NAME);
        registrationPage.fillPhone(generatedPhone);
        registrationPage.selectRandomBalance();

        String selectedBalance = registrationPage.getSelectedBalance();

        registrationPage.clickRegister();

        LoginPage loginPage = new LoginPage(page);
        loginPage.informUsername(generatedEmail);
        loginPage.informPassword(CREATE_PASSWORD);
        loginPage.clickLogin();

        page.waitForLoadState();

        Locator homeBalance = page.locator("#balanceInfoNumber");
        homeBalance.waitFor(new Locator.WaitForOptions()
                .setTimeout(60000)
                .setState(WaitForSelectorState.VISIBLE));

        String displayedBalance = homeBalance.textContent().trim().replace(".", "");

        assertAll("Check Balance",
                () -> assertNotNull(displayedBalance),
                () -> assertEquals(displayedBalance, selectedBalance));
    }


    @AfterEach
    public void tearDown(){
        page.close();
        browser.close();
    }

    @AfterAll
    public static void afterAll(){
        playwright.close();
    }

}
