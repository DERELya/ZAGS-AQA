package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ApplicantPage extends  BasePage {

    @FindBy(xpath="//input[contains(@placeholder, \"фамилию\")]")
    private WebElement lastNameInput;

    @FindBy(xpath="//input[contains(@placeholder, \"имя\")]")
    private WebElement nameInput;

    @FindBy(xpath="//input[contains(@placeholder, \"отчество\")]")
    private WebElement middleNameInput;

    @FindBy(xpath="//input[contains(@placeholder, \"телефона\")]")
    private WebElement phoneNumberInput;

    @FindBy(xpath="//input[contains(@placeholder, \"паспорта\")]")
    private WebElement passportInput;

    @FindBy(xpath="//input[contains(@placeholder, \"прописки\")]")
    private WebElement addressInput;

    @FindBy(xpath = "//button[text()=\"Далее\"]")
    private WebElement nextButton;

    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;


    public ApplicantPage(WebDriver driver) {
        super(driver);
    }


    public ApplicantPage fillLastName(String lastName) {
        clearAndSendKeys(lastNameInput, lastName);
        return this;
    }

    public ApplicantPage fillName(String firstName) {
        clearAndSendKeys(nameInput, firstName);
        return this;
    }

    public ApplicantPage fillMiddlename(String middlename) {
        clearAndSendKeys(middleNameInput, middlename);
        return this;
    }

    public ApplicantPage fillPhone(String phone) {
        clearAndSendKeys(phoneNumberInput, phone);
        return this;
    }

    public ApplicantPage fillPassport(String passport) {
        clearAndSendKeys(passportInput, passport);
        return this;
    }

   public ApplicantPage fillAddress(String address) {
        clearAndSendKeys(addressInput, address);
        return this;
   }
   public void clickNextButton() {
       wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
   }
   public void clickCloseButton(){
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
   }
}