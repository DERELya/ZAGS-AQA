package pages;

import org.openqa.selenium.By;
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

    @FindBy(xpath = "//button[text()=\"Войти как администратор\"]")
    private WebElement buttonAdmin;

    @FindBy(xpath = "//button[text()=\"Заказать справку\"]")
    private WebElement buttonApplication;


    public LoginPage clickButtonUser() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonUser)).click();
        return this;
    }
}
