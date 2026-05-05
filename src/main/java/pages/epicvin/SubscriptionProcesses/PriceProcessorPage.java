package pages.epicvin.SubscriptionProcesses;

import pages.epicvin.Account.BillingPage;
import pages.epicvin.Price.PricePage;

import static constants.Constants.*;
import static constants.Constants.CVC;

public class PriceProcessorPage {

    private final PricePage pricePage;

    public PriceProcessorPage(PricePage pricePage) {
        this.pricePage = pricePage;
    }

    public BillingPage subscribe(String subscription) {
        switch (subscription) {
            case "full":
                return pricePage.fullSubFromPrice(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);
            case "trial":
                return pricePage.trialSubFromPrice(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);
        }
        throw new IllegalArgumentException("No such subscription: " + subscription);
    }
}
