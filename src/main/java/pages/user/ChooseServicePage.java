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
        click(buttonWedding);
        return new CitizenPage();
    }

    @Step("Нажатие кнопки 'Регистрация рождения'")
    public CitizenPage clickButtonBirth() {
        click(buttonBirth);
        return new CitizenPage();
    }

    @Step("Нажатие кнопки 'Регистрация смерти'")
    public CitizenPage clickButtonDeath() {
        click(buttonDeath);

        return new CitizenPage();
    }
}
