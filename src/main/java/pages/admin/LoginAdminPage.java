package pages.admin;

import model.AdminData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.user.ChooseServicePage;

public class LoginAdminPage extends BasePage {
    @FindBy(xpath = "//input[@id=//label[text()=\"Фамилия\"]/@for]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Имя\"]/@for]")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Отчество\"]/@for]")
    private WebElement middleNameInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Телефон\"]/@for]")
    private WebElement phoneNumberInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Номер паспорта\"]/@for]")
    private WebElement passportInput;

    @FindBy(xpath = "//input[@id=//label[text()=\"Дата рождения\"]/@for]")
    private WebElement dateOfBirthInput;

    @FindBy(xpath = "//button[text()=\"Далее\"]")
    private WebElement nextButton;

    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;

    public LoginAdminPage() {
        super();
    }

    public LoginAdminPage fillForm(AdminData admin) {
        clearAndSendKeys(lastNameInput, admin.lastName());
        clearAndSendKeys(firstNameInput, admin.firstName());
        clearAndSendKeys(middleNameInput, admin.middleName());
        clearAndSendKeys(phoneNumberInput, admin.phoneNumber());
        clearAndSendKeys(passportInput, admin.passport());
        clearAndSendKeys(dateOfBirthInput, admin.dateOfBirth());
        return this;
    }

    public LoginAdminPage fillLastName(String lastName) {
        clearAndSendKeys(lastNameInput, lastName);
        return this;
    }

    public LoginAdminPage fillFirstName(String firstName) {
        clearAndSendKeys(firstNameInput, firstName);
        return this;
    }

    public LoginAdminPage fillMiddleName(String middleName) {
        clearAndSendKeys(middleNameInput, middleName);
        return this;
    }

    public LoginAdminPage fillPhoneNumber(String phoneNumber) {
        clearAndSendKeys(phoneNumberInput, phoneNumber);
        return this;
    }

    public LoginAdminPage fillPassport(String passport) {
        clearAndSendKeys(passportInput, passport);
        return this;
    }

    public LoginAdminPage fillDateOfBirth(String dateOfBirth) {
        clearAndSendKeys(dateOfBirthInput, dateOfBirth);
        return this;
    }


    public AdministrationApplications clickNextButton() {
        click(nextButton);
        return new AdministrationApplications();
    }

    public void clickCloseButton() {
        click(closeButton);
    }
}
