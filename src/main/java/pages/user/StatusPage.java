package pages.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class StatusPage extends BasePage {
    public StatusPage() {super();}

    public boolean isSuccessMessageDisplayed(String expectedText) {
        try {
            By locator = By.xpath("//span[contains(text(), '" + expectedText + "')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}
