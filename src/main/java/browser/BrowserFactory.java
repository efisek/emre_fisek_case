package browser;

import config.RuntimeConfig;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public final class BrowserFactory {
    private BrowserFactory(){

    }
    private static final Map<String, Supplier<BrowserSetup>> REGISTRY = new HashMap<>();

    static {
        register("chrome", ChromeBrowserSetup::new);
        //it can be implemented for other browsers
        //register("firefox", FirefoxBrowserSetup::new);
    }

    public static WebDriver create(String browserName) {
        String chosenBrowser = (browserName == null || browserName.isBlank()) ? RuntimeConfig.browser : browserName;
        String key = chosenBrowser.toLowerCase(Locale.ROOT).trim();

        Supplier<BrowserSetup> supplier = REGISTRY.get(key);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown browser: " + key + " | Known: " + REGISTRY.keySet());
        }
        return supplier.get().setupDriver();
    }

    public static void register(String name, Supplier<BrowserSetup> supplier) {
        REGISTRY.put(name.toLowerCase(Locale.ROOT), supplier);
    }

}
