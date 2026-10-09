package element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.util.List;

public class TableElement {

    private final WebElement tableElement;
    private final WebDriverWait wait;

    public TableElement(WebElement tableElement) {
        this.tableElement = tableElement;
        this.wait = DriverManager.getWait();
    }

    public List<WebElement> getRows() {
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableElement, By.xpath(".//tr[td]")));
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tbody/tr"));
        if (rows.isEmpty()) {
            rows = tableElement.findElements(By.xpath(".//tr[td]"));
        }
        return rows;
    }

    public WebElement getRowByText(String expectedText) {
        for (WebElement row : getRows()) {
            if (row.getText().contains(expectedText)) {
                return row;
            }
        }
        throw new RuntimeException("Строка с текстом '" + expectedText + "' не найдена в таблице!");
    }

}
