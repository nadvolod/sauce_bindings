package com.saucelabs.saucebindings.options;

import org.openqa.selenium.edge.EdgeOptions;

/**
 * Capabilities that apply only to Edge sessions.
 */
public class EdgeConfigurations extends VDCConfigurations<EdgeConfigurations> {
    EdgeConfigurations(EdgeOptions edgeOptions) {
        validatePrefix("ms", edgeOptions.asMap());
        validateBrowserName("MicrosoftEdge", edgeOptions.getBrowserName());
        sauceOptions = new SauceOptions(edgeOptions);
    }

    /**
     * Version of Edge driver to use. Typically, for specifying a different patch version than the Sauce default.
     *
     * @param version the specific version of Driver to use with the job
     * @return instance of configuration
     */
    public EdgeConfigurations setEdgedriverVersion(String version) {
        sauceOptions.sauce().setEdgedriverVersion(version);
        return this;
    }

    /**
     * You should almost always use the latest version of Selenium.
     *
     * @param version the version of Selenium Server to use for the test
     * @return instance of configuration
     */
    public EdgeConfigurations setSeleniumVersion(String version) {
        sauceOptions.sauce().setSeleniumVersion(version);
        return this;
    }
}
