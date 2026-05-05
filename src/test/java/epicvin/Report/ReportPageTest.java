package epicvin.Report;

import MD5hash.HashMD5;
import annotations.epicvin.EpicvinPaymentTest;
import annotations.epicvin.EpicvinTest;
import clearAccount.ClearAccount;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Account.SettingsPage;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static pages.epicvin.Report.ReportPage.*;

public class ReportPageTest extends base.BaseTest {

    @EpicvinPaymentTest
    public void sendReviewReportOneStar() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL12, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ReportPage reportPage = precheckPage.fullReportByCard(TEST,CARD_NUMBER, MONTH_YEAR, CVC, ZIP);
        reportPage.sendReviewOneStar(TEST);
        Assertions.assertFalse((titleReview).isDisplayed());
    }

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
    public void crashRecordsInReport() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        PrecheckPage precheckPage = mainPage.searchLotByVin(CRASHES_VIN);
        ReportPage reportPage = precheckPage.prepaidReport();
        reportPage.clickShowAllInAllHistoryEventTable();
        Assertions.assertTrue(reportPage.getTextFromVehicleHistoryTbl().contains("Crash record"));
    }

    @EpicvinTest
    public void emissionsInReport() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        PrecheckPage precheckPage = mainPage.searchLotByVin(EMISSIONS_VIN);
        ReportPage reportPage = precheckPage.prepaidReport();
        reportPage.clickShowAllInAllHistoryEventTable();
        Assertions.assertTrue(reportPage.getTextFromVehicleHistoryTbl().contains("Emissions and safety governments checks"));
    }

    @EpicvinTest
    public void servicesInReport() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        PrecheckPage precheckPage = mainPage.searchLotByVin(SERVICES_VIN);
        ReportPage reportPage = precheckPage.prepaidReport();
        reportPage.clickShowAllInAllHistoryEventTable();
        Assertions.assertTrue(reportPage.getTextFromVehicleHistoryTbl().contains("Vehicle serviced"));
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

    @EpicvinPaymentTest
    public void changeMeasurementSystem() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL7, VALID_PASSWORD);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        precheckPage.fullReportByCard(TEST,CARD_NUMBER, MONTH_YEAR, CVC, ZIP);
        ReportPage reportPage = new ReportPage();
        if (reportPage.epicVinReport().contains("EpicVIN vehicle history report for")) {
            SettingsPage settingsPage = precheckPage.clickSettings();
            settingsPage.clickKm();
            assertAll(
                    () -> assertEquals("km", reportPage.getKM(outlineKM)),
                    () -> assertEquals("km", reportPage.getKM(ownershipKM)),
                    () -> assertEquals("km", reportPage.getKM(estimatedMileageKM)),
                    () -> assertEquals("km", reportPage.getKM(averageMileageKM)),
                    () -> assertEquals("km", reportPage.getKM(odometerKM)),
                    () -> assertEquals("km", reportPage.getKM(currentTitleKM)),
                    () -> assertEquals("km", reportPage.getKM(historicalTitleKM)),
                    () -> assertEquals("km", reportPage.getKM(salesKM)),
                    () -> assertEquals("km", reportPage.getKM(priceChanges))
            );
        }
    }

    @EpicvinTest
    public void checkRepairSmithPartner() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        PrecheckPage precheckPage = mainPage.searchLotByVin(REPAIR_SMITH_VIN);
        ReportPage reportPage = precheckPage.prepaidReport();
        reportPage.clickShowAllInAllHistoryEventTable();
        Assertions.assertEquals("Mobile Car Repair, we come to you!", reportPage.getRepairSmithLink());
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
