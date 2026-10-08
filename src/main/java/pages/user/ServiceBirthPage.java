package pages.user;

import io.qameta.allure.Step;
import model.BirthData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ServiceBirthPage extends BasePage {

    @FindBy(xpath = "//input[@id=//label[text()=\"Место рождения\"]/@for]")
    private WebElement placeOfBirthInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Мать\"]/@for]")
    private WebElement motherInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Отец\"]/@for]")
    private WebElement dadInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Бабушка\"]/@for]")
    private WebElement grandmotherInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Дедушка\"]/@for]")
    private WebElement granddadInput;

    @FindBy(xpath = "//button[text()=\"Завершить\"]")
    private WebElement completeButton;
    @FindBy(xpath = "//button[text()=\"Назад\"]")
    private WebElement backButton;
    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;

    public ServiceBirthPage() {super();}

    @Step("Заполнение данных услуги регистрация рождения")
    public ServiceBirthPage fillForm(BirthData birthData) {
        logger.info("Начинаем заполнение данные услуги регистрация рождения");
        clearAndSendKeys(placeOfBirthInput, birthData.placeOfBirth());
        clearAndSendKeys(motherInput, birthData.mother());
        clearAndSendKeys(dadInput, birthData.dad());
        clearAndSendKeys(grandmotherInput, birthData.grandmother());
        clearAndSendKeys(granddadInput, birthData.granddad());
        logger.info("Данные услуги регистрация рождения успешно заполнены");
        return this;
    }
    public ServiceBirthPage fillPlaceOfBirth(String placeOfBirth) {
        clearAndSendKeys(placeOfBirthInput, placeOfBirth);
        return this;
    }

    public ServiceBirthPage fillMotherInput(String mother) {
        clearAndSendKeys(motherInput, mother);
        return this;
    }
    public ServiceBirthPage fillDadInput(String dad) {
        clearAndSendKeys(dadInput, dad);
        return this;
    }
    public ServiceBirthPage fillGrandmotherInput(String grandmother) {
        clearAndSendKeys(grandmotherInput, grandmother);
        return this;
    }
    public ServiceBirthPage fillGranddadInput(String granddad) {
        clearAndSendKeys(granddadInput, granddad);
        return this;
    }
    @Step("нажатие кнопки завершить")
    public StatusPage clickCompleteButton() {
        logger.info("Нажимаем кнопку завершить");
        click(completeButton);
        logger.info("Кнопка 'завершить' успешна нажата");
        return new StatusPage();
    }

    public void clickBackButton() {
        click(backButton);
    }

    public void clickCloseButton() {
        click(closeButton);
    }
}
