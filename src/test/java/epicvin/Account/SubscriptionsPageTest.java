package epicvin.Account;

import annotations.epicvin.EpicvinPaymentTest;
import annotations.epicvin.EpicvinTest;
import clearAccount.ClearAccount;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Account.SubscriptionsPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;
import pages.epicvin.SocialReviews.GooglePage;
import pages.epicvin.SocialReviews.TrustpilotPage;

import static constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriptionsPageTest extends base.BaseTest {

    @EpicvinTest
    public void reviewAfterCancelTrialSub() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL13, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ReportPage reportPage = precheckPage.trialSubscription(TEST, CARD_NUMBER_1, MONTH_YEAR, CVC, ZIP,YUNO_3DS_CODE);
        if (reportPage.epicVinReport().contains("EpicVIN vehicle history report for")) {
            SubscriptionsPage subscriptionsPage = reportPage.subscriptions(SubscriptionsPage.class);
            subscriptionsPage.sendReview();
            if (reportPage.getReviewServiceText().contains("Share your review on Trustpilot")) {
                subscriptionsPage.clickShareReview();
                Assertions.assertEquals(EPICVIN_TRUSTPILOT, new TrustpilotPage().currentUrl());
            } else {
                subscriptionsPage.clickShareReview();
                Assertions.assertTrue(new GooglePage().currentUrl().contains("https://accounts.google.com/"));
            }
        }
    }

    @EpicvinPaymentTest
    public void cancelTrialSubAfterReview() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL14, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ReportPage reportPage = precheckPage.trialSubscription(TEST,CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);
        SubscriptionsPage subscriptionsPage = reportPage.subscriptions(SubscriptionsPage.class);
        subscriptionsPage.cancelSubAfterReview(TEST);
        assertAll(
                () -> assertEquals("Subscription canceled", subscriptionsPage.getSubStatus()),
                () -> assertEquals("Resume Subscription", subscriptionsPage.resumeSubscription())
        );
    }

    @EpicvinTest
    public void checkSubWithDispute() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL16, VALID_PASSWORD);
        ClearAccount.disputeMainPageEpicvin(ADD_DISPUTE_EPICVIN);
        SubscriptionsPage subscriptionsPage = mainPage.subscriptions(SubscriptionsPage.class);
        Assertions.assertEquals("Your Account Temporarily Limited", subscriptionsPage.getModalDispute());
        ClearAccount.disputeMainPageEpicvin(CLOSE_DISPUTE_EPICVIN);
    }
}