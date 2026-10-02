package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CitizenPage extends  BasePage {

    @FindBy(css = "#TextInputField-7")
    private WebElement lastNameInput;

    @FindBy(css = "#TextInputField-8")
    private WebElement nameLabel;

    @FindBy(css = "#TextInputField-9")
    private WebElement middlenameLabel;

    @FindBy(css = "#TextInputField-10")
    private WebElement dateOfBirthLabel;

    @FindBy(css = "#TextInputField-11")
    private WebElement passportLabel;

    @FindBy(css = "#TextInputField-12")
    private WebElement genderLabel;

    @FindBy(css = "#TextInputField-13")
    private WebElement addressLabel;

    @FindBy(xpath = "//button[text()=\"Далее\"]")
    private WebElement nextButton;
    @FindBy(xpath = "//button[text()=\"Назад\"]")
    private WebElement backButton;
    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;


    public CitizenPage(WebDriver driver) {
       super(driver);
    }

    public CitizenPage fillLastName(String lastName) {
        clearAndSendKeys(lastNameInput, lastName);
        return this;
    }

    public CitizenPage fillName(String firstName) {
        clearAndSendKeys(nameLabel, firstName);
        return this;
    }

    public CitizenPage fillMiddlename(String middlename) {
        clearAndSendKeys(middlenameLabel, middlename);
        return this;
    }

    public CitizenPage fillDateOfBirth(String dateOfBirth) {
        clearAndSendKeys(dateOfBirthLabel, dateOfBirth);
        return this;
    }

    public CitizenPage fillPassport(String passport) {
        clearAndSendKeys(passportLabel, passport);
        return this;
    }

    public CitizenPage fillGender(String gender) {
       clearAndSendKeys(genderLabel, gender);
       return this;
    }

    public CitizenPage fillAddress(String address) {
        clearAndSendKeys(addressLabel, address);
        return this;
    }

    public CitizenPage fillAllTest(){
        return fillLastName("Сергеев").
        fillName("Олег").
        fillMiddlename("Викторович").
        fillDateOfBirth("12.09.2005").
        fillPassport("12345123").
        fillGender("Муж").
        fillAddress("г.Брест, ул.Московская 320");
    }

    public ServicePage clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        return new ServicePage(driver);
    }

    public void clickBackButton() {
        wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
    }

    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }


}
