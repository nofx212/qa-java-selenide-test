package epicvin.Precheck;

import annotations.epicvin.EpicvinTest;
import annotations.epicvin.prechek.EpicvinVinNotFoundTest;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Account.DashboardPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.PaymentProcesses.VinNotFoundProcessorPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Precheck.VinNotFoundPage;

import static com.codeborne.selenide.Selenide.title;
import static constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static pages.epicvin.Precheck.VinNotFoundPage.*;

public class VinNotFoundPageTest extends base.BaseTest {

    @EpicvinTest
    public void checkContentOnVinNotFoundPage() {
        MainPage mainPage = new MainPage();
        VinNotFoundPage vinNotFoundPage = mainPage.searchWithInvalidVin(INVALID_VIN);
        assertAll(
                () -> assertEquals("Free VIN check for 2PMPK3J94L0000000", title()),
                () -> assertEquals("Free advanced pre-check for  2PMPK3J94L0000000 from our vast database", vinNotFoundPage.getDescription()),
                () -> assertTrue(vinNotFoundPage.vinNotFoundBlock(titleH1).contains("So sorry! We don’t have enough information to provide you with a quality report for")),
                () -> assertEquals("Leave your email and we'll contact you as soon as we have any new information about this vehicle.", vinNotFoundPage.vinNotFoundBlock(block1)),
                () -> assertEquals("Our reports may include the following information:", vinNotFoundPage.vinNotFoundBlock(block2)),
                () -> assertEquals("Get a package of reports to save money now and check the vehicle's history later", vinNotFoundPage.vinNotFoundBlock(block3))
        );
    }

    @EpicvinTest
    public void submitEmail() {
        MainPage mainPage = new MainPage();
        VinNotFoundPage vinNotFoundPage = mainPage.searchWithInvalidVin(INVALID_VIN);
        vinNotFoundPage.submitEmail(VALID_EMAIL);
        Assertions.assertEquals("Already start searching for vehicle history for your VIN", vinNotFoundPage.vinNotFoundBlock(successSubscribe));
    }

    @EpicvinTest
    public void searchWithValidVinFromVinNotFoundPage() {
        MainPage mainPage = new MainPage();
        VinNotFoundPage vinNotFoundPage = mainPage.searchWithInvalidVin(INVALID_VIN);
        PrecheckPage precheckPage = vinNotFoundPage.changeVin(VALID_VIN);
        Assertions.assertTrue(precheckPage.greatText().contains("Get unlimited access to detailed reports"));
    }

    @EpicvinVinNotFoundTest
    public void purchaseFirstPacket(String paymentMethod) {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL7, VALID_PASSWORD);
        VinNotFoundPage vinNotFoundPage = mainPage.searchWithInvalidVin(INVALID_VIN);
        VinNotFoundProcessorPage vinNotFoundProcessorPage = new VinNotFoundProcessorPage(vinNotFoundPage);
        DashboardPage dashboardPage = vinNotFoundProcessorPage.processPayment(paymentMethod);
        if(paymentMethod.equals("cardWithDecline")) {
            Assertions.assertEquals("Your card was declined. Please contact your card issuer.",vinNotFoundPage.getErrorCard());
        }else{
            Assertions.assertEquals("The payment is received, thank you! Your prepaid reports are in your account now.", dashboardPage.getSuccess());

        }
    }
}
