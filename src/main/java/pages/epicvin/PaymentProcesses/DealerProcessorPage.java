package pages.epicvin.PaymentProcesses;

import pages.epicvin.Footer.Dealer.DealerReportPage;
import pages.epicvin.Footer.Dealer.DealerSubscriptionPage;

import static constants.Constants.LICENSE_PLATE;
import static constants.Constants.VALID_VIN;

public class DealerProcessorPage {
    private final DealerSubscriptionPage dealerSubscriptionPage;

    public DealerProcessorPage(DealerSubscriptionPage dealerSubscriptionPage) {
        this.dealerSubscriptionPage = dealerSubscriptionPage;
    }

    public DealerReportPage processSearch(String vinPlate) {
        switch (vinPlate) {
            case "vin":
                return dealerSubscriptionPage.setInputVin(VALID_VIN);
            case "plate":
                return dealerSubscriptionPage.setInputPlate(LICENSE_PLATE);
            default:
                throw new IllegalArgumentException("Unsupported search: " + vinPlate);

        }
    }
}
