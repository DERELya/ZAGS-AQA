package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ServicePage extends BasePage {

    @FindBy(css = "#TextInputField-14")
    private WebElement dateOfRegistrationInput;

    @FindBy(css = "#TextInputField-15")
    private WebElement newLastNameInput;

    @FindBy(css = "#TextInputField-16")
    private WebElement lastNameSpouseInput;

    @FindBy(css = "#TextInputField-17")
    private WebElement nameSpouseInput;

    @FindBy(css = "#TextInputField-18")
    private WebElement middleNameSpouseInput;

    @FindBy(css = "#TextInputField-19")
    private WebElement dateOfBirthSpouseInput;

    @FindBy(css = "#TextInputField-20")
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
