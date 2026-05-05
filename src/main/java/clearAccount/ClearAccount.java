package clearAccount;

import pages.epicvin.Main.MainPage;

import static com.codeborne.selenide.Selenide.sleep;

public class ClearAccount {

    public static void clearEpicvinMainPage(String trial, String fingerprints,String full, String reports) {
        new ClearTrialSubscriptions(trial);
        new ClearFingerprints(fingerprints);
        new ClearFullSubscriptions(full);
        new ClearReports(reports);
        new MainPage();
    }

    public static void clearTrialMainPage(String trial) {
        new ClearTrialSubscriptions(trial);
        new MainPage();
    }

    public static void disputeMainPageEpicvin(String url) {
        new Dispute(url);
        new MainPage();
    }

    public static void disputePrecheckPage(String url) {
        sleep(3000);
        new Dispute(url);
    }

    public static void addFunds(String url) {
        new Funds(url);
        new MainPage();
    }
}
