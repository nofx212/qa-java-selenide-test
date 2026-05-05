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

//      No 3Ds and prepaid tests cards on Ixopay/Yuno
//          case "card3Ds":
//              return precheckPage.fullReportByCardWith3Ds(TEST,CARD_WITH_3Ds,MONTH_YEAR,CVC, ZIP);
//          case "prepaidCard":
//              return precheckPage.fullReportByCard(TEST,CARD_PREPAID, MONTH_YEAR, CVC, ZIP);
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }
}