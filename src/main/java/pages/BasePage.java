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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        logger.info("Ввод данных: {} в элемент: {}",value, element);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    protected void clearAndSendDate(WebElement element, String date) {
        logger.info("Ввод даты: {} в элемент: {}",date, element);
        String formattedDate = LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
        ).format(DateTimeFormatter.ISO_LOCAL_DATE);

        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(formattedDate);
    }

    protected void click(WebElement element) {
        logger.info("Нажатие элемента: {}", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
