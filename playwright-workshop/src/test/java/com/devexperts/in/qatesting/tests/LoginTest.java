package com.devexperts.in.qatesting.tests;

import com.devexperts.in.qatesting.configuration.PropertiesProvider;
import com.devexperts.in.qatesting.pages.HomePage;
import com.devexperts.in.qatesting.pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest {

    private static final String USERNAME_DATA = PropertiesProvider.getProperty("test.user");
    private static final String PASSWORD_DATA = PropertiesProvider.getProperty("test.password");
    private static final String USERNAME_WRONG_DATA = PropertiesProvider.getProperty("wrong.test.user");
    private static final String PASSWORD_WRONG_DATA = PropertiesProvider.getProperty("wrong.test.password");

    private static Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeAll
    public static void createPlaywright(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp(){
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate(PropertiesProvider.getProperty("base.url"));
    }

    @Test
    public void testSuccessfulLogin(){

        LoginPage loginPage = new LoginPage(page);
        loginPage.login(USERNAME_DATA, PASSWORD_DATA);

        HomePage homePage = new HomePage(page);
        assertThat(homePage.getHeaderHomepage()).hasText("Home Test Task");
    }

    private static Stream<Arguments> userDataAndErrors() {
        return Stream.of(
                Arguments.of("", "", "Please enter valid credentials:"),
                Arguments.of(USERNAME_WRONG_DATA, PASSWORD_WRONG_DATA, "Wrong user! User " + USERNAME_WRONG_DATA + " not found."),
                Arguments.of(USERNAME_DATA, PASSWORD_WRONG_DATA, "Wrong password! Correct password is: " + PASSWORD_DATA)
        );
    }

    @ParameterizedTest
    @MethodSource("userDataAndErrors")
    void testLoginErrors(String username, String password, String error){
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(username, password);
        assertThat(loginPage.getLoginStatus()).hasText(error);
    }

    @AfterEach
    public void tearDown(){
        page.close();
        browser.close();
    }

    @AfterAll
    public static void closePlaywright(){
        playwright.close();
    }
}