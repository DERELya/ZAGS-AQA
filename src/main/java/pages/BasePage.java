package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AllureFailureExtension;
import utils.DriverManager;

@ExtendWith(AllureFailureExtension.class)
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Logger logger = LogManager.getLogger(getClass());

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
        PageFactory.initElements(driver, this);
    }

    protected void clearAndSendKeys(WebElement element, String value) {
        logger.info("Ввод данных в элемент: {}", element);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    protected void click(WebElement element) {
        logger.info("Нажатие элемента: {}", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
