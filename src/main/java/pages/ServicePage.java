package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ServicePage extends BasePage {

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

    public ServicePage(WebDriver driver) {
        super(driver);
    }


    public ServicePage fillDateOfRegistration(String dateOfRegistration) {
       clearAndSendKeys(dateOfRegistrationInput, dateOfRegistration);
       return this;
    }

    public ServicePage fillNewLastName(String newLastName) {
        clearAndSendKeys(newLastNameInput, newLastName);
        return this;
    }

    public ServicePage fillLastNameSpouse(String lastNameSpouse) {
        clearAndSendKeys(lastNameSpouseInput, lastNameSpouse);
        return this;
    }

    public ServicePage fillNameSpouse(String nameSpouse) {
        clearAndSendKeys(nameSpouseInput, nameSpouse);
        return this;
    }

    public ServicePage fillMiddleNameSpouse(String middleNameSpouse) {
        clearAndSendKeys(middleNameSpouseInput, middleNameSpouse);
        return this;
    }

    public ServicePage fillDateOfBirthSpouse(String dateOfBirthSpouse) {
       clearAndSendKeys(dateOfBirthSpouseInput,dateOfBirthSpouse);
       return this;
    }

    public ServicePage fillPassportSpouse(String passportSpouse) {
       clearAndSendKeys(passportSpouseInput, passportSpouse);
       return this;
    }

    public void clickCompleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(completeButton)).click();
    }

    public void clickBackButton() {
        wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
    }

    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
}
