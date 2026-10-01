package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ServicePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//label[text()=\"Дата регистрации\"]/parent::div/following-sibling::input")
    private WebElement dateOfRegistrationInput;

    @FindBy(xpath = "//label[text()=\"Новая фамилия\"]/parent::div/following-sibling::input")
    private WebElement newLastNameInput;

    @FindBy(xpath = "//label[text()=\"Фамилия супруга/и\"]/parent::div/following-sibling::input")
    private WebElement lastNameSpouseInput;

    @FindBy(xpath = "//label[text()=\"Имя супруга/и\"]/parent::div/following-sibling::input")
    private WebElement nameSpouseInput;

    @FindBy(xpath = "//label[text()=\"Отчество супруга/и\"]/parent::div/following-sibling::input")
    private WebElement middleNameSpouseInput;

    @FindBy(xpath = "//label[text()=\"Дата рождения супруга/и\"]/parent::div/following-sibling::input")
    private WebElement dateOfBirthSpouseInput;

    @FindBy(xpath = "//label[text()=\"Номер паспорта супруга/и\"]/parent::div/following-sibling::input")
    private WebElement passportSpouseInput;

    @FindBy(xpath = "//button[text()=\"Завершить\"]")
    private WebElement completeButton;
    @FindBy(xpath = "//button[text()=\"Назад\"]")
    private WebElement backButton;
    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;

    public ServicePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void clearAndSendKeys(WebElement element, String value) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
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

    public ServicePage fillAllTest(){
        return fillDateOfRegistration("26.09.2026")
                .fillNewLastName("Петрова")
                .fillLastNameSpouse("Сергеев")
                .fillNameSpouse("Олег")
                .fillMiddleNameSpouse("Викторович")
                .fillDateOfBirthSpouse("12.06.2000")
                .fillPassportSpouse("12784352617");
    }

    public void clickCompleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(completeButton)).click();
    }

    public CitizenPage clickBackButton() {
        wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
        return new CitizenPage(driver);
    }

    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
}
