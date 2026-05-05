package pages.epicvin.PaymentProcesses;

import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;

import static constants.Constants.*;

public class TrialProcessorPage {

    private final PrecheckPage precheckPage;

    public TrialProcessorPage(PrecheckPage precheckPage) {
        this.precheckPage = precheckPage;
    }


    public ReportPage processPayment(String paymentMethod) {
        switch (paymentMethod) {
            case "card":
                return precheckPage.trialSubscription(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);
            case "paypal":
                return precheckPage.chooseTrialByPayPal(PAYPAL_EMAIL, PAYPAL_PASSWORD);
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }
}
