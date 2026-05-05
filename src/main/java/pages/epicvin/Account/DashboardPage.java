package pages.epicvin.Account;

import com.codeborne.selenide.SelenideElement;
import interfaces.Dispute;
import org.openqa.selenium.By;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Price.PricePage;
import pages.epicvin.Report.ReportPage;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage implements Dispute {

    private final SelenideElement email = $x("//span[@class='user-info__email']");
    public static SelenideElement titleH1 = $x("//h1[contains(@class,'profile__title')]");
    public static SelenideElement block1 = $x("//div[@class='profile__sections row']//section[1]//h2");
    public static SelenideElement block2 = $x("//div[@class='profile__sections row']//section[2]//h2");
    public static SelenideElement block3 = $x("//section[@class='save-banner']//h2");
    public static SelenideElement prepaid = $x("//div[@class='report-info']//span[2]");
    private final SelenideElement inputVin = $(By.id("vin-inputnot-found"));
    private final SelenideElement btnBuyMore = $(By.partialLinkText("Buy More"));
    private final SelenideElement success = $x("//div[contains(@class,'alert-text')]");
    private final SelenideElement tooltip = $x("//button[@class='btn-reset']");
    private final SelenideElement tooltipText = $x("//p[contains(@class,'report-tooltip')]");
    private final SelenideElement tooltipLink = $x("//p[contains(@class,'report-tooltip')]//a");
    private final SelenideElement lastReportsBlock = $x("//div[@class='profile__sections row']//section[2]//h2");
    private final SelenideElement openFirstReport = $x("//div[@class='profile__sections row']//section[2]//div//div[1]//a");

    public String getAccountEmail() {
        return email.getText();
    }

    public String dashboardBlock(SelenideElement block) {
        return block.getText();
    }

    public PrecheckPage setInputVin(String vin) {
        inputVin.setValue(vin);
        return new PrecheckPage();
    }

    public PricePage clickBuyMore() {
        btnBuyMore.click();
        return new PricePage();
    }

    public String getSuccess() {
        return success.getText();
    }

    public void openTooltip() {
        tooltip.hover();
        if (tooltipText.getText().contains("To utilize your prepaid reports")) {
            tooltipLink.click();
        }
    }

    public ReportPage openReport() {
        if (lastReportsBlock.getText().contains("Last Reports")) {
            openFirstReport.click();
        }
        return new ReportPage();
    }
}
