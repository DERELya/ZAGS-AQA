package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChooseServicePage extends BasePage {

    public ChooseServicePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[text()=\"Регистрация брака\"]")
    private WebElement buttonWedding;

    public ChooseServicePage clickButtonWedding() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonWedding)).click();
        return this;
    }
}
