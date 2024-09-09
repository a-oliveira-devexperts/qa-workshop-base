package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class ExampleTest {
    //
    //
    @Test
    public void example(){

    }

    @Test
    void testFirst() {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch();
             Page page = browser.newPage()) {
            page.navigate("https://qa-testing.in.devexperts.com/internship/");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
            System.out.println("ECHO: " + page.title());
        }
    }
}
