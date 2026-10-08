package pages.user;

import io.qameta.allure.Step;
import model.CitizenData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.function.Supplier;

public class CitizenPage extends BasePage {

    @FindBy(xpath = "//input[@id=//label[text()=\"Фамилия\"]/@for]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Имя\"]/@for]")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Отчество\"]/@for]")
    private WebElement middleNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Дата рождения\"]/@for]")
    private WebElement dateOfBirthInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Номер паспорта\"]/@for]")
    private WebElement passportInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Пол\"]/@for]")
    private WebElement genderInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Адрес прописки\"]/@for]")
    private WebElement addressInput;

    @FindBy(xpath = "//button[text()=\"Далее\"]")
    private WebElement nextButton;

    public CitizenPage() {
       super();
    }

    @Step("Заполнение данных гражданина")
    public CitizenPage fillForm(CitizenData citizen) {
        logger.info("Начинаем заполнение данных гражданина");

        clearAndSendKeys(lastNameInput, citizen.lastName());
        clearAndSendKeys(firstNameInput, citizen.firstName());
        clearAndSendKeys(middleNameInput, citizen.middleName());
        clearAndSendKeys(dateOfBirthInput, citizen.dateOfBirth());
        clearAndSendKeys(passportInput, citizen.passportNumber());
        clearAndSendKeys(genderInput, citizen.gender());
        clearAndSendKeys(addressInput, citizen.address());

        logger.info("Данные гражданина успешно заполнены");
        return this;
    }

    public CitizenPage fillLastName(String lastName) {
        clearAndSendKeys(lastNameInput, lastName);
        return this;
    }

    public CitizenPage fillName(String firstName) {
        clearAndSendKeys(firstNameInput, firstName);
        return this;
    }

    public CitizenPage fillMiddleName(String middleName) {
        clearAndSendKeys(middleNameInput, middleName);
        return this;
    }

    public CitizenPage fillDateOfBirth(String dateOfBirth) {
        clearAndSendKeys(dateOfBirthInput, dateOfBirth);
        return this;
    }

    public CitizenPage fillPassport(String passport) {
        clearAndSendKeys(passportInput, passport);
        return this;
    }

    public CitizenPage fillGender(String gender) {
       clearAndSendKeys(genderInput, gender);
       return this;
    }

    public CitizenPage fillAddress(String address) {
        clearAndSendKeys(addressInput, address);
        return this;
    }
    @Step("нажатие кнопки далее")
    public <T extends BasePage> T clickNextButton(Supplier<T> nextPage) {
        logger.info("Нажимаем кнопку 'Далее'");
        click(nextButton);
        logger.info("Кнопка 'Далее' успешна нажата");

        return nextPage.get();
    }
}
