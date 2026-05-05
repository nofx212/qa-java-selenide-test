package interfaces;

import base.BaseMyReportsPage;
import base.BaseSubscriptionPage;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.epicvin.Account.DashboardPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public interface Menu<T extends BaseMyReportsPage, S extends BaseSubscriptionPage> {

    SelenideElement accountID = $x("//span[@class='acc-email']");
    SelenideElement myAccountNewReport = $(By.id("accountDropdown"));
    SelenideElement myAccountOldReport = $(By.id("navbarDropdown"));
    SelenideElement dashboard = $(new By.ByPartialLinkText("Dashboard"));
    SelenideElement myReportsNew = $(new By.ByPartialLinkText("My reports"));
    SelenideElement myReportsOld = $(new By.ByPartialLinkText("My Reports"));
    SelenideElement subscriptionsOld = $(new By.ByPartialLinkText("Subscription"));
    SelenideElement subscriptionsNew = $(new By.ByPartialLinkText("Subscriptions"));

    default String accountID() {
        return accountID.getText();
    }

    default void clickMyAccountNew() {
        sleep(5000);
        if (myAccountNewReport.exists() && myAccountNewReport.isDisplayed()) {
            System.out.println("New account");
            myAccountNewReport.click();
        } else {
            System.out.println("Old account");
            myAccountOldReport.shouldBe(visible).click();
        }
    }

    default void clickMyReports() {
        if (!myReportsNew.isDisplayed()) {
            System.out.println("Old reports");
            myReportsOld.click();
        } else {
            System.out.println("New reports");
            myReportsNew.click();
        }
    }

    default void clickSubscriptions() {
        if (!subscriptionsNew.isDisplayed()) {
            System.out.println("Old subscription");
            subscriptionsOld.click();
        } else {
            System.out.println("New subscription");
            subscriptionsNew.click();
        }
    }

    default DashboardPage clickDashboard() {
        clickMyAccountNew();
        dashboard.click();
        return new DashboardPage();
    }

    default T clickMyReports(Class<T> pageClass) {
        clickMyAccountNew();
        clickMyReports();
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error " + pageClass.getSimpleName(), e);
        }
    }

    default S subscriptions(Class<S> pageClass) {
        sleep(3000);
        clickMyAccountNew();
        clickSubscriptions();
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error " + pageClass.getSimpleName(), e);
        }
    }
}
