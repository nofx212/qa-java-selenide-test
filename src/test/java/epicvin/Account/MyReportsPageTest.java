package epicvin.Account;

import annotations.epicvin.EpicvinTest;
import clearAccount.ClearAccount;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Account.MyReportsPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportInaccuracyPage;
import pages.epicvin.Report.ReportPage;

import static constants.Constants.*;

public class MyReportsPageTest extends base.BaseTest {

    @EpicvinTest
    public void checkNoticeAnInaccuracyForm() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        MyReportsPage myReportsPage = mainPage.clickMyReports(MyReportsPage.class);
        ReportPage reportPage = myReportsPage.clickViewReport();
        ReportInaccuracyPage reportInaccuracyPage = reportPage.clickNotice();
        reportInaccuracyPage.sendNoticeForm();
        Assertions.assertEquals("Message sent successfully", reportPage.messageSuccessfullySend());
    }

    @EpicvinTest
    public void checkPaginationOnMyReportsPage() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        MyReportsPage myReportsPage = mainPage.clickMyReports(MyReportsPage.class);
        var href = myReportsPage.clickPage5();
        Assertions.assertEquals(MY_REPORT_URL_PAGE5, href);
    }

    @EpicvinTest
    public void checkReportsWithDispute() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL16, VALID_PASSWORD);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ReportPage reportPage = precheckPage.prepaidReport();
        if(reportPage.epicVinTitleText().contains("EpicVIN vehicle history report for")) {
            ClearAccount.disputeMainPageEpicvin(ADD_DISPUTE_EPICVIN);
        }
        MyReportsPage myReportsPage = mainPage.clickMyReports(MyReportsPage.class);
        myReportsPage.clickViewReport();
        Assertions.assertEquals("Your Account Temporarily Limited",reportPage.getModalDispute());
        ClearAccount.disputeMainPageEpicvin(CLOSE_DISPUTE_EPICVIN);
    }
}
