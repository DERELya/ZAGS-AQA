package pages.user;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ChooseServicePage extends BasePage {

    public ChooseServicePage() {
        super();
    }

    @FindBy(xpath = "//button[text()=\"Регистрация брака\"]")
    private WebElement buttonWedding;

    @FindBy(xpath = "//button[text()=\"Регистрация рождения\"]")
    private WebElement buttonBirth;

    @FindBy(xpath = "//button[text()=\"Регистрация смерти\"]")
    private WebElement buttonDeath;

    public CitizenPage clickButtonWedding() {
        click(buttonWedding);
        return new CitizenPage();
    }

    public CitizenPage clickButtonBirth() {
        click(buttonBirth);
        return new CitizenPage();
    }

    public CitizenPage clickButtonDeath() {
        click(buttonDeath);
        return new CitizenPage();
    }
}
