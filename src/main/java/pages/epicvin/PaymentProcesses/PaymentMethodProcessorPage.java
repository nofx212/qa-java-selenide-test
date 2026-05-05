package pages.epicvin.PaymentProcesses;

import pages.epicvin.Account.BillingPage;

import static constants.Constants.CARD_NUMBER;
import static constants.Constants.CARD_WITH_3Ds;
import static constants.Constants.CVC;
import static constants.Constants.MONTH_YEAR;

public class PaymentMethodProcessorPage {

    private final BillingPage billingPage;

    public PaymentMethodProcessorPage(BillingPage billingPage) {
        this.billingPage = billingPage;
    }

    public void processPayment(String paymentMethod) {
        switch (paymentMethod) {
            case "card":
                billingPage.addPaymentMethodCard(CARD_NUMBER, MONTH_YEAR, CVC);
                return;
            case "card3Ds":
                billingPage.addPaymentMethodCard3Ds(CARD_WITH_3Ds, MONTH_YEAR, CVC);
                return;
            case "paypal":
                BillingPage.addPaymentMethodPayPal();
                return;
            case "googlepay":
                billingPage.addPaymentMethodGooglePay();
                return;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }
}
