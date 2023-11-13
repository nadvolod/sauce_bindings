package com.saucelabs.saucebindings.junit5;

import com.saucelabs.saucebindings.DataCenter;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;

public class SauceBindingsExtension implements TestWatcher, BeforeEachCallback {
  private final SauceOptions sauceOptions;
  private final DataCenter dataCenter;
  private final Boolean enabled;
  private SauceSession session;
  private WebDriver driver;

  public SauceBindingsExtension() {
    this(builder());
  }

  private SauceBindingsExtension(Builder builder) {
    this.sauceOptions = builder.sauceOptions == null ? new SauceOptions() : builder.sauceOptions;
    this.dataCenter = builder.dataCenter;
    this.enabled = builder.enabled;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    if (!enabled) {
      return;
    }

    if (sauceOptions.sauce().getName() == null) {
      sauceOptions.sauce().setName(context.getDisplayName());
    }

    session = new SauceSession(sauceOptions);
    session.setDataCenter(dataCenter);
    driver = session.start();
  }

  public SauceSession getSession() {
    return session;
  }

  public WebDriver getDriver() {
    return driver;
  }

  @Override
  public void testSuccessful(ExtensionContext context) {
    if (!enabled) {
      return;
    }

    try {
      session.stop(true);
    } catch (NoSuchSessionException e) {
      System.out.println(
          "Driver quit prematurely; Remove calls to `driver.quit()` to allow SauceBindingsExtension"
              + " to stop the test");
    }
  }

  @Override
  public void testFailed(ExtensionContext context, Throwable cause) {
    if (session != null) {
      session.annotate("Failure Reason: " + cause.getMessage());

      Arrays.stream(cause.getStackTrace())
          .map(StackTraceElement::toString)
          .filter(line -> !line.contains("sun"))
          .forEach(session::annotate);

      session.stop(false);
    }
  }

  public static class Builder {
    private SauceOptions sauceOptions;
    private DataCenter dataCenter = DataCenter.US_WEST;
    private Boolean enabled = true;

    public Builder capabilities(SauceOptions sauceOptions) {
      this.sauceOptions = sauceOptions;
      return this;
    }

    public Builder capabilities(Capabilities capabilities) {
      this.sauceOptions = convertCapabilities(capabilities);
      return this;
    }

    public Builder dataCenter(DataCenter dataCenter) {
      this.dataCenter = dataCenter;
      return this;
    }

    public Builder enabled(Boolean enabled) {
      this.enabled = enabled;
      return this;
    }

    public SauceBindingsExtension build() {
      return new SauceBindingsExtension(this);
    }

    // TODO: Put this in Sauce Bindings directly?
    private SauceOptions convertCapabilities(Capabilities capabilities) {
      SauceOptions options = new SauceOptions();
      Map<String, Object> capabilitiesMap = new HashMap<>(capabilities.asMap());

      @SuppressWarnings("unchecked")
      Map<String, Object> sauceMap = (Map<String, Object>) capabilitiesMap.get("sauce:options");
      capabilitiesMap.remove("sauce:options");
      options.mergeCapabilities(capabilitiesMap);
      options.sauce().mergeCapabilities(sauceMap);
      return options;
    }
  }
}
