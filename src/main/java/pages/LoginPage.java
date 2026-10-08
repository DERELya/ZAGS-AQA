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
        logger.info("Нажимаем кнопку входа пользователя");

        try {
            click(buttonUser);
            logger.info("Кнопка 'Войти как пользователь' успешно нажата");
        } catch (Exception e) {
            logger.error("Не удалось нажать кнопку 'Войти как пользователь'", e);
            throw e;
        }

        return new ApplicantPage();
    }

    @Step("Нажатие кнопки 'Войти как администратор'")
    public LoginAdminPage clickButtonAdmin() {
        logger.info("Нажимаем кнопку входа администратора");

        try {
            click(buttonAdmin);
            logger.info("Кнопка 'Войти как администратор' успешно нажата");
        } catch (Exception e) {
            logger.error("Не удалось нажать кнопку 'Войти как администратор'", e);
            throw e;
        }

        return new LoginAdminPage();
    }
}
