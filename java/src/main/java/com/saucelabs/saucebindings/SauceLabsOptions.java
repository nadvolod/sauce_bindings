package com.saucelabs.saucebindings;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Accessors(chain = true) @Setter @Getter
public class SauceLabsOptions extends Options {
    // https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options
    private Boolean avoidProxy = null;
    private String build;
    private Boolean capturePerformance = null;
    private String chromedriverVersion;
    private Integer commandTimeout = null;
    private Map<String, Object> customData = null;
    private Boolean extendedDebugging = null;
    private Integer idleTimeout = null;
    private String iedriverVersion;
    private Integer maxDuration = null;
    private String name;
    private String parentTunnel;
    private Map<Prerun, Object> prerun;
    private URL prerunUrl;
    private Integer priority = null;
    private JobVisibility jobVisibility; // the actual key for this is a Java reserved keyword "public"; uses enum
    private Boolean recordLogs = null;
    private Boolean recordScreenshots = null;
    private Boolean recordVideo = null;
    private String screenResolution;
    private String seleniumVersion;
    private List<String> tags = null;
    private String timeZone;
    private String tunnelIdentifier;
    private Boolean videoUploadOnPass = null;


    public final List<String> validOptions = Arrays.asList(
            "avoidProxy",
            "build",
            "capturePerformance",
            "chromedriverVersion",
            "commandTimeout",
            "customData",
            "extendedDebugging",
            "idleTimeout",
            "iedriverVersion",
            "jobVisibility",
            "maxDuration",
            "name",
            "parentTunnel",
            "prerun",
            "prerunUrl",
            "priority",
            // public, do not use, reserved keyword, using jobVisibility with enum
            "recordLogs",
            "recordScreenshots",
            "recordVideo",
            "screenResolution",
            "seleniumVersion",
            "tags",
            "timeZone",
            "tunnelIdentifier",
            "videoUploadOnPass");

    public SauceLabsOptions() {
        capabilityManager = new CapabilityManager(this);
    }

    public MutableCapabilities toCapabilities() {
        capabilities.setCapability("username", getSauceUsername());
        capabilities.setCapability("accessKey", getSauceAccessKey());

        Object visibilityValue = capabilityManager.getCapability("jobVisibility");
        if (visibilityValue != null) {
            capabilities.setCapability("public", visibilityValue);
            setJobVisibility(null);
        }

        Object prerunValue = capabilityManager.getCapability("prerunUrl");
        if (prerunValue != null) {
            capabilities.setCapability("prerun", prerunValue);
            setPrerunUrl(null);
        }

        capabilityManager.addCapabilities();
        return capabilities;
    }

    @Override
    protected void setCapability(String key, Object value) {
        if ("jobVisibility".equals(key)) {
            capabilityManager.capabilityValidator("JobVisibility", JobVisibility.keys(), (String) value);
            setJobVisibility(JobVisibility.valueOf(JobVisibility.fromString((String) value)));
        } else if ("prerun".equals(key)) {
            Map<Prerun, Object> prerunMap = new HashMap<>();
            ((Map) value).forEach((oldKey, val) -> {
                capabilityManager.capabilityValidator("Prerun", Prerun.keys(), (String) oldKey);
                String keyString = Prerun.fromString((String) oldKey);
                prerunMap.put(Prerun.valueOf(keyString), val);
            });
            setPrerun(prerunMap);
        } else {
            super.setCapability(key, value);
        }
    }

    public String getBuild() {
        if (build != null) {
            return build;
        } else if (SystemManager.getEnv(knownCITools.get("Jenkins")) != null) {
            return SystemManager.getEnv("BUILD_NAME") + ": " + SystemManager.getEnv("BUILD_NUMBER");
        } else if (SystemManager.getEnv(knownCITools.get("Bamboo")) != null) {
            return SystemManager.getEnv("bamboo_shortJobName") + ": " + SystemManager.getEnv("bamboo_buildNumber");
        } else if (SystemManager.getEnv(knownCITools.get("Travis")) != null) {
            return SystemManager.getEnv("TRAVIS_JOB_NAME") + ": " + SystemManager.getEnv("TRAVIS_JOB_NUMBER");
        } else if (SystemManager.getEnv(knownCITools.get("Circle")) != null) {
            return SystemManager.getEnv("CIRCLE_JOB") + ": " + SystemManager.getEnv("CIRCLE_BUILD_NUM");
        } else if (SystemManager.getEnv(knownCITools.get("GitLab")) != null) {
            return SystemManager.getEnv("CI_JOB_NAME") + ": " + SystemManager.getEnv("CI_JOB_ID");
        } else if (SystemManager.getEnv(knownCITools.get("TeamCity")) != null) {
            return SystemManager.getEnv("TEAMCITY_PROJECT_NAME") + ": " + SystemManager.getEnv("BUILD_NUMBER");
        } else {
            return "Build Time: " + System.currentTimeMillis();
        }
    }

    public boolean isKnownCI() {
        return !knownCITools.keySet().stream().allMatch((key) -> SystemManager.getEnv(key) == null);
    }

    public static final Map<String, String> knownCITools;

    static {
        knownCITools = new HashMap<>();
        knownCITools.put("Jenkins", "BUILD_TAG");
        knownCITools.put("Bamboo", "bamboo_agentId");
        knownCITools.put("Travis", "TRAVIS_JOB_ID");
        knownCITools.put("Circle", "CIRCLE_JOB");
        knownCITools.put("GitLab", "CI");
        knownCITools.put("TeamCity", "TEAMCITY_PROJECT_NAME");
    }

    protected String getSauceUsername() {
        return SystemManager.get("SAUCE_USERNAME", "Sauce Username was not provided");
    }

    protected String getSauceAccessKey() {
        return SystemManager.get("SAUCE_ACCESS_KEY", "Sauce Access Key was not provided");
    }
}
