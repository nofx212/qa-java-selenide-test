package epicvin.Precheck;

import annotations.epicvin.EpicvinPaymentTest;
import annotations.epicvin.EpicvinTest;
import annotations.epicvin.prechek.EpicvinFullReportTest;
import annotations.epicvin.prechek.EpicvinSubTest;
import annotations.epicvin.prechek.EpicvinTrialTest;
import clearAccount.*;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Account.SubscriptionsPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.PaymentProcesses.FullReportProcessorPage;
import pages.epicvin.PaymentProcesses.TrialProcessorPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;
import pages.epicvin.Report.SampleVinPage;
import pages.epicvin.SubscriptionProcesses.SubscriptionProcessorPage;
import sql.SQLRequestsEpicvin;

import java.sql.ResultSet;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
import static constants.Constants.*;
import static interfaces.Sample.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static pages.epicvin.Precheck.PrecheckPage.*;

public class PrecheckPageTest extends base.BaseTest {

    /**
     * Этот параметризованный тест проверяет, что полный отчет корректно отображается
     * для каждого метода оплаты ("card", "paypal", "prepaid", "unloggedUser", "fullReportByFunds").
     * Для этой проверки создан отдельный класс — FullReportProcessorPage,
     * в котором реализована логика вызова методов оплаты.
     */
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

    @EpicvinSubTest
    public void purchaseSubscription(String subscription) {
        MainPage mainPage = new MainPage();
        Set<String> unloggedUser = Set.of(
                "trialUnloggedUser",
                "trialUnloggedUserWithInvalidEmail",
                "trialUnloggedUserWithInvalidCVV",
                "trialUnloggedUserWithInvalidExpDate",
                "trialUnloggedUserWithEmptyName",
                "trialUnloggedUserWithLongPostalCode",
                "fullUnloggedUser"
        );
        if (!unloggedUser.contains(subscription)) {
            System.out.println("Logging in for subscription: " + subscription);
            LoginPage loginPage = mainPage.clickLoginButton();
            loginPage.login(VALID_EMAIL8, VALID_PASSWORD);
            ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        } else {
            System.out.println("Skipping login for unlogged subscription: " + subscription);
        }
        if (subscription.equals("fullByFunds")) {
            ClearAccount.addFunds(ADD_FUNDS);
        }
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        SubscriptionProcessorPage subscriptionProcessorPage = new SubscriptionProcessorPage(precheckPage);
        ReportPage reportPage = subscriptionProcessorPage.subscribe(subscription);

        var price = precheckPage.getFullPrice();
        System.out.println("price precheck " + price);

        switch (subscription) {
            case "trialsWithPrepaidCard" ->
                    Assertions.assertEquals("We’re sorry, but we can’t accept prepaid cards. Please try another card to complete your payment.", precheckPage.getErrorCard());
            case "trialWithMaestroCard" ->
                    Assertions.assertEquals("Unfortunately, we do not accept Maestro cards. Please use a different payment card.", precheckPage.getErrorCard());
            case "trialUnloggedUserWithInvalidEmail" ->
                    Assertions.assertEquals("Typo in email? Did you mean test.777@gmx.com?", precheckPage.getEmailValidation());
            case "trialWithFail3Ds" ->
                    Assertions.assertEquals("Payment was not completed.", precheckPage.getErrorCard());
            case "trialUnloggedUserWithInvalidCVV" ->
                    Assertions.assertEquals("Invalid CVV code", precheckPage.getCVVValidation());
            case "trialUnloggedUserWithInvalidExpDate" ->
                    Assertions.assertEquals("Invalid expiration date", precheckPage.getDateValidation());
            case "trialUnloggedUserWithEmptyName" ->
                    Assertions.assertEquals("Please enter the cardholder name", precheckPage.getNameValidation());
            case "trialUnloggedUserWithLongPostalCode" ->
                    Assertions.assertTrue(precheckPage.getErrorZip().contains("ZIP/postal code must not exceed 16 characters"));
            default -> {
                if (reportPage.epicVinReport().contains("EpicVIN vehicle history report for")) {
                    System.out.println("report");
                    mainPage.subscriptions(SubscriptionsPage.class);
                }
                SubscriptionsPage subscriptionsPage = new SubscriptionsPage();

                switch (subscription) {
                    case "trial", "trialUnloggedUser", "trialWith3Ds" ->
                            Assertions.assertEquals("FULL HISTORY UNLIM - Trial", subscriptionsPage.getTrialTitle());
                    default -> {
                        var subPrice = subscriptionsPage.getActualSubPrice();
                        System.out.println("price subscription " + subPrice);
                        Assertions.assertEquals(price, subPrice);
                        subscriptionsPage.cancelAndResumeAnnualSub();
                        subscriptionsPage.cancelNowSubscription();
                    }
                }
                ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
            }
        }
    }

    /**
     * Этот параметризованный тест проверяет, что пробную подписку можно приобрести только один раз,
     * независимо от учетной записи пользователя, при использовании способов оплаты "card" и "paypal".
     * Для этой проверки создан отдельный класс — TrialProcessorPage,
     * в котором реализована логика вызова платежных методов.
     */
    @EpicvinTrialTest
    public void checkTrialSubOnlyOnce(String paymentMethod) {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL11, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        TrialProcessorPage trialProcessorPage = new TrialProcessorPage(precheckPage);
        ReportPage reportPage = trialProcessorPage.processPayment(paymentMethod);
        sleep(5000);
        if ("paypal".equals(paymentMethod)) {
            System.out.println("Pay with PayPal");
            new MainPage();
        }
        reportPage.clickLogout();
        mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL12, VALID_PASSWORD);
        ClearAccount.clearTrialMainPage(TRIAL_SUB_EPICVIN);
        mainPage.searchLotByVin(VALID_VIN);
        trialProcessorPage.processPayment(paymentMethod);
        Assertions.assertEquals("You can have trial subscription only once", precheckPage.getErrorCard());
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
    public void fundsNotDisplayForTrial() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL17, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        mainPage.searchLotByVin(VALID_VIN);
        Assertions.assertFalse((btnFundsInsufficient).isDisplayed());
    }

    @EpicvinTest
    public void fundsIsDisabled() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL17, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        precheckPage.clickFirstPack();
        Assertions.assertTrue(PrecheckPage.btnFundsInsufficient.getText().contains("Insufficient Funds"));
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

    @EpicvinTest
    public void checkSalvageAlertBanner() throws Exception {
        ResultSet resultSet = SQLRequestsEpicvin.getAuctionVinFromDB();
        try {
            resultSet.next();
            String vin = resultSet.getString("vin");
            System.out.println("VIN: " + vin);
            MainPage mainPage = new MainPage();
            PrecheckPage precheckPage = mainPage.searchLotByVin(vin);
            if (precheckPage.precheckPageAll().contains("So sorry!")) {
                Assertions.assertEquals("So sorry!", precheckPage.sorryText());
            } else {
                Assertions.assertEquals("SALVAGE ALERT!\nThis vehicle may have salvage records", precheckPage.getSalvageAlertBanner());
            }
        } catch (Exception e) {
            assert false : "vin not found in database";
        }
    }
}
