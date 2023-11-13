package com.saucelabs.saucebindings.junit5.examples;

import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.junit5.SauceBindingsExtension;
import com.saucelabs.saucebindings.options.SauceOptions;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LocalExample {
  WebDriver driver;
  SauceSession session;

  public static SauceOptions createSauceOptions() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--hide-scrollbars");

    return SauceOptions.chrome(chromeOptions)
        .setPlatformName(SaucePlatform.MAC_CATALINA)
        .setIdleTimeout(Duration.ofSeconds(30))
        .build();
  }

  public static Boolean useSauce() {
    System.setProperty("sauce.enabled", "true");
    return Boolean.getBoolean("sauce.enabled");
  }

  @RegisterExtension
  static SauceBindingsExtension sauceExtension =
      SauceBindingsExtension.builder()
          .capabilities(createSauceOptions())
          .enabled(useSauce())
          .build();

  @RegisterExtension TestWatcher testWatcher = new LocalTestWatcher();

  @BeforeEach
  public void setup() {
    if (useSauce()) {
      session = sauceExtension.getSession();
      driver = sauceExtension.getDriver();
    } else {
      driver = new ChromeDriver();
    }
  }

  @AfterEach
  public void tearDown() {
    if (!useSauce()) {
      driver.quit();
    }
  }

  @Test
  public void localExample() {
    if (useSauce()) {
      session.annotate("Navigating to Swag Labs");
    }
    driver.get("https://www.saucedemo.com/");
  }

  public class LocalTestWatcher implements TestWatcher {
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
      ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }
  }
}
