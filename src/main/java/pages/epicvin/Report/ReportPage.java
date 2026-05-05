package pages.epicvin.Report;

import com.codeborne.selenide.SelenideElement;
import interfaces.Dispute;
import interfaces.Report;
import org.openqa.selenium.By;

import java.io.File;

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
    private final SelenideElement download = $(new By.ByLinkText("Download Report"));
    public static SelenideElement reviewServiceText = $x("//p[@class='review__share-call']");
    public static SelenideElement report = $("#report");

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

    public File downloadReport() {
        sleep(2000);
        File report = download.download();
        return report;
    }
}
