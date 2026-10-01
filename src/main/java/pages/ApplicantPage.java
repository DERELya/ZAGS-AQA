package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ApplicantPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//label[text()=\"Фамилия\"]/parent::div/following-sibling::input")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[text()=\"Имя\"]/parent::div/following-sibling::input")
    private WebElement nameInput;

    @FindBy(xpath = "//label[text()=\"Отчество\"]/parent::div/following-sibling::input")
    private WebElement middleNameInput;

    @FindBy(xpath = "//label[text()=\"Телефон\"]/parent::div/following-sibling::input")
    private WebElement phoneNumberInput;

    @FindBy(xpath = "//label[text()=\"Номер паспорта\"]/parent::div/following-sibling::input")
    private WebElement passportInput;

    @FindBy(xpath = "//label[text()=\"Адрес прописки\"]/parent::div/following-sibling::input")
    private WebElement addressInput;

    @FindBy(xpath = "//button[text()=\"Далее\"]")
    private WebElement nextButton;

    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;


    public ApplicantPage(WebDriver driver) {
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