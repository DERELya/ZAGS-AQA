package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.selinoid.BrowserConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal =
            new ThreadLocal<>();

    private static final ThreadLocal<WebDriverWait> waitThreadLocal =
            new ThreadLocal<>();

    private static final ThreadLocal<BrowserConfig> browserThreadLocal =
            new ThreadLocal<>();

    private static final int TIMEOUT_SECONDS = 10;

    private static final boolean SELENOID = Boolean.parseBoolean(
            System.getProperty("selenoid", "false")
    );

    private static final boolean DOCKER = Boolean.parseBoolean(
            System.getProperty(
                    "docker",
                    System.getenv().getOrDefault("DOCKER", "false")
            )
    );
    private static final boolean HEADLESS = Boolean.parseBoolean(
            System.getProperty(
                    "headless",
                    System.getenv().getOrDefault(
                            "HEADLESS",
                            Boolean.toString(DOCKER)
                    )
            )
    );

    private static final String SELENOID_URL = System.getProperty(
            "selenoid.url",
            System.getenv().getOrDefault(
                    "SELENOID_URL",
                    "http://localhost:4444/wd/hub"
            )
    );

    private DriverManager() {
    }

    public static boolean isSelenoid() {
        return SELENOID;
    }

    public static void setBrowser(BrowserConfig browser) {
        browserThreadLocal.set(browser);
    }

    public static void clearBrowser() {
        browserThreadLocal.remove();
    }

    private static BrowserConfig currentBrowser() {
        BrowserConfig browser = browserThreadLocal.get();

        return browser != null ? browser : new BrowserConfig("chrome", null);
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = SELENOID
                    ? createRemoteDriver()
                    : createLocalDriver();

            try {
                if (!SELENOID && HEADLESS) {
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                } else {
                    driver.manage().window().maximize();
                }

                driver = registerAuthentication(driver);

                driverThreadLocal.set(driver);
                waitThreadLocal.set(
                        new WebDriverWait(
                                driver,
                                Duration.ofSeconds(TIMEOUT_SECONDS)
                        )
                );
            } catch (RuntimeException e) {
                try {
                    driver.quit();
                } catch (RuntimeException ignored) {

                }

                driverThreadLocal.remove();
                waitThreadLocal.remove();
                throw e;
            }
        }

        return driverThreadLocal.get();
    }

    private static WebDriver createLocalDriver() {
        ChromeOptions options = new ChromeOptions();

        if (HEADLESS) {
            options.addArguments(
                    "--headless=new",
                    "--window-size=1920,1080"
            );
        }

        if (DOCKER) {
            options.addArguments(
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createRemoteDriver() {
        BrowserConfig browser = currentBrowser();

        try {
            URL remoteUrl = URI.create(SELENOID_URL).toURL();

            return switch (browser.name()) {
                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    applyRemoteSettings(options, browser);
                    yield new RemoteWebDriver(remoteUrl, options);
                }

                case "firefox" -> {
                    FirefoxOptions options = new FirefoxOptions();
                    applyRemoteSettings(options, browser);
                    yield new RemoteWebDriver(remoteUrl, options);
                }

                default -> throw new IllegalArgumentException(
                        "Unsupported browser: " + browser.name()
                                + ". Supported browsers: chrome, firefox"
                );
            };
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new WebDriverException(
                    "Failed to create browser session via Selenoid at "
                            + SELENOID_URL + " for " + browser.displayName(),
                    e
            );
        }
    }

    private static void applyRemoteSettings(
            MutableCapabilities capabilities,
            BrowserConfig browser
    ) {
        if (browser.version() != null) {
            capabilities.setCapability("browserVersion", browser.version());
        }

        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "name", browser.displayName()
                        + " / " + Thread.currentThread().getName()
        ));
    }

    private static WebDriver registerAuthentication(WebDriver driver) {
        if (SELENOID) {
            return driver;
        }

        String username = TestConfig.getUsername();
        String password = TestConfig.getPassword();
        String host = URI.create(TestConfig.getBaseUrl()).getHost();

        WebDriver authDriver = driver;

        if (!(authDriver instanceof HasAuthentication)) {
            authDriver = new Augmenter().augment(authDriver);
        }

        if (authDriver instanceof HasAuthentication authentication) {
            authentication.register(
                    uri -> host != null
                            && host.equalsIgnoreCase(uri.getHost()),
                    () -> UsernameAndPassword.of(username, password).get()
            );

            return authDriver;
        }

        return driver;
    }

    public static String withCredentials(String url) {
        try {
            URI uri = URI.create(url);

            return new URI(
                    uri.getScheme(),
                    TestConfig.getUsername() + ":" + TestConfig.getPassword(),
                    uri.getHost(),
                    uri.getPort(),
                    uri.getPath(),
                    uri.getQuery(),
                    uri.getFragment()
            ).toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(
                    "Cannot add credentials to URL: " + url, e
            );
        }
    }

    public static WebDriverWait getWait() {
        if (waitThreadLocal.get() == null) {
            getDriver();
        }

        return waitThreadLocal.get();
    }

    public static WebDriver getDriverIfExists() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();

        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            driverThreadLocal.remove();
            waitThreadLocal.remove();
        }
    }
}