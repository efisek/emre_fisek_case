package config;

public final class RuntimeConfig {
    private RuntimeConfig() {

    }

    public static String  baseUrl;
    public static String  browser;
    public static boolean headless;
    public static int     implicitWait;
    public static int     explicitWait;
    public static int     pageLoadTimeout;
    public static boolean incognito;

}
