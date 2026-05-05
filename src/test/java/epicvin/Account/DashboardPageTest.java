package epicvin.Account;

import annotations.epicvin.EpicvinTest;
import clearAccount.ClearAccount;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Account.DashboardPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Price.PricePage;
import pages.epicvin.Report.ReportPage;

import static com.codeborne.selenide.Selenide.title;
import static constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static pages.epicvin.Account.DashboardPage.*;

public class DashboardPageTest extends base.BaseTest {

    @EpicvinTest
    public void checkContentOnDashboardPage() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL15, VALID_PASSWORD);
        DashboardPage dashboardPage = mainPage.clickDashboard();
        assertAll(
                () -> assertEquals("Epicvin", title()),
                () -> assertEquals("Dashboard", dashboardPage.dashboardBlock(titleH1)),
                () -> assertEquals("My Balance", dashboardPage.dashboardBlock(block1)),
                () -> assertEquals("You haven’t checked any vehicles yet!", dashboardPage.dashboardBlock(block2)),
                () -> assertEquals("Save Thousands of Dollars", dashboardPage.dashboardBlock(block3)),
                () -> assertEquals("0", dashboardPage.dashboardBlock(prepaid))
        );
    }

    @EpicvinTest
    public void searchVinFromDashboard() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        ClearAccount.disputeMainPageEpicvin(CLOSE_DISPUTE_EPICVIN);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        DashboardPage dashboardPage = mainPage.clickDashboard();
        PrecheckPage precheckPage = dashboardPage.setInputVin(VALID_VIN);
        Assertions.assertTrue(precheckPage.greatText().contains("Get unlimited access to detailed reports"));
    }

    @EpicvinTest
    public void checkBuyMore() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        DashboardPage dashboardPage = mainPage.clickDashboard();
        PricePage pricePage = dashboardPage.clickBuyMore();
        assertTrue(
                "Get a package of reports to save money now and check the vehicle's history later".equals(pricePage.titleH1()) ||
                         "Get a package of reports to save money now and check the vehicle's history later - Global".equals(pricePage.titleH1()),
                "Unexpected page H1: " + pricePage.titleH1()
        );
    }

    @EpicvinTest
    public void checkNavigationToHomePageFromTooltip() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        DashboardPage dashboardPage = mainPage.clickDashboard();
        dashboardPage.openTooltip();
        Assertions.assertTrue(BASE_URL_EPICVIN.contains(mainPage.getUrlMainPage()));
    }

    @EpicvinTest
    public void openReportFromDashboard() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL6, VALID_PASSWORD);
        DashboardPage dashboardPage = mainPage.clickDashboard();
        ReportPage reportPage = dashboardPage.openReport();
        Assertions.assertTrue(reportPage.epicVinReport().contains("EpicVIN vehicle history report for"));
    }
}
