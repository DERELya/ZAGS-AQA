package pages.user;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatusPage extends BasePage {
    public StatusPage() {
        super();
    }

    @FindBy(xpath = "//span[contains(text(), 'Ваша заявка')]")
    private WebElement applicationNumberMessage;

    @FindBy(xpath = "//button[text()=\"Закрыть\"]")
    private WebElement closeButton;

    @Step("Проверка соответствия текста")
    public boolean isSuccessMessageDisplayed(String expectedText) {
        logger.info("Проверяем наличие сообщения: '{}'", expectedText);
        try {
            By locator = By.xpath("//span[contains(text(), '" + expectedText + "')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.warn("Сообщение '{}' не найдено за отведённое время", expectedText);
            return false;
        }
    }


    public String getApplicationNumberMessage() {
        wait.until(ExpectedConditions.visibilityOf(applicationNumberMessage));

        wait.until(driver ->
                applicationNumberMessage.getText().matches(".*№\\s*\\d+.*")
        );
        String text = applicationNumberMessage.getText();
        logger.info("Получен текст сообщения: '{}'", text);

        Pattern pattern = Pattern.compile("№\\s*(\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String appNumber = matcher.group(1);

            logger.info("Номер заявки успешно получен: {}", appNumber);

            return appNumber;
        }
        logger.error("Не удалось найти номер заявки в тексте: '{}'", text);

        throw new IllegalStateException(
                "Не удалось найти номер заявки в тексте: " + text
        );
    }

    @Step("нажатие кнопки закрыть")
    public void clickCloseButton() {
        logger.info("Нажимаем кнопку закрыть");
        click(closeButton);
        logger.info("Кнопка 'Закрыть' успешно нажата");
    }
}
