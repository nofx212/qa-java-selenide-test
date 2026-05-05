package epicvin.Account;

import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import pages.epicvin.Account.BillingPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.PaymentProcesses.PaymentMethodProcessorPage;

import java.util.List;

import static constants.Constants.*;

public class BillingPageTest extends base.BaseTest {

    @Disabled("Delete this flow")
    @EpicvinTest
    public void addAllPaymentMethodsThenDelete() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL9, VALID_PASSWORD);
        BillingPage billingPage = mainPage.clickBilling();
        PaymentMethodProcessorPage processorPage = new PaymentMethodProcessorPage(billingPage);
        List<String> paymentMethods = List.of("card", "card3Ds", "paypal", "googlepay");
        for (String method : paymentMethods) {
            processorPage.processPayment(method);
            String actualAlert = billingPage.getSuccessAlert();
            switch (method) {
                case "card" ->
                        Assertions.assertEquals("Payment method VISA **** 1111 has been successfully added.", actualAlert);
                case "card3Ds" ->
                        Assertions.assertEquals("Payment method VISA **** 3220 has been successfully added.", actualAlert);
                default -> Assertions.assertEquals("Payment method has been successfully added.", actualAlert,
                        "Unexpected success alert for " + method + ": " + actualAlert);
            }
        }
        billingPage.deleteAllPaymentMethods();
        Assertions.assertEquals("Payment method has been successfully deleted.", billingPage.getSuccessAlert());
    }
}
