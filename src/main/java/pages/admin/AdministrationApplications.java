package pages.admin;

import element.TableElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class AdministrationApplications extends BasePage {
    public AdministrationApplications() {super();}

    @FindBy(xpath = "//table")
    private WebElement applicationsTable;

    public TableElement getTable() {
        return new TableElement(applicationsTable);
    }

    public String getStatusByApplicationNumber(String appNumber) {
        WebElement row = getTable().getRowByText(appNumber);
        return row.findElements(By.xpath("./td")).get(4).getText().trim();
    }
}
