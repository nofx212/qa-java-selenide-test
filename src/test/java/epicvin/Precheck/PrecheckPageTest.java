package epicvin.Precheck;

import annotations.epicvin.EpicvinPaymentTest;
import annotations.epicvin.EpicvinTest;
import annotations.epicvin.prechek.EpicvinFullReportTest;
import clearAccount.*;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.PaymentProcesses.FullReportProcessorPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;
import pages.epicvin.Report.SampleVinPage;
import sql.SQLRequestsEpicvin;

import java.sql.ResultSet;

import static com.codeborne.selenide.Selenide.*;
import static constants.Constants.*;
import static interfaces.Sample.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static pages.epicvin.Precheck.PrecheckPage.*;

public class PrecheckPageTest extends base.BaseTest {

    @EpicvinFullReportTest
    public void purchaseFullReport(String paymentMethod) {
        MainPage mainPage = new MainPage();
        if (!paymentMethod.equals("unloggedUser")) {
            LoginPage loginPage = mainPage.clickLoginButton();
            loginPage.login(VALID_EMAIL4, VALID_PASSWORD);
        }
        if (paymentMethod.equals("fullReportByFunds")) {
            ClearAccount.addFunds(ADD_FUNDS);
        }
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        FullReportProcessorPage fullReportProcessorPage = new FullReportProcessorPage(precheckPage);
        ReportPage reportPage = fullReportProcessorPage.processPayment(paymentMethod);
        var precheckFullPrice = precheckPage.getFullPrice();
        SampleVinPage sampleVinPage = new SampleVinPage();
        assertAll(
                () -> assertEquals("EpicVIN vehicle history report for", sampleVinPage.epicVinTitleText()),
                () -> assertEquals("Ownership history", sampleVinPage.reportBlock(titleOwnershipHistory)),
                () -> assertEquals("Vehicle specifications", sampleVinPage.reportBlock(titleVehicleSpecifications)),
                () -> assertEquals("All history events", sampleVinPage.reportBlock(titleAllHistoryEvents)),
                () -> assertEquals("Safety recall check", sampleVinPage.reportBlock(titleRecallCheck)),
                () -> assertEquals("Stolen vehicle check", sampleVinPage.reportBlock(titleStolenCheck)),
                () -> assertEquals("Junk / Salvage / Insurance records", sampleVinPage.reportBlock(titleJunkSalvage)),
                () -> assertEquals("Odometer check", sampleVinPage.getTitleOdometerCheck()),
                () -> assertEquals("Title history information", sampleVinPage.reportBlock(titleHistoryInformation)),
                () -> assertEquals("Major title brand check", sampleVinPage.reportBlock(titleBrandMajorCheck)),
                () -> assertEquals("Other title brand check", sampleVinPage.reportBlock(titleBrandOtherCheck)),
                () -> assertEquals("Vehicle damages", sampleVinPage.reportBlock(titleVehicleDamages)),
                () -> assertEquals("Sales history", sampleVinPage.reportBlock(titleSalesHistory)),
                () -> assertEquals("Market price analysis", sampleVinPage.reportBlock(titleMarketPrice)),
                () -> assertEquals("Give feedback", reportPage.textReportReview())
        );
        if (!paymentMethod.equals("paypal")) {
            Assertions.assertEquals("24.99", precheckFullPrice);
        }
        if (paymentMethod.equals("card")) {
            System.out.println("CLEAR BECAUSE PAYMENT METHOD IS CARD");
            new ClearPaymentMethod(PAYMENT_METHOD_EPICVIN);
        }
    }

    @EpicvinPaymentTest
    public void purchaseWithDispute() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL16, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ClearAccount.disputePrecheckPage(ADD_DISPUTE_EPICVIN);
        open(BASE_URL_EPICVIN_PRECHECK);
        precheckPage.fullReportByCard(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP);
        Assertions.assertEquals("Your Account Temporarily Limited", precheckPage.getModalDispute());
        ClearAccount.disputeMainPageEpicvin(CLOSE_DISPUTE_EPICVIN);
    }

    @EpicvinTest
    public void checkBrokenImg() throws Exception {
        ResultSet resultSet = SQLRequestsEpicvin.getValidVinFromDB();

        assumeTrue(resultSet.next(), "Skipping test because VIN not found in database");

        String vin = resultSet.getString("vin");
        System.out.println("VIN: " + vin);

        MainPage mainPage = new MainPage();
        PrecheckPage precheckPage = mainPage.searchLotByVin(vin);

        assumeTrue(
                !precheckPage.precheckPageAll().contains("So sorry!"),
                "Skipping test - vin not found page is displayed"
        );

        boolean validContentType = precheckPage.checkImageByContentType(imgURL);
        assertThat(validContentType)
                .as("Image is broken for VIN=" + vin)
                .isTrue();
    }
}
