package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.user.ApplicantPage;

public class LoginPage extends BasePage {
        public LoginPage() {
            super();
        }

    @FindBy(xpath = "//button[text()=\"Войти как пользователь\"]")
    private WebElement buttonUser;

    @FindBy(xpath = "//button[text()=\"Войти как администратор\"]")
    private WebElement buttonAdmin;

    public ApplicantPage clickButtonUser() {
       click(buttonUser);
       return new ApplicantPage();
    }
    public void clickButtonAdmin() {
        click(buttonAdmin);
    }
}
