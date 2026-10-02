package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ApplicantPage extends  BasePage {

    @FindBy(css = "#TextInputField-1")
    private WebElement lastNameInput;

    @FindBy(css = "#TextInputField-2")
    private WebElement nameInput;

    @FindBy(css = "#TextInputField-3")
    private WebElement middleNameInput;

    @FindBy(css = "#TextInputField-4")
    private WebElement phoneNumberInput;

    @FindBy(css = "#TextInputField-5")
    private WebElement passportInput;

    @FindBy(css = "#TextInputField-6")
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

   public ApplicantPage fillAllTest(){
        return fillLastName("Сергеев").
        fillName("Олег").
        fillMiddlename("Викторович").
        fillPhone("+375672911256").
        fillPassport("12345123").
        fillAddress("г.Брест, ул.Московская 320");
   }
   public void clickNextButton() {
       wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
   }
   public void clickCloseButton(){
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
   }
}