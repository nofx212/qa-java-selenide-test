package epicvin.Report;

import MD5hash.HashMD5;
import annotations.epicvin.EpicvinPaymentTest;
import annotations.epicvin.EpicvinTest;
import clearAccount.ClearAccount;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;
import pages.epicvin.SocialReviews.GooglePage;
import pages.epicvin.SocialReviews.TrustpilotPage;
import sql.SQLRequestsEpicvin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static constants.Constants.*;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class ReportPageTest extends base.BaseTest {

    @EpicvinPaymentTest
    public void sendReviewReportFiveStars() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL13, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ReportPage reportPage = precheckPage.fullReportByCard(TEST,CARD_NUMBER, MONTH_YEAR, CVC, ZIP);
        reportPage.sendReviewFiveStars();
        if (reportPage.getReviewServiceText().contains("Share your review on Trustpilot")) {
            reportPage.clickShareReview();
            Assertions.assertEquals(EPICVIN_TRUSTPILOT, new TrustpilotPage().currentUrl());
        } else {
            reportPage.clickShareReview();
            Assertions.assertTrue(new GooglePage().currentUrl().contains("https://accounts.google.com/"));
        }
    }

    @EpicvinTest
    public void downloadPDF() throws IOException {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ReportPage reportPage = precheckPage.prepaidReport();
        var report = reportPage.downloadReport();
        assertThat(readFileToString(report, StandardCharsets.UTF_8)).contains("Vehicle history report for 2020 FORD Edge");
    }

    @EpicvinTest
    public void checkCountImagesInReport() throws Exception {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL9, VALID_PASSWORD);
        String vin;
        try (ResultSet rs = SQLRequestsEpicvin.getValidVinFromDB()) {
            assertThat(rs.next())
                    .as("VIN not found in database")
                    .isTrue();
            vin = rs.getString("vin");
        }
        PrecheckPage precheckPage = mainPage.searchLotByVin(vin);
        assumeTrue(
                !precheckPage.precheckPageAll().contains("So sorry!"),
                "Skipping test because VIN not found"
        );
        precheckPage.prepaidReport();

        List<SQLRequestsEpicvin.SaleRecord> records = SQLRequestsEpicvin.getAllRecordsByVin(vin);
        if (records.isEmpty()) {
            System.out.println("No records found for VIN=" + vin);
            return;
        }
        for (SQLRequestsEpicvin.SaleRecord record : records) {
            String hash = new HashMD5().md5(record.id() + record.createdAt());

            SelenideElement saleRecord = $x("//li[contains(@class,'sale-record') and @data-hash='" + hash + "']")
                    .should(Condition.exist);

            int countImagesHtml = saleRecord.$$x(".//div[contains(@class,'sale-record__photo')]/img").size();

            System.out.printf(
                    "VIN=%s, hash=%s → DB: %d, HTML: %d%n",
                    vin, hash, record.countImages(), countImagesHtml
            );

            assertThat(countImagesHtml)
                    .as("Mismatch for VIN=%s and hash=%s", vin, hash)
                    .isEqualTo(record.countImages());
        }
    }
}
