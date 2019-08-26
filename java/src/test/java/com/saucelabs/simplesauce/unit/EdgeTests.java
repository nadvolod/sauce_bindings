package com.saucelabs.simplesauce.unit;

import org.junit.Test;

import java.net.MalformedURLException;
import static org.junit.Assert.assertEquals;

public class EdgeTests extends BaseConfigurationTest{
    @Test
    public void withEdge18_17763_returnsBrowserVersion18_17763() {
        fakeSauceSession.withEdge18_17763();
        fakeSauceSession.start();
        String actualBrowserSetInConfig = fakeSauceSession.sauceSessionCapabilities.getVersion();
        assertEquals("18.17763", actualBrowserSetInConfig);
    }
    @Test
    public void withEdge17_17134_returnsBrowserVersion17_17134() {
        fakeSauceSession.withEdge17_17134();
        fakeSauceSession.start();
        String actualBrowserSetInConfig = fakeSauceSession.sauceSessionCapabilities.getVersion();
        assertEquals("17.17134", actualBrowserSetInConfig);
    }
    @Test
    public void withEdge16_16299_returnsBrowserVersion16_16299() {
        fakeSauceSession.withEdge16_16299();
        fakeSauceSession.start();
        String actualBrowserSetInConfig = fakeSauceSession.sauceSessionCapabilities.getVersion();
        assertEquals("16.16299", actualBrowserSetInConfig);
    }
    @Test
    public void withEdge15_15063_returnsBrowserVersion15_15063() {
        fakeSauceSession.withEdge15_15063();
        fakeSauceSession.start();
        String actualBrowserSetInConfig = fakeSauceSession.sauceSessionCapabilities.getVersion();
        assertEquals("15.15063", actualBrowserSetInConfig);
    }
    @Test
    public void withEdge14_14393_returnsBrowserVersion14_14393() {
        fakeSauceSession.withEdge14_14393();
        fakeSauceSession.start();
        String actualBrowserSetInConfig = fakeSauceSession.sauceSessionCapabilities.getVersion();
        assertEquals("14.14393", actualBrowserSetInConfig);
    }
    @Test
    public void withEdge13_10586_returnsBrowserVersion13_10586() {
        fakeSauceSession.withEdge13_10586();
        fakeSauceSession.start();
        String actualBrowserSetInConfig = fakeSauceSession.sauceSessionCapabilities.getVersion();
        assertEquals("13.10586", actualBrowserSetInConfig);
    }
}
