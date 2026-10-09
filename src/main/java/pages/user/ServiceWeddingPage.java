package pages.user;

import model.WeddingData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;


public class ServiceWeddingPage extends BasePage {

    @FindBy(xpath = "//input[@id=//label[text()=\"Дата регистрации\"]/@for]")
    private WebElement dateOfRegistrationInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Новая фамилия\"]/@for]")
    private WebElement newLastNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Фамилия супруга/и\"]/@for]")
    private WebElement lastNameSpouseInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Имя супруга/и\"]/@for]")
    private WebElement nameSpouseInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Отчество супруга/и\"]/@for]")
    private WebElement middleNameSpouseInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Дата рождения супруга/и\"]/@for]")
    private WebElement dateOfBirthSpouseInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Номер паспорта супруга/и\"]/@for]")
    private WebElement passportSpouseInput;

    @FindBy(xpath = "//button[text()=\"Завершить\"]")
    private WebElement completeButton;
    @FindBy(xpath = "//button[text()=\"Назад\"]")
    private WebElement backButton;
    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;

    public ServiceWeddingPage() {
        super();
    }


    public ServiceWeddingPage fillForm(WeddingData weddingData) {
        clearAndSendKeys(dateOfRegistrationInput, weddingData.dateOfRegistration());
        clearAndSendKeys(newLastNameInput, weddingData.newLastName());
        clearAndSendKeys(lastNameSpouseInput, weddingData.lastNameSpouse());
        clearAndSendKeys(nameSpouseInput, weddingData.nameSpouse());
        clearAndSendKeys(middleNameSpouseInput, weddingData.middleNameSpouse());
        clearAndSendKeys(dateOfBirthSpouseInput, weddingData.dateOfBirthSpouse());
        clearAndSendKeys(passportSpouseInput, weddingData.passportSpouse());
        return this;
    }

    public ServiceWeddingPage fillDateOfRegistration(String dateOfRegistration) {
       clearAndSendKeys(dateOfRegistrationInput, dateOfRegistration);
       return this;
    }

    public ServiceWeddingPage fillNewLastName(String newLastName) {
        clearAndSendKeys(newLastNameInput, newLastName);
        return this;
    }

    public ServiceWeddingPage fillLastNameSpouse(String lastNameSpouse) {
        clearAndSendKeys(lastNameSpouseInput, lastNameSpouse);
        return this;
    }

    public ServiceWeddingPage fillNameSpouse(String nameSpouse) {
        clearAndSendKeys(nameSpouseInput, nameSpouse);
        return this;
    }

    public ServiceWeddingPage fillMiddleNameSpouse(String middleNameSpouse) {
        clearAndSendKeys(middleNameSpouseInput, middleNameSpouse);
        return this;
    }

    public ServiceWeddingPage fillDateOfBirthSpouse(String dateOfBirthSpouse) {
       clearAndSendKeys(dateOfBirthSpouseInput,dateOfBirthSpouse);
       return this;
    }

    public ServiceWeddingPage fillPassportSpouse(String passportSpouse) {
       clearAndSendKeys(passportSpouseInput, passportSpouse);
       return this;
    }

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
