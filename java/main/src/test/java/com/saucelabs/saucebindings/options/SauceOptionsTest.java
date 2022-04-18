package com.saucelabs.saucebindings.options;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.JobVisibility;
import com.saucelabs.saucebindings.PageLoadStrategy;
import com.saucelabs.saucebindings.Prerun;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SystemManager;
import com.saucelabs.saucebindings.Timeouts;
import com.saucelabs.saucebindings.UnhandledPromptBehavior;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SauceOptionsTest {
    private SauceOptions sauceOptions = SauceOptions.chrome().build();

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    @Test
    public void updatesBrowserVersionPlatformVersionValues() {
        sauceOptions.setPlatformName(SaucePlatform.MAC_HIGH_SIERRA);
        sauceOptions.setBrowserVersion("99");

        Assert.assertEquals(Browser.CHROME, sauceOptions.getBrowserName());
        Assert.assertEquals("99", sauceOptions.getBrowserVersion());
        Assert.assertEquals(SaucePlatform.MAC_HIGH_SIERRA, sauceOptions.getPlatformName());
    }

    @Test
    public void fluentPatternWorks() {
        sauceOptions.setBrowserVersion("68")
                .setPlatformName(SaucePlatform.MAC_HIGH_SIERRA);

        Assert.assertEquals(Browser.CHROME, sauceOptions.getBrowserName());
        Assert.assertEquals("68", sauceOptions.getBrowserVersion());
        Assert.assertEquals(SaucePlatform.MAC_HIGH_SIERRA, sauceOptions.getPlatformName());
    }

    @Test
    public void setsOtherW3CValues() {
        sauceOptions.setAcceptInsecureCerts(true);
        sauceOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        sauceOptions.setSetWindowRect(true);
        sauceOptions.setUnhandledPromptBehavior(UnhandledPromptBehavior.IGNORE);
        sauceOptions.setStrictFileInteractability(true);

        sauceOptions.setImplicitWaitTimeout(Duration.ofSeconds(1));
        sauceOptions.setPageLoadTimeout(Duration.ofSeconds(100));
        sauceOptions.setScriptTimeout(Duration.ofSeconds(10));

        Assert.assertEquals(true, sauceOptions.getAcceptInsecureCerts());
        Assert.assertEquals(PageLoadStrategy.EAGER, sauceOptions.getPageLoadStrategy());
        Assert.assertEquals(true, sauceOptions.getSetWindowRect());
        Assert.assertEquals(UnhandledPromptBehavior.IGNORE, sauceOptions.getUnhandledPromptBehavior());
        Assert.assertEquals(true, sauceOptions.getStrictFileInteractability());
        Assert.assertEquals(Duration.ofSeconds(1), sauceOptions.getImplicitWaitTimeout());
        Assert.assertEquals(Duration.ofSeconds(100), sauceOptions.getPageLoadTimeout());
        Assert.assertEquals(Duration.ofSeconds(10), sauceOptions.getScriptTimeout());
    }

    @Test
    public void setsSauceLabsSettings() {
        Map<Prerun, Object> prerun = new HashMap<>();
        prerun.put(Prerun.EXECUTABLE, "https://url.to/your/executable.exe");
        prerun.put(Prerun.ARGS, ImmutableList.of("--silent", "-a", "-q"));
        prerun.put(Prerun.BACKGROUND, false);
        prerun.put(Prerun.TIMEOUT, 120);

        sauceOptions.sauce().setAvoidProxy(true);
        sauceOptions.sauce().setBuild("Sample Build Name");
        sauceOptions.sauce().setCapturePerformance(true);
        sauceOptions.sauce().setChromedriverVersion("71");
        sauceOptions.sauce().setCommandTimeout(2);
        sauceOptions.sauce().setCustomData(ImmutableMap.of("foo", "foo", "bar", "bar"));
        sauceOptions.sauce().setExtendedDebugging(true);
        sauceOptions.sauce().setIdleTimeout(3);
        sauceOptions.sauce().setIedriverVersion("3.141.0");
        sauceOptions.sauce().setMaxDuration(300);
        sauceOptions.sauce().setName("Test name");
        sauceOptions.sauce().setParentTunnel("Mommy");
        sauceOptions.sauce().setPrerun(prerun);
        sauceOptions.sauce().setPriority(0);
        sauceOptions.sauce().setJobVisibility(JobVisibility.TEAM);
        sauceOptions.sauce().setRecordLogs(false);
        sauceOptions.sauce().setRecordScreenshots(false);
        sauceOptions.sauce().setRecordVideo(false);
        sauceOptions.sauce().setScreenResolution("10x10");
        sauceOptions.sauce().setSeleniumVersion("3.141.59");
        sauceOptions.sauce().setTags(ImmutableList.of("Foo", "Bar", "Foobar"));
        sauceOptions.sauce().setTimeZone("San Francisco");
        sauceOptions.sauce().setTunnelIdentifier("tunnelName");
        sauceOptions.sauce().setVideoUploadOnPass(false);

        Assert.assertEquals(true, sauceOptions.sauce().getAvoidProxy());
        Assert.assertEquals("Sample Build Name", sauceOptions.sauce().getBuild());
        Assert.assertEquals(true, sauceOptions.sauce().getCapturePerformance());
        Assert.assertEquals("71", sauceOptions.sauce().getChromedriverVersion());
        Assert.assertEquals(Integer.valueOf(2), sauceOptions.sauce().getCommandTimeout());
        Assert.assertEquals(ImmutableMap.of("foo", "foo", "bar", "bar"), sauceOptions.sauce().getCustomData());
        Assert.assertEquals(true, sauceOptions.sauce().getExtendedDebugging());
        Assert.assertEquals(Integer.valueOf(3), sauceOptions.sauce().getIdleTimeout());
        Assert.assertEquals("3.141.0", sauceOptions.sauce().getIedriverVersion());
        Assert.assertEquals(Integer.valueOf(300), sauceOptions.sauce().getMaxDuration());
        Assert.assertEquals("Test name", sauceOptions.sauce().getName());
        Assert.assertEquals("Mommy", sauceOptions.sauce().getParentTunnel());
        Assert.assertEquals(prerun, sauceOptions.sauce().getPrerun());
        Assert.assertEquals(Integer.valueOf(0), sauceOptions.sauce().getPriority());
        Assert.assertEquals(JobVisibility.TEAM, sauceOptions.sauce().getJobVisibility());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordLogs());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordScreenshots());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordVideo());
        Assert.assertEquals("10x10", sauceOptions.sauce().getScreenResolution());
        Assert.assertEquals("3.141.59", sauceOptions.sauce().getSeleniumVersion());
        Assert.assertEquals(ImmutableList.of("Foo", "Bar", "Foobar"), sauceOptions.sauce().getTags());
        Assert.assertEquals("San Francisco", sauceOptions.sauce().getTimeZone());
        Assert.assertEquals("tunnelName", sauceOptions.sauce().getTunnelIdentifier());
        Assert.assertEquals(false, sauceOptions.sauce().getVideoUploadOnPass());
    }

    @Test
    public void createsDefaultBuildName() {
        Assert.assertNotNull(sauceOptions.sauce().getBuild());
    }

    @Test
    public void setsCapabilitiesFromYaml() {
        Path path = Paths.get("src/test/java/com/saucelabs/saucebindings/options.yml");
        sauceOptions = new SauceOptions(path, "firefoxMac");

        Map<Prerun, Object> prerun = new HashMap<>();
        prerun.put(Prerun.EXECUTABLE, "https://url.to/your/executable.exe");
        prerun.put(Prerun.ARGS, ImmutableList.of("--silent", "-a", "-q"));
        prerun.put(Prerun.BACKGROUND, false);
        prerun.put(Prerun.TIMEOUT, 120);

        Assert.assertEquals(Browser.FIREFOX, sauceOptions.getBrowserName());
        Assert.assertEquals("90", sauceOptions.getBrowserVersion());
        Assert.assertEquals(SaucePlatform.MAC_HIGH_SIERRA, sauceOptions.getPlatformName());
        Assert.assertEquals(true, sauceOptions.getAcceptInsecureCerts());
        Assert.assertEquals(PageLoadStrategy.EAGER, sauceOptions.getPageLoadStrategy());
        Assert.assertEquals(true, sauceOptions.getSetWindowRect());
        Assert.assertEquals(UnhandledPromptBehavior.ACCEPT, sauceOptions.getUnhandledPromptBehavior());
        Assert.assertEquals(true, sauceOptions.getStrictFileInteractability());
        Assert.assertEquals(Duration.ofSeconds(1), sauceOptions.getImplicitWaitTimeout());
        Assert.assertEquals(Duration.ofSeconds(59), sauceOptions.getPageLoadTimeout());
        Assert.assertEquals(Duration.ofSeconds(29), sauceOptions.getScriptTimeout());
        Assert.assertEquals(true, sauceOptions.sauce().getAvoidProxy());
        Assert.assertEquals("Sample Build Name", sauceOptions.sauce().getBuild());
        Assert.assertEquals(true, sauceOptions.sauce().getCapturePerformance());
        Assert.assertEquals("71", sauceOptions.sauce().getChromedriverVersion());
        Assert.assertEquals(Integer.valueOf(2), sauceOptions.sauce().getCommandTimeout());
        Assert.assertEquals(ImmutableMap.of("foo", "foo", "bar", "bar"), sauceOptions.sauce().getCustomData());
        Assert.assertEquals(true, sauceOptions.sauce().getExtendedDebugging());
        Assert.assertEquals(Integer.valueOf(3), sauceOptions.sauce().getIdleTimeout());
        Assert.assertEquals("3.141.0", sauceOptions.sauce().getIedriverVersion());
        Assert.assertEquals(Integer.valueOf(300), sauceOptions.sauce().getMaxDuration());
        Assert.assertEquals("Sample Test Name", sauceOptions.sauce().getName());
        Assert.assertEquals("Mommy", sauceOptions.sauce().getParentTunnel());
        Assert.assertEquals(prerun, sauceOptions.sauce().getPrerun());
        Assert.assertEquals(Integer.valueOf(0), sauceOptions.sauce().getPriority());
        Assert.assertEquals(JobVisibility.TEAM, sauceOptions.sauce().getJobVisibility());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordLogs());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordScreenshots());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordVideo());
        Assert.assertEquals("10x10", sauceOptions.sauce().getScreenResolution());
        Assert.assertEquals("3.141.59", sauceOptions.sauce().getSeleniumVersion());
        Assert.assertEquals(ImmutableList.of("foo", "bar", "foobar"), sauceOptions.sauce().getTags());
        Assert.assertEquals("San Francisco", sauceOptions.sauce().getTimeZone());
        Assert.assertEquals("tunnelName", sauceOptions.sauce().getTunnelIdentifier());
        Assert.assertEquals(false, sauceOptions.sauce().getVideoUploadOnPass());
        Assert.assertEquals(ImmutableMap.of("args", ImmutableList.of("-a")),
                sauceOptions.getCapabilities().getCapability("moz:firefoxOptions"));
    }

    @Test
    public void setsCapabilitiesFromJSON() {
        Path path = Paths.get("src/test/java/com/saucelabs/saucebindings/options.json");
        sauceOptions = new SauceOptions(path, "firefoxMac");

        Map<Prerun, Object> prerun = new HashMap<>();
        prerun.put(Prerun.EXECUTABLE, "https://url.to/your/executable.exe");
        prerun.put(Prerun.ARGS, ImmutableList.of("--silent", "-a", "-q"));
        prerun.put(Prerun.BACKGROUND, false);
        prerun.put(Prerun.TIMEOUT, 120);

        Assert.assertEquals(Browser.FIREFOX, sauceOptions.getBrowserName());
        Assert.assertEquals("90", sauceOptions.getBrowserVersion());
        Assert.assertEquals(SaucePlatform.MAC_HIGH_SIERRA, sauceOptions.getPlatformName());
        Assert.assertEquals(true, sauceOptions.getAcceptInsecureCerts());
        Assert.assertEquals(PageLoadStrategy.EAGER, sauceOptions.getPageLoadStrategy());
        Assert.assertEquals(true, sauceOptions.getSetWindowRect());
        Assert.assertEquals(UnhandledPromptBehavior.ACCEPT, sauceOptions.getUnhandledPromptBehavior());
        Assert.assertEquals(true, sauceOptions.getStrictFileInteractability());
        Assert.assertEquals(Duration.ofSeconds(1), sauceOptions.getImplicitWaitTimeout());
        Assert.assertEquals(Duration.ofSeconds(59), sauceOptions.getPageLoadTimeout());
        Assert.assertEquals(Duration.ofSeconds(29), sauceOptions.getScriptTimeout());
        Assert.assertEquals(true, sauceOptions.sauce().getAvoidProxy());
        Assert.assertEquals("Sample Build Name", sauceOptions.sauce().getBuild());
        Assert.assertEquals(true, sauceOptions.sauce().getCapturePerformance());
        Assert.assertEquals("71", sauceOptions.sauce().getChromedriverVersion());
        Assert.assertEquals(Integer.valueOf(2), sauceOptions.sauce().getCommandTimeout());
        Assert.assertEquals(ImmutableMap.of("foo", "foo", "bar", "bar"), sauceOptions.sauce().getCustomData());
        Assert.assertEquals(true, sauceOptions.sauce().getExtendedDebugging());
        Assert.assertEquals(Integer.valueOf(3), sauceOptions.sauce().getIdleTimeout());
        Assert.assertEquals("3.141.0", sauceOptions.sauce().getIedriverVersion());
        Assert.assertEquals(Integer.valueOf(300), sauceOptions.sauce().getMaxDuration());
        Assert.assertEquals("Sample Test Name", sauceOptions.sauce().getName());
        Assert.assertEquals("Mommy", sauceOptions.sauce().getParentTunnel());
        Assert.assertEquals(prerun, sauceOptions.sauce().getPrerun());
        Assert.assertEquals(Integer.valueOf(0), sauceOptions.sauce().getPriority());
        Assert.assertEquals(JobVisibility.TEAM, sauceOptions.sauce().getJobVisibility());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordLogs());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordScreenshots());
        Assert.assertEquals(false, sauceOptions.sauce().getRecordVideo());
        Assert.assertEquals("10x10", sauceOptions.sauce().getScreenResolution());
        Assert.assertEquals("3.141.59", sauceOptions.sauce().getSeleniumVersion());
        Assert.assertEquals(ImmutableList.of("foo", "bar", "foobar"), sauceOptions.sauce().getTags());
        Assert.assertEquals("San Francisco", sauceOptions.sauce().getTimeZone());
        Assert.assertEquals("tunnelName", sauceOptions.sauce().getTunnelIdentifier());
        Assert.assertEquals(false, sauceOptions.sauce().getVideoUploadOnPass());
    }

    @Test(expected = InvalidSauceOptionsArgumentException.class)
    public void errorsBadChromeOptionsCapability() {
        Path path = Paths.get("src/test/java/com/saucelabs/saucebindings/badOptions.yml");

        sauceOptions = new SauceOptions(path, "invalidCap");
    }

    @Test(expected = InvalidSauceOptionsArgumentException.class)
    public void errorsBadChromeOptionsSauceCapability() {
        Path path = Paths.get("src/test/java/com/saucelabs/saucebindings/badOptions.yml");

        sauceOptions = new SauceOptions(path, "invalidSauceCap");
    }

    @Test(expected = InvalidSauceOptionsArgumentException.class)
    public void errorsBadChromeOptionsValue() {
        Path path = Paths.get("src/test/java/com/saucelabs/saucebindings/badOptions.yml");

        sauceOptions = new SauceOptions(path, "badValue");
    }

    @Test(expected = InvalidSauceOptionsArgumentException.class)
    public void errorsBadChromeOptionsSauceValue() {
        Path path = Paths.get("src/test/java/com/saucelabs/saucebindings/badOptions.yml");

        sauceOptions = new SauceOptions(path, "badSauceValue");
    }

    @Ignore("Current code does not parse by browser making this a more difficult implementation at this time")
    @Test(expected = InvalidSauceOptionsArgumentException.class)
    public void errorsBadNameSpacedValues() {
        Path path = Paths.get("src/test/java/com/saucelabs/saucebindings/badOptions.yml");

        sauceOptions = new SauceOptions(path, "invalidPrefix");
    }

    @Test
    public void parsesCapabilitiesFromW3CValues() {
        sauceOptions.setPlatformName(SaucePlatform.MAC_BIG_SUR);
        sauceOptions.setBrowserVersion("77");
        sauceOptions.setAcceptInsecureCerts(true);
        sauceOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        sauceOptions.setSetWindowRect(true);
        sauceOptions.setUnhandledPromptBehavior(UnhandledPromptBehavior.IGNORE);
        sauceOptions.setStrictFileInteractability(true);
        sauceOptions.setImplicitWaitTimeout(Duration.ofSeconds(1));
        sauceOptions.setPageLoadTimeout(Duration.ofSeconds(100));
        sauceOptions.setScriptTimeout(Duration.ofSeconds(10));
        sauceOptions.sauce().setBuild("Build Name");

        Map<Timeouts, Integer> timeouts = new HashMap<>();
        timeouts.put(Timeouts.IMPLICIT, 1000);
        timeouts.put(Timeouts.SCRIPT, 10000);
        timeouts.put(Timeouts.PAGE_LOAD, 100000);

        MutableCapabilities expectedCapabilities = new MutableCapabilities();
        expectedCapabilities.setCapability("browserName", "chrome");
        expectedCapabilities.setCapability("browserVersion", "77");
        expectedCapabilities.setCapability("platformName", "macOS 11");
        expectedCapabilities.setCapability("acceptInsecureCerts", true);
        expectedCapabilities.setCapability("setWindowRect", true);
        expectedCapabilities.setCapability("strictFileInteractability", true);
        expectedCapabilities.setCapability("pageLoadStrategy", PageLoadStrategy.EAGER);
        expectedCapabilities.setCapability("timeouts", timeouts);
        expectedCapabilities.setCapability("unhandledPromptBehavior", UnhandledPromptBehavior.IGNORE);

        MutableCapabilities sauceCapabilities = new MutableCapabilities();
        sauceCapabilities.setCapability("build", "Build Name");
        sauceCapabilities.setCapability("username", SystemManager.get("SAUCE_USERNAME"));
        sauceCapabilities.setCapability("accessKey", SystemManager.get("SAUCE_ACCESS_KEY"));
        expectedCapabilities.setCapability("sauce:options", sauceCapabilities);
        expectedCapabilities.setCapability("goog:chromeOptions",
                ImmutableMap.of("args", new ArrayList<>(),
                        "extensions", new ArrayList<>()));
        MutableCapabilities actualCapabilities = sauceOptions.toCapabilities();

        Assert.assertEquals(expectedCapabilities.asMap().toString(), actualCapabilities.asMap().toString());
    }

    @Test
    public void parsesCapabilitiesFromSauceValues() {
        Map<Prerun, Object> prerun = new HashMap<>();
        prerun.put(Prerun.EXECUTABLE, "https://url.to/your/executable.exe");
        prerun.put(Prerun.ARGS, ImmutableList.of("--silent", "-a", "-q"));
        prerun.put(Prerun.BACKGROUND, false);
        prerun.put(Prerun.TIMEOUT, 120);

        sauceOptions.sauce().setAvoidProxy(true);
        sauceOptions.sauce().setBuild("Sample Build Name");
        sauceOptions.sauce().setCapturePerformance(true);
        sauceOptions.sauce().setChromedriverVersion("71");
        sauceOptions.sauce().setCommandTimeout(2);
        sauceOptions.sauce().setCustomData(ImmutableMap.of("foo", "foo", "bar", "bar"));
        sauceOptions.sauce().setExtendedDebugging(true);
        sauceOptions.sauce().setIdleTimeout(3);
        sauceOptions.sauce().setIedriverVersion("3.141.0");
        sauceOptions.sauce().setMaxDuration(300);
        sauceOptions.sauce().setName("Test name");
        sauceOptions.sauce().setParentTunnel("Mommy");
        sauceOptions.sauce().setPrerun(prerun);
        sauceOptions.sauce().setPriority(0);
        sauceOptions.sauce().setJobVisibility(JobVisibility.TEAM);
        sauceOptions.sauce().setRecordLogs(false);
        sauceOptions.sauce().setRecordScreenshots(false);
        sauceOptions.sauce().setRecordVideo(false);
        sauceOptions.sauce().setScreenResolution("10x10");
        sauceOptions.sauce().setSeleniumVersion("3.141.59");
        sauceOptions.sauce().setTags(ImmutableList.of("Foo", "Bar", "Foobar"));
        sauceOptions.sauce().setTimeZone("San Francisco");
        sauceOptions.sauce().setTunnelIdentifier("tunnelName");
        sauceOptions.sauce().setVideoUploadOnPass(false);

        MutableCapabilities sauceCapabilities = new MutableCapabilities();
        sauceCapabilities.setCapability("avoidProxy", true);
        sauceCapabilities.setCapability("build", "Sample Build Name");
        sauceCapabilities.setCapability("capturePerformance", true);
        sauceCapabilities.setCapability("chromedriverVersion", "71");
        sauceCapabilities.setCapability("commandTimeout", 2);
        sauceCapabilities.setCapability("custom-data", ImmutableMap.of("foo", "foo", "bar", "bar"));
        sauceCapabilities.setCapability("extendedDebugging", true);
        sauceCapabilities.setCapability("idleTimeout", 3);
        sauceCapabilities.setCapability("iedriverVersion", "3.141.0");
        sauceCapabilities.setCapability("maxDuration", 300);
        sauceCapabilities.setCapability("name", "Test name");
        sauceCapabilities.setCapability("parentTunnel", "Mommy");
        sauceCapabilities.setCapability("prerun", prerun);
        sauceCapabilities.setCapability("priority", 0);
        sauceCapabilities.setCapability("public", "team");
        sauceCapabilities.setCapability("recordLogs", false);
        sauceCapabilities.setCapability("recordScreenshots", false);
        sauceCapabilities.setCapability("recordVideo", false);
        sauceCapabilities.setCapability("screenResolution", "10x10");
        sauceCapabilities.setCapability("seleniumVersion", "3.141.59");
        sauceCapabilities.setCapability("tags", ImmutableList.of("Foo", "Bar", "Foobar"));
        sauceCapabilities.setCapability("timeZone", "San Francisco");
        sauceCapabilities.setCapability("tunnelIdentifier", "tunnelName");
        sauceCapabilities.setCapability("videoUploadOnPass", false);
        sauceCapabilities.setCapability("username", SystemManager.get("SAUCE_USERNAME"));
        sauceCapabilities.setCapability("accessKey", SystemManager.get("SAUCE_ACCESS_KEY"));

        MutableCapabilities expectedCapabilities = new MutableCapabilities();
        expectedCapabilities.setCapability("browserName", "chrome");
        expectedCapabilities.setCapability("browserVersion", "latest");
        expectedCapabilities.setCapability("platformName", "Windows 10");
        expectedCapabilities.setCapability("goog:chromeOptions",
                ImmutableMap.of("args", new ArrayList<>(),
                        "extensions", new ArrayList<>()));

        expectedCapabilities.setCapability("goog:chromeOptions",
                ImmutableMap.of("args", new ArrayList<>(), "extensions", new ArrayList<String>()));
        expectedCapabilities.setCapability("sauce:options", sauceCapabilities);
        MutableCapabilities actualCapabilities = sauceOptions.toCapabilities();

        // toString() serializes the enums
        Assert.assertEquals(expectedCapabilities.asMap().toString(), actualCapabilities.asMap().toString());
    }

    @Test
    public void parsesCapabilitiesFromSeleniumValues() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--foo");
        firefoxOptions.addPreference("foo", "bar");
        firefoxOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

        sauceOptions = SauceOptions.firefox(firefoxOptions).build();
        sauceOptions.sauce().setBuild("Build Name");

        MutableCapabilities expectedCapabilities = new MutableCapabilities();
        expectedCapabilities.setCapability("browserName", "firefox");
        expectedCapabilities.setCapability("browserVersion", "latest");
        expectedCapabilities.setCapability("platformName", "Windows 10");
        expectedCapabilities = expectedCapabilities.merge(firefoxOptions);

        MutableCapabilities sauceCapabilities = new MutableCapabilities();
        sauceCapabilities.setCapability("build", "Build Name");
        sauceCapabilities.setCapability("username", SystemManager.get("SAUCE_USERNAME"));
        sauceCapabilities.setCapability("accessKey", SystemManager.get("SAUCE_ACCESS_KEY"));
        expectedCapabilities.setCapability("sauce:options", sauceCapabilities);
        MutableCapabilities actualCapabilities = sauceOptions.toCapabilities();

        Assert.assertEquals(expectedCapabilities.asMap().toString(), actualCapabilities.asMap().toString());
    }

    @Test
    public void parsesW3CAndSauceAndSeleniumSettings() {
        MutableCapabilities expectedCapabilities = new MutableCapabilities();
        MutableCapabilities sauceCapabilities = new MutableCapabilities();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--foo");
        firefoxOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

        sauceOptions = SauceOptions.firefox(firefoxOptions).build();

        expectedCapabilities = expectedCapabilities.merge(firefoxOptions);
        expectedCapabilities.setCapability("browserVersion", "latest");
        expectedCapabilities.setCapability("platformName", "Windows 10");
        expectedCapabilities.setCapability("acceptInsecureCerts", true);

        sauceOptions.sauce().setBuild("CUSTOM BUILD: 12");
        sauceCapabilities.setCapability("build", "CUSTOM BUILD: 12");

        sauceOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        expectedCapabilities.setCapability("pageLoadStrategy", PageLoadStrategy.EAGER);
        sauceOptions.setAcceptInsecureCerts(true);
        expectedCapabilities.setCapability("acceptInsecureCerts", true);
        sauceOptions.setImplicitWaitTimeout(Duration.ofSeconds(1))
                .setPageLoadTimeout(Duration.ofSeconds(100))
                .setScriptTimeout(Duration.ofSeconds(10));
        Map<Timeouts, Integer> timeouts = new HashMap<>();
        timeouts.put(Timeouts.IMPLICIT, 1000);
        timeouts.put(Timeouts.SCRIPT, 10000);
        timeouts.put(Timeouts.PAGE_LOAD, 100000);
        expectedCapabilities.setCapability("timeouts", timeouts);
        sauceOptions.setUnhandledPromptBehavior(UnhandledPromptBehavior.IGNORE);
        expectedCapabilities.setCapability("unhandledPromptBehavior", UnhandledPromptBehavior.IGNORE);

        sauceOptions.sauce().setExtendedDebugging(true);
        sauceCapabilities.setCapability("extendedDebugging", true);
        sauceOptions.sauce().setName("Test name");
        sauceCapabilities.setCapability("name", "Test name");
        sauceOptions.sauce().setParentTunnel("Mommy");
        sauceCapabilities.setCapability("parentTunnel", "Mommy");

        sauceOptions.sauce().setJobVisibility(JobVisibility.SHARE);
        sauceCapabilities.setCapability("public", JobVisibility.SHARE);
        sauceCapabilities.setCapability("username", SystemManager.get("SAUCE_USERNAME"));
        sauceCapabilities.setCapability("accessKey", SystemManager.get("SAUCE_ACCESS_KEY"));

        expectedCapabilities.setCapability("sauce:options", sauceCapabilities);
        MutableCapabilities actualCapabilities = sauceOptions.toCapabilities();

        Assert.assertEquals(expectedCapabilities.asMap().toString(), actualCapabilities.asMap().toString());
    }
}
