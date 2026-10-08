package pages.user;

import io.qameta.allure.Step;
import model.ApplicantData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ApplicantPage extends BasePage {

    @FindBy(xpath="//input[contains(@placeholder, \"фамилию\")]")
    private WebElement lastNameInput;

    @FindBy(xpath="//input[contains(@placeholder, \"имя\")]")
    private WebElement firstNameInput;

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


    public ApplicantPage() {
        super();
    }

    @Step("Заполнение данных заявителя")
    public ApplicantPage fillForm(ApplicantData applicant) {
        logger.info("Начинаем заполнение данных заявителя");

        clearAndSendKeys(lastNameInput, applicant.lastName());
        clearAndSendKeys(firstNameInput, applicant.firstName());
        clearAndSendKeys(middleNameInput, applicant.middleName());
        clearAndSendKeys(phoneNumberInput, applicant.phoneNumber());
        clearAndSendKeys(passportInput, applicant.passportNumber());
        clearAndSendKeys(addressInput, applicant.address());

        logger.info("Данные заявителя успешно заполнены");

        return this;
    }
    public ApplicantPage fillLastName(String lastName) {
        clearAndSendKeys(lastNameInput, lastName);
        return this;
    }

    public ApplicantPage fillName(String firstName) {
        clearAndSendKeys(firstNameInput, firstName);
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
    @Step("Нажатие кнопки далее")
   public ChooseServicePage clickNextButton() {
        logger.info("Нажимаем кнопку 'Далее'");
        click(nextButton);
        logger.info("Кнопка 'Далее' успешна нажата");
       return new  ChooseServicePage();
   }
   public void clickCloseButton(){
        click(closeButton);
   }
}