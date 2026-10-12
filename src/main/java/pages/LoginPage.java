package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.admin.LoginAdminPage;
import pages.user.ApplicantPage;

public class LoginPage extends BasePage {
    public LoginPage() {
        super();
    }

    @FindBy(xpath = "//button[text()=\"Войти как пользователь\"]")
    private WebElement buttonUser;

    @FindBy(xpath = "//button[text()=\"Войти как администратор\"]")
    private WebElement buttonAdmin;

    @Step("Нажатие кнопки 'Войти как пользователь'")
    public ApplicantPage clickButtonUser() {
        click(buttonUser);
        return new ApplicantPage();
    }

    @Step("Нажатие кнопки 'Войти как администратор'")
    public LoginAdminPage clickButtonAdmin() {
        click(buttonAdmin);
        return new LoginAdminPage();
    }
}
