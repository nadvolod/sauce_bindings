package com.saucelabs.simplesauce;

import lombok.Getter;
import lombok.Setter;

public class SauceOptions {
    //TODO can probably use BrowserType enum from Selenium to do BrowserType.CHROME
    @Getter
    @Setter
    public String browser = "Chrome";

    @Getter @Setter public String browserVersion = "latest";
    public String operatingSystem = "Windows 10";
}
