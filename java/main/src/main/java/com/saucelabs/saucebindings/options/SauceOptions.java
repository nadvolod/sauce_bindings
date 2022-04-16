package com.saucelabs.saucebindings.options;

import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.PageLoadStrategy;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.TimeoutStore;
import com.saucelabs.saucebindings.Timeouts;
import com.saucelabs.saucebindings.UnhandledPromptBehavior;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Accessors(chain = true)
@Setter @Getter
public class SauceOptions extends BaseOptions {
    @Setter(AccessLevel.NONE) @Getter(AccessLevel.NONE) private SauceLabsOptions sauceLabsOptions = null;
    public TimeoutStore timeout = new TimeoutStore();

    // w3c Settings
    @Setter(AccessLevel.NONE) protected Browser browserName = Browser.CHROME;
    protected String browserVersion = "latest";
    protected SaucePlatform platformName = SaucePlatform.WINDOWS_10;
    protected PageLoadStrategy pageLoadStrategy;
    protected Boolean acceptInsecureCerts = null;
    protected Proxy proxy;
    protected Boolean setWindowRect = null;
    @Getter(AccessLevel.NONE) protected Map<Timeouts, Integer> timeouts;
    protected Boolean strictFileInteractability = null;
    protected UnhandledPromptBehavior unhandledPromptBehavior;

    /**
     * This needs to be public for Capabilities Manager to use it
     * Valid list Sauce Labs specific options for currently supported platforms
     */
    public final List<String> validOptions = Arrays.asList(
            "browserName",
            "browserVersion",
            "platformName",
            "pageLoadStrategy",
            "acceptInsecureCerts",
            "proxy",
            "setWindowRect",
            "timeouts",
            "strictFileInteractability",
            "unhandledPromptBehavior");

    /**
     * Supports creating a SauceOptions instance from a config file instead of with conditionals in code
     * JSON & YAML are both supported.
     * Example Usage:
     * <pre>{@code new SauceOptions(pathToConfigFile, System.getEnv("firefox_mac"));}</pre>
     *
     * @param path location of the yaml or json configuration file
     * @param key which configuration in that file to use
     */
    public SauceOptions(Path path, String key) {
        this((Map<String, Object>) deserialize(path).get(key));
    }

    private static Map<String, Object> deserialize(Path path) {
        PathMatcher yamlMatcher = FileSystems.getDefault().getPathMatcher("glob:**.{yaml,yml}");
        PathMatcher jsonMatcher = FileSystems.getDefault().getPathMatcher("glob:**.json");

        try {
            if (yamlMatcher.matches(path)) {
                return loadYaml(path);
            } else if (jsonMatcher.matches(path)) {
                return loadJSON(path);
            }
        } catch (IOException e) {
            throw new InvalidSauceOptionsArgumentException("Invalid file Location", e);
        }

        throw new InvalidSauceOptionsArgumentException("Unable to parse this fyle type");
    }

    private static Map<String, Object> loadYaml(Path path) throws FileNotFoundException {
        InputStream input = new FileInputStream(path.toString());
        Yaml yaml = new Yaml();
        return yaml.load(input);
    }

    private static Map<String, Object> loadJSON(Path path) throws IOException {
        String content = new String(Files.readAllBytes(path));
        JSONObject jsonObject = new JSONObject(content);
        return jsonObject.toMap();
    }

    /**
     * This is intended to facilitate using a JSON file or a YAML to create a SauceOptions instance
     * It is currently private, but might be made public in the future
     * Raise an issue on GitHub if you have a use case for it
     *
     * @see SauceOptions(Path)
     * @param map Capabilities to use to create a SauceSession
     */
    private SauceOptions(Map<String, Object> map) {
        this(new MutableCapabilities(map));
    }

    /**
     * This method allows building a default Sauce Options instance for Chrome
     * Call build() method on return value rather than using directly
     * @return instance of ChromeConfigurations
     * @see ChromeConfigurations#build()
     */
    public static ChromeConfigurations chrome() {
        return chrome(new ChromeOptions());
    }

    /**
     * This method allows building a Sauce Options instance for Chrome using a provided Selenium ChromeOptions instance
     * Call build() method on return value rather than using directly
     *
     * @param chromeOptions an instance of a Selenium ChromeOptions class
     * @return instance of ChromeConfigurations
     * @see ChromeConfigurations#build()
     */
    public static ChromeConfigurations chrome(ChromeOptions chromeOptions) {
        return new ChromeConfigurations(chromeOptions);
    }

    /**
     * This method allows building a default Sauce Options instance for Edge
     * Call build() method on return value rather than using directly
     *
     * @return instance of EdgeConfigurations
     * @see EdgeConfigurations#build()
     */
    public static EdgeConfigurations edge() {
        return edge(new EdgeOptions());
    }

    /**
     * This method allows building a Sauce Options instance for Edge using a provided Selenium EdgeOptions instance
     * Call build() method on return value rather than using directly
     *
     * @param edgeOptions an instance of a Selenium EdgeOptions class
     * @return instance of EdgeConfigurations
     * @see EdgeConfigurations#build()
     */
    public static EdgeConfigurations edge(EdgeOptions edgeOptions) {
        return new EdgeConfigurations(edgeOptions);
    }

    /**
     * This method allows building a default Sauce Options instance for Firefox
     * Call build() method on return value rather than using directly
     *
     * @return instance of FirefoxConfigurations
     * @see FirefoxConfigurations#build()
     */
    public static FirefoxConfigurations firefox() {
        return firefox(new FirefoxOptions());
    }

    /**
     * This method allows building a Sauce Options instance for Firefox using a provided Selenium FirefoxOptions instance
     * Call build() method on return value rather than using directly
     *
     * @param firefoxOptions an instance of a Selenium FirefoxOptions class
     * @return instance of FirefoxConfigurations
     * @see FirefoxConfigurations#build()
     */
    public static FirefoxConfigurations firefox(FirefoxOptions firefoxOptions) {
        return new FirefoxConfigurations(firefoxOptions);
    }

    /**
     * This method allows building a default Sauce Options instance for Internet Explorer
     * Call build() method on return value rather than using directly
     *
     * @return instance of InternetExplorerConfigurations
     * @see InternetExplorerConfigurations#build()
     */
    public static InternetExplorerConfigurations ie() {
        return ie(new InternetExplorerOptions());
    }

    /**
     * This method allows building a Sauce Options instance for Internet Explorer using a provided Selenium InternetExplorerOptions instance
     * Call build() method on return value rather than using directly
     *
     * @param internetExplorerOptions an instance of a Selenium InternetExplorerOptions class
     * @return instance of InternetExplorerConfigurations
     * @see InternetExplorerConfigurations#build()
     */
    public static InternetExplorerConfigurations ie(InternetExplorerOptions internetExplorerOptions) {
        return new InternetExplorerConfigurations(internetExplorerOptions);
    }

    /**
     * This method allows building a default Sauce Options instance for Safari
     * Call build() method on return value rather than using directly
     *
     * @return instance of SafariConfigurations
     * @see SafariConfigurations#build()
     */
    public static SafariConfigurations safari() {
        return safari(new SafariOptions());
    }

    /**
     * This method allows building a Sauce Options instance for Safari using a provided Selenium SafariOptions instance
     * Call build() method on return value rather than using directly
     *
     * @param safariOptions an instance of a Selenium SafariOptions class
     * @return instance of SafariConfigurations
     * @see SafariConfigurations#build()
     */
    public static SafariConfigurations safari(SafariOptions safariOptions) {
        return new SafariConfigurations(safariOptions);
    }

    /**
     * This method does not need to be used if working with the static methods and the build() method
     *
     * @return an instance of SauceLabsOptions built by configurations
     */
    public SauceLabsOptions sauce() {
        return sauceLabsOptions;
    }

    /**
     * This was created to be used by SauceSession, but SauceSession does something different now.
     *
     * @deprecated Use static browser method instead e.g., SauceOptions.chrome().build()
     */
    @Deprecated
    public SauceOptions() {
        this(new MutableCapabilities());
    }

    // TODO: Make this private
    public Map<Timeouts, Integer> getTimeouts() {
        if (timeout.getTimeouts().isEmpty()) {
            return timeouts;
        }
        return timeout.getTimeouts();
    }

    /**
     * For Configuration classes to build a SauceOptions instance
     *
     * @param options Selenium capability values
     */
    SauceOptions(MutableCapabilities options) {
        capabilities = new MutableCapabilities(options.asMap()); // reset Browser Options subclasses to MutableCapabilities superclass
        // NOTE: This is what Java actually does to capabilities before sending to Sauce
        // new MutableCapabilities(CapabilitiesUtils.makeW3CSafe(options).findFirst().get().asMap());
        capabilityManager = new CapabilityManager(this);
        sauceLabsOptions = new SauceLabsOptions(capabilities.getCapability("sauce:options"));

        if (options.getCapability("browserName") != null) {
            setCapability("browserName", options.getCapability("browserName"));
        }

        processOptions();
    }

    // This insanity is required because Java treats InternetExplorerOptions differently for some reason
    private void processOptions() {
        capabilities.asMap().keySet().forEach((capability) -> {
            if (!capability.contains(":")
                    && !"browserName".equals(capability)
                    && !(Browser.INTERNET_EXPLORER.equals(browserName)
                    && ((Map) capabilities.getCapability("se:ieOptions")).containsKey(capability))) {
                setCapability(capability, capabilities.getCapability(capability));
            }
        });
    }

    /**
     * @return instance of MutableCapabilities representing all key value pairs set in SauceOptions
     * @see SauceSession#start()
     */
    public MutableCapabilities toCapabilities() {
        capabilityManager.addCapabilities();
        capabilities.setCapability("sauce:options", sauce().toCapabilities());
        return capabilities;
    }

    /**
     * This method is to handle special cases and enums as necessary
     *
     * @param key   Which capability to set on this instance's Selenium MutableCapabilities instance
     * @param value The value of the capability getting set
     */
    @Override
    public void setCapability(String key, Object value) {
        switch (key) {
        case "browserName":
            capabilityManager.validateCapability("Browser", Browser.keys(), (String) value);
            setBrowserName(Browser.valueOf(Browser.fromString((String) value)));
            break;
        case "platformName":
            String stringValue = (String) value;
            capabilityManager.validateCapability("SaucePlatform", SaucePlatform.keys(), stringValue);
            setPlatformName(SaucePlatform.valueOf(SaucePlatform.fromString(stringValue)));
            break;
        case "pageLoadStrategy":
            if (value instanceof PageLoadStrategy) {
                setPageLoadStrategy((PageLoadStrategy) value);
            } else {
                capabilityManager.validateCapability("PageLoadStrategy", PageLoadStrategy.keys(), (String) value);
                setPageLoadStrategy(PageLoadStrategy.valueOf(PageLoadStrategy.fromString((String) value)));
            }
            break;
        case "unhandledPromptBehavior":
            if (value instanceof UnhandledPromptBehavior) {
                setUnhandledPromptBehavior((UnhandledPromptBehavior) value);
            } else if (value instanceof UnexpectedAlertBehaviour) {
                setCapability("unhandledPromptBehavior", value.toString());
            } else {
                capabilityManager.validateCapability("UnhandledPromptBehavior", UnhandledPromptBehavior.keys(), (String) value);
                setUnhandledPromptBehavior(UnhandledPromptBehavior.valueOf(UnhandledPromptBehavior.fromString((String) value)));
            }
            break;
        case "timeouts":
            Map<Timeouts, Integer> timeoutsMap = new HashMap<>();
            ((Map) value).forEach((oldKey, val) -> {
                capabilityManager.validateCapability("Timeouts", Timeouts.keys(), (String) oldKey);
                String keyString = Timeouts.fromString((String) oldKey);
                timeoutsMap.put(Timeouts.valueOf(keyString), (Integer) val);
            });
            setTimeouts(timeoutsMap);
            break;
        case "sauce":
        case "sauce:options":
            Map<String, Object> map = new HashMap<>((Map<? extends String, ?>) value);
            map.keySet().forEach(k -> sauce().setCapability(k, map.get(k)));
            break;
        default:
            if (sauce().getValidOptions().contains(key)) {
                deprecatedSetCapability(key, value);
            } else {
                super.setCapability(key, value);
            }
        }
    }

    /**
     * @return duration to wait for finding an element
     */
    public Duration getImplicitWaitTimeout() {
        return Duration.ofMillis(getTimeouts().get(Timeouts.IMPLICIT));
    }

    /**
     * @return duration to wait for page to load
     */
    public Duration getPageLoadTimeout() {
        return Duration.ofMillis(getTimeouts().get(Timeouts.PAGE_LOAD));
    }

    /**
     * @return duration to wait for script to execute
     */
    public Duration getScriptTimeout() {
        return Duration.ofMillis(getTimeouts().get(Timeouts.SCRIPT));
    }

    private void deprecatedSetCapability(String key, Object value) {
        System.out.println("WARNING: using merge() of Map with value of (" + key + ") is DEPRECATED");
        System.out.println("place this value inside a nested Map with the keyword 'sauce'");
        sauce().setCapability(key, value);
    }

    /**
     * This sets the browser for the Session
     *
     * @deprecated use static browser methods with SauceOptions class to set browser name
     * @param browserName which browser to use
     * @return this SauceOptions instance
     */
    @Deprecated
    public SauceOptions setBrowserName(Browser browserName) {
        this.browserName = browserName;
        return this;
    }
}
