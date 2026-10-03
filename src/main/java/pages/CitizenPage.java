package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CitizenPage extends  BasePage {

    @FindBy(xpath = "//input[@id=//label[text()=\"Фамилия\"]/@for]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Имя\"]/@for]")
    private WebElement nameLabel;

    @FindBy(xpath = "//input[@id=//label[text()=\"Отчество\"]/@for]")
    private WebElement middlenameLabel;

    @FindBy(xpath = "//input[@id=//label[text()=\"Дата рождения\"]/@for]")
    private WebElement dateOfBirthLabel;

    @FindBy(xpath = "//input[@id=//label[text()=\"Номер паспорта\"]/@for]")
    private WebElement passportLabel;

    @FindBy(xpath = "//input[@id=//label[text()=\"Пол\"]/@for]")
    private WebElement genderLabel;

    @FindBy(xpath = "//input[@id=//label[text()=\"Адрес прописки\"]/@for]")
    private WebElement addressLabel;

    @FindBy(xpath = "//button[text()=\"Далее\"]")
    private WebElement nextButton;

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

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }
}
