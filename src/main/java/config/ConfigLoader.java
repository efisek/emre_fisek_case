package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {
    private static final Properties props = new Properties();
    private ConfigLoader() {

    }

    static {
        String file = System.getProperty("config.file", "config.properties");
        try (InputStream in = open(file)) {
            if (in != null) props.load(in);
        } catch (Exception ignored) { }
    }

    public static String getProperty(String key) {
        String systemOverride = System.getProperty(key);
        if (systemOverride != null && !systemOverride.isBlank()) {
            return systemOverride.trim();
        }
        String fileValue = props.getProperty(key);
        return (fileValue == null || fileValue.isBlank()) ? null : fileValue.trim();
    }

    public static void loadRuntime() {

        RuntimeConfig.baseUrl = require("baseUrl");
        RuntimeConfig.browser = require("browser").toLowerCase().trim();

        RuntimeConfig.headless  = parseBool(require("headless"));
        RuntimeConfig.incognito = parseBool(require("incognito"));

        RuntimeConfig.implicitWait    = parseInt(require("implicitWait"),    "implicitWait");
        RuntimeConfig.explicitWait    = parseInt(require("explicitWait"),    "explicitWait");
        RuntimeConfig.pageLoadTimeout = parseInt(require("pageLoadTimeout"), "pageLoadTimeout");
    }

    private static String require(String key) {
        String value = getProperty(key);
        if (value == null) throw new IllegalStateException("Missing config key: " + key);
        return value;
    }

    private static int parseInt(String raw, String key) {
        try { return Integer.parseInt(raw); }
        catch (NumberFormatException e) {
            throw new IllegalStateException("Config key '" + key + "' must be an integer, got: " + raw);
        }
    }

    private static boolean parseBool(String raw) {
        return Boolean.parseBoolean(raw);
    }

    private static InputStream open(String name) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream cp = (cl != null) ? cl.getResourceAsStream(name) : null;
            return (cp != null) ? cp : new FileInputStream(name);
        } catch (Exception e) { return null; }
    }
}
