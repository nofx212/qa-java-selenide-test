package pages.epicvin.SubscriptionProcesses;

import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;

import static constants.Constants.*;

public class SubscriptionProcessorPage {

    private final PrecheckPage precheckPage;

    public SubscriptionProcessorPage(PrecheckPage precheckPage) {
        this.precheckPage = precheckPage;
    }

    public ReportPage subscribe(String subscription) {
        switch (subscription) {

            case "full":
                return precheckPage.fullSubscription(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);

            case "trial":
                return precheckPage.trialSubscription(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);

            case "trialWithFail3Ds":
                return precheckPage.trialSubscriptionByCardWithFail3Ds(TEST, CARD_WITH_3Ds, MONTH_YEAR, CVC, ZIP);

            case "trialUnloggedUser":
                return precheckPage.trialSubscriptionUnloggedUser(TEST, VALID_EMAIL8, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);

            case "trialUnloggedUserWithInvalidEmail":
                return precheckPage.trialSubsUnloggedUserPaymentValidation(TEST, INVALID_EMAIL_WITH_TYPO, CARD_NUMBER, MONTH_YEAR, CVC, ZIP);

            case "trialUnloggedUserWithInvalidCVV":
                return precheckPage.trialSubsUnloggedUserPaymentValidation(TEST, VALID_EMAIL, CARD_NUMBER, MONTH_YEAR, INVALID_CVV, ZIP);

            case "trialUnloggedUserWithInvalidExpDate":
                return precheckPage.trialSubsUnloggedUserPaymentValidation(TEST, VALID_EMAIL, CARD_NUMBER, INVALID_MONTH_YEAR, CVC, ZIP);

            case "trialUnloggedUserWithEmptyName":
                return precheckPage.trialSubsUnloggedUserPaymentValidation(null, VALID_EMAIL, CARD_NUMBER, MONTH_YEAR, CVC, ZIP);

            case "trialUnloggedUserWithLongPostalCode":
                return precheckPage.trialSubsUnloggedUserPaymentValidation(TEST, VALID_EMAIL, CARD_NUMBER, MONTH_YEAR, CVC, POSTAL_CODE_LONG);

            case "fullUnloggedUser":
                return precheckPage.fullSubscriptionUnloggedUser(TEST, VALID_EMAIL8, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);

            case "fullByFunds":
                return precheckPage.fullSubscriptionByFunds();

//          Для Yuno обычный "trial" уже с 3Ds
//          case "trialWith3Ds":
//               return precheckPage.trialSubscriptionByCardWith3Ds(TEST,CARD_WITH_3Ds, MONTH_YEAR, CVC, ZIP);
//
//          Для Yuno нет тестовых prepaid карт
//          case "trialsWithPrepaidCard":
//               return precheckPage.trialSubscription(TEST,CARD_PREPAID_IXOPAY, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);
//
//          Для Yuno нет тестовых maestro карт
//          case "trialWithMaestroCard":
//               return precheckPage.trialSubscription(TEST,CARD_MAESTRO, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);
        }
        throw new IllegalArgumentException("No such subscription: " + subscription);
    }

}
