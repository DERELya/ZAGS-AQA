package utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class AllureFailureExtension
        implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            return;
        }

        WebDriver driver = DriverManager.getDriverIfExists();

        if (driver == null) {
            return;
        }

        attachScreenshot(driver);
        attachPageSource(driver);
    }

    private void attachScreenshot(WebDriver driver) {
        try {
            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            try (ByteArrayInputStream input =
                         new ByteArrayInputStream(screenshot)) {
                Allure.addAttachment(
                        "Скриншот ошибки",
                        "image/png",
                        input,
                        ".png"
                );
            }
        } catch (Exception e) {
            System.err.println(
                    "Failed to attach screenshot: " + e.getMessage()
            );
        }
    }

    private void attachPageSource(WebDriver driver) {
        try {
            String pageSource = driver.getPageSource();

            Allure.addAttachment(
                    "Page source ошибки",
                    "text/html",
                    pageSource,
                    ".html"
            );
        } catch (Exception e) {
            System.err.println(
                    "Failed to attach page source: " + e.getMessage()
            );
        }
    }

}
