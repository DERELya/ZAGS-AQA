package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CitizenPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//label[text()=\"Фамилия\"]/parent::div/following-sibling::input")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[text()=\"Имя\"]/parent::div/following-sibling::input")
    private WebElement nameLabel;

    @FindBy(xpath = "//label[text()=\"Отчество\"]/parent::div/following-sibling::input")
    private WebElement middlenameLabel;

    @FindBy(xpath = "//label[text()=\"Дата рождения\"]/parent::div/following-sibling::input")
    private WebElement dateOfBirthLabel;

    @FindBy(xpath = "//label[text()=\"Номер паспорта\"]/parent::div/following-sibling::input")
    private WebElement passportLabel;

    @FindBy(xpath = "//label[text()=\"Пол\"]/parent::div/following-sibling::input")
    private WebElement genderLabel;

    @FindBy(xpath = "//label[text()=\"Адрес прописки\"]/parent::div/following-sibling::input")
    private WebElement addressLabel;

    @FindBy(xpath = "//button[text()=\"Далее\"]")
    private WebElement nextButton;
    @FindBy(xpath = "//button[text()=\"Назад\"]")
    private WebElement backButton;
    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;


    public CitizenPage(WebDriver driver) {
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
