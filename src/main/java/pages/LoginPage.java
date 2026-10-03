package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
        public LoginPage(WebDriver driver) {
            super(driver);
        }

    @FindBy(xpath = "//button[text()=\"Войти как пользователь\"]")
    private WebElement buttonUser;

    public void clickButtonUser() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonUser)).click();
    }
}
