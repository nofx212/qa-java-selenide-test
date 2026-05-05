package pages.epicvin.Account;

import com.codeborne.selenide.SelenideElement;
import base.BaseMyReportsPage;
import org.openqa.selenium.By;
import pages.epicvin.Report.ReportPage;

import static com.codeborne.selenide.Selenide.*;

public class MyReportsPage extends BaseMyReportsPage {

    private final SelenideElement view = $(By.linkText("View"));
    private final SelenideElement page5 = $x("//ul[contains(@class,'pagination')]//li[6]//a");

    public ReportPage clickViewReport() {
        view.click();
        return new ReportPage();
    }

    public String clickPage5() {
        var href = page5.getAttribute("href");
        executeJavaScript("arguments[0].click()", page5);
        return href;
    }
}
