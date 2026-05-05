package pages.epicvin.PaymentProcesses;

import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;

import static constants.Constants.*;

public class FullReportProcessorPage {
    private final PrecheckPage precheckPage;

    public FullReportProcessorPage(PrecheckPage precheckPage) {
        this.precheckPage = precheckPage;
    }

    public ReportPage processPayment(String paymentMethod) {
        switch (paymentMethod) {
            case "card":
                return precheckPage.fullReportByCard(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP);

            case "paypal":
                return precheckPage.fullReportByPayPal(PAYPAL_EMAIL, PAYPAL_PASSWORD);

            case "prepaid":
                return precheckPage.prepaidReport();

            case "unloggedUser":
                return precheckPage.fullReportByCardUnloggedUser(TEST, VALID_EMAIL4, CARD_NUMBER, MONTH_YEAR, CVC, ZIP);

            case "fullReportByFunds":
                return precheckPage.fullReportByFunds();

            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }
}