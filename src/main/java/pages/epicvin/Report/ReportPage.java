package pages.epicvin.Report;

import com.codeborne.selenide.SelenideElement;
import interfaces.Dispute;
import interfaces.Report;
import org.openqa.selenium.By;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ReportPage extends SampleVinPage implements Report, Dispute {
    private final SelenideElement epicvinReportText = $x("//p[contains(@class,'sub-title')]");
    private final SelenideElement textReview = $x("//textarea[contains(@class,'msg-field')]");
    public static SelenideElement titleReview = $x("//section[@id='review']//h2");
    private final SelenideElement unlimBanner = $x("//div[@id='unlim-modal']//div");
    private final SelenideElement notice = $x("//a[@class='report__notice']");
    private final SelenideElement messageSend = $(By.id("alert-notice"));
    private final SelenideElement closeFullUnlim = $x("//div[@id='unlim-modal']//button[@aria-label='Close']");
    private final SelenideElement vehicleHistoryTblText = $x("//table[contains(@class,'report__table')]");
    private final SelenideElement btnShowAll = $x("//section[@id='historyEvents']//div[@class='report__table-show']//button//span[1]");
    private final SelenideElement download = $(new By.ByLinkText("Download Report"));
    public static SelenideElement outlineKM = $x("//section[@id='outline']//li[4]//span");
    public static SelenideElement ownershipKM = $x("//div[@class='owner-card__spec-item']//dd");
    public static SelenideElement estimatedMileageKM = $x("//div[@class='report__average-list']//div//p");
    public static SelenideElement averageMileageKM = $x("//div[@class='report__average-list']//div[2]//p");
    public static SelenideElement odometerKM = $x("//div[contains(@class,'report__table')]//tr[2]//td[contains(@class,'table-odometer-cell')]//p");
    public static SelenideElement currentTitleKM = $x("//section[@id='titles']//div[3]//div[1]//td[3]//p[2]");
    public static SelenideElement historicalTitleKM = $x("//section[@id='titles']//div[3]//div[2]//td[3]//p[2]");
    public static SelenideElement salesKM = $x("//div[contains(@class,'spec-item--odometer')]//dd");
    public static SelenideElement priceChanges = $x("//section[@id='price-changes']//div[2]//tbody//tr//td[3]//p[2]");
    public static SelenideElement reviewServiceText = $x("//p[@class='review__share-call']");
    public static SelenideElement report = $("#report");
    private final SelenideElement repairSmithLink = $x("//div[@class='report__table-service']//p[2]");

    public String epicVinReport() {
        return epicvinReportText.getText();
    }

    public void scrollToReviewIsBlock() {
        executeJavaScript("arguments[0].scrollIntoView();", titleReview);
        sleep(2000);
        if (unlimBanner.shouldBe(visible).isDisplayed()) {
            sleep(2000);
            closeUnlimBanner();
        }else {
            System.out.println("Unlim banner is not displayed");
        }
    }

    private void closeUnlimBanner() {
        executeJavaScript("arguments[0].click()", closeFullUnlim);
    }

    @Override
    public void setTextReview(String test) {
        textReview.setValue(test);
    }

    public String textReportReview() {
        return titleReview.getText();
    }

    @Override
    public void sendReviewOneStar(String test) {
        scrollToReviewIsBlock();
        sleep(1000);
        clickOneStar();
        setTextReview(test);
        clickBtnSend();
        modalReviewDisplayed();
    }

    public ReportInaccuracyPage clickNotice() {
        notice.click();
        switchTo().window(1);
        return new ReportInaccuracyPage();
    }

    public String messageSuccessfullySend() {
        return messageSend.getText();
    }

    public void sendReviewFiveStars() {
        scrollToReviewIsBlock();
        sleep(1000);
        clickFiveStars();
    }

    public String getReviewServiceText() {
        return reviewServiceText.getText();
    }

    public void clickShowAllInAllHistoryEventTable() {
        try {
            report.shouldBe(visible, Duration.ofSeconds(100));
            if (report.isDisplayed()) {
                System.out.println("report displayed");
                executeJavaScript("arguments[0].click();", btnShowAll);
                System.out.println("clicked show all");
                sleep(5000);
            }else{
                System.out.println("report not displayed");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public String getTextFromVehicleHistoryTbl() {
        return vehicleHistoryTblText.getText();
    }

    public File downloadReport() {
        sleep(2000);
        File report = download.download();
        return report;
    }

    public String getKM(SelenideElement element) {
        return element.getText().replaceAll("\\P{L}", "");
    }

    public String getRepairSmithLink() {
        return repairSmithLink.getText();
    }
}
