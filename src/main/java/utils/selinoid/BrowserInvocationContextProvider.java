package utils.selinoid;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import utils.DriverManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class BrowserInvocationContextProvider
        implements TestTemplateInvocationContextProvider {

    private static final List<BrowserConfig> SELENOID_BROWSERS = List.of(
            new BrowserConfig("chrome", "148.0"),
            new BrowserConfig("firefox", "125.0"),
            new BrowserConfig("chrome", "147.0")
    );

    private static final List<BrowserConfig> LOCAL_BROWSERS = List.of(
            new BrowserConfig("chrome", null)
    );

    private static final Map<String, Semaphore> LANES =
            new ConcurrentHashMap<>();

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext>
    provideTestTemplateInvocationContexts(ExtensionContext context) {
        String testName = context.getDisplayName();
        boolean selenoid = DriverManager.isSelenoid();

        List<BrowserConfig> browsers =
                selenoid ? SELENOID_BROWSERS : LOCAL_BROWSERS;

        return browsers.stream()
                .map(browser -> invocationContext(browser, testName, selenoid));
    }

    private static TestTemplateInvocationContext invocationContext(
            BrowserConfig browser,
            String testName,
            boolean selenoid
    ) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return selenoid
                        ? testName + " [" + browser.displayName() + "]"
                        : testName;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(new BrowserExtension(browser, selenoid));
            }
        };
    }

    private static Semaphore lane(BrowserConfig browser) {
        return LANES.computeIfAbsent(
                browser.displayName(),
                key -> new Semaphore(1, true)
        );
    }

    private static void acquire(Semaphore semaphore)
            throws InterruptedException {
        ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker() {
            private boolean acquired;

            @Override
            public boolean block() throws InterruptedException {
                if (!acquired) {
                    semaphore.acquire();
                    acquired = true;
                }
                return true;
            }

            @Override
            public boolean isReleasable() {
                if (!acquired) {
                    acquired = semaphore.tryAcquire();
                }
                return acquired;
            }
        });
    }


    private static final class BrowserExtension
            implements BeforeEachCallback, AfterEachCallback {

        private final BrowserConfig browser;
        private final boolean useLane;
        private volatile boolean acquired;

        private BrowserExtension(BrowserConfig browser, boolean useLane) {
            this.browser = browser;
            this.useLane = useLane;
        }

        @Override
        public void beforeEach(ExtensionContext context)
                throws InterruptedException {
            if (useLane) {
                acquire(lane(browser));
                acquired = true;
            }

            DriverManager.setBrowser(browser);
        }

        @Override
        public void afterEach(ExtensionContext context) {
            DriverManager.clearBrowser();

            if (acquired) {
                acquired = false;
                lane(browser).release();
            }
        }
    }
}