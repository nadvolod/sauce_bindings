package com.saucelabs.saucebindings.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class DisabledTest {
  @RegisterExtension
  static SauceBindingsExtension sauceExtension =
      SauceBindingsExtension.builder().enabled(false).build();

  @Test
  public void ignoresExtension() {
    Assertions.assertNull(sauceExtension.getDriver());
    Assertions.assertNull(sauceExtension.getSession());
  }
}
