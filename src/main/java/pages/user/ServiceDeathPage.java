package pages.user;

import io.qameta.allure.Step;
import model.DeathData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ServiceDeathPage extends BasePage {

    @FindBy(xpath = "//input[@id=//label[text()=\"Дата смерти\"]/@for]")
    private WebElement dateOfDeathInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Место смерти\"]/@for]")
    private WebElement placeOfDeathInput;

    @FindBy(xpath = "//button[text()=\"Завершить\"]")
    private WebElement completeButton;

    @FindBy(xpath = "//button[text()=\"Назад\"]")
    private WebElement backButton;

    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;

    public ServiceDeathPage() {super();}

    @Step("Заполнение данных услуги регистрация смерти: {deathData.dateOfDeath},{deathData.placeOfDeath}")
    public ServiceDeathPage fillForm(DeathData deathData) {
        clearAndSendKeys(dateOfDeathInput, deathData.dateOfDeath());
        clearAndSendKeys(placeOfDeathInput, deathData.placeOfDeath());
        return this;
    }
    public ServiceDeathPage fillDateOfDeath(String dateOfDeath) {
        clearAndSendKeys(dateOfDeathInput, dateOfDeath);
        return this;
    }

    public ServiceDeathPage fillPlaceOfDeath(String placeOfDeath) {
        clearAndSendKeys(placeOfDeathInput, placeOfDeath);
        return this;
    }
    @Step("нажатие кнопки завершить")
    public StatusPage clickCompleteButton() {

        click(completeButton);

        return new StatusPage();
    }

    public void clickBackButton() {
        click(backButton);
    }

    public void clickCloseButton() {
        click(closeButton);
    }
}
