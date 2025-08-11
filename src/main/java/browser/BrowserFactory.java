package browser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class BrowserFactory {
    private static final Map<String, Supplier<BrowserSetup>> browsers = new HashMap<>();

    static {
        browsers.put("chrome", ChromeBrowserSetup::new);
        //it can be implemented for other browser
        //browsers.put("firefox", FirefoxBrowserSetup::new);

    }

    public static BrowserSetup getBrowserSetup(String browserName) {
        return browsers.getOrDefault(browserName.toLowerCase(), ChromeBrowserSetup::new).get();
    }
}
