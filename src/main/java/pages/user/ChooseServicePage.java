package pages.user;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ChooseServicePage extends BasePage {

    public ChooseServicePage() {
        super();
    }

    @FindBy(xpath = "//button[text()=\"Регистрация брака\"]")
    private WebElement buttonWedding;

    @FindBy(xpath = "//button[text()=\"Регистрация рождения\"]")
    private WebElement buttonBirth;

    @FindBy(xpath = "//button[text()=\"Регистрация смерти\"]")
    private WebElement buttonDeath;

    @Step("Нажатие кнопки 'Регистрация брака'")
    public CitizenPage clickButtonWedding() {
        logger.info("Нажимаем кнопку 'Регистрация брака'");
        click(buttonWedding);
        logger.info("Кнопка 'Регистрация брака' успешна нажата");
        return new CitizenPage();
    }

    @Step("Нажатие кнопки 'Регистрация рождения'")
    public CitizenPage clickButtonBirth() {
        logger.info("Нажимаем кнопку 'Регистрация рождения'");
        click(buttonBirth);
        logger.info("Кнопка 'Регистрация рождения' успешна нажата");
        return new CitizenPage();
    }

    @Step("Нажатие кнопки 'Регистрация смерти'")
    public CitizenPage clickButtonDeath() {
        logger.info("Нажимаем кнопку 'Регистрация смерти'");
        click(buttonDeath);
        logger.info("Кнопка 'Регистрация смерти' успешна нажата");

        return new CitizenPage();
    }
}
