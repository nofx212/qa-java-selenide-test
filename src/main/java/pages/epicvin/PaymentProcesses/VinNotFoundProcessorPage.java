package pages.epicvin.PaymentProcesses;

import pages.epicvin.Account.DashboardPage;
import pages.epicvin.Precheck.VinNotFoundPage;

import static constants.Constants.*;

public class VinNotFoundProcessorPage {
    private final VinNotFoundPage vinNotFoundPage;

    public VinNotFoundProcessorPage(VinNotFoundPage vinNotFoundPage) {
        this.vinNotFoundPage = vinNotFoundPage;
    }

    public DashboardPage processPayment(String paymentMethod) {
        switch (paymentMethod) {
            case "card":
                return vinNotFoundPage.purchaseFirstPacketCreditCard(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP);
            case "paypal":
                return vinNotFoundPage.purchaseFirstPacketPayPal(PAYPAL_EMAIL, PAYPAL_PASSWORD);
            case "cardWithDecline":
                return vinNotFoundPage.purchaseFirstPacketCreditCard(TEST, CARD_DECLINE_YUNO, MONTH_YEAR, CVC, ZIP);
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }
}
