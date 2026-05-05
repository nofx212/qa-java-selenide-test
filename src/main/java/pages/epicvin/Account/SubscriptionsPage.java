package pages.epicvin.Account;

import base.BaseSubscriptionPage;
import com.codeborne.selenide.SelenideElement;
import interfaces.Dispute;
import interfaces.Precheck;
import interfaces.Report;
import org.openqa.selenium.By;
import pages.epicvin.Price.PricePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SubscriptionsPage extends BaseSubscriptionPage implements Precheck, Report, Dispute {

//    private final SelenideElement btnSubscribe = $x("//a[contains(@class,'sub-offer-btn')]");
//    private final SelenideElement btnPayByCard = $x("//button[contains(@class,'cc-btn')]");
    private final SelenideElement alertText = $x("//p[@class='alert-v2__text']");
    private final SelenideElement btnCancelNow = $x("//button[contains(@class,'cancel-now-sub')]");
    private final SelenideElement subscribeNow = $x("//a[contains(@class,'profile__sub-offer-btn')]");
    private final SelenideElement btnCancelSubscription = $x("//button[contains(@class,'cancel-sub')]");
    private final SelenideElement btnConfirmCancel = $x("//button[contains(@class,'btn-cancel-modal')]");
//    private final SelenideElement acceptOffer = $x("//button[contains(@class,'btn-agree-pack')]");
//    private final SelenideElement annualPriceOnSub = $x("//div[contains(@class,'mb-2')]");
//    private final SelenideElement annualPopup = $x("//p[@class='annual-modal__call']");
    private final SelenideElement subPrice = $x("//div[contains(@class,'mb-2')]");
//    private final SelenideElement addNewPaymentMethod = $(By.id("add-open"));
//    private final SelenideElement btnAddNewCard = $x("//button[@data-type='cc']");
    private final SelenideElement resumeSub = $x("//button[contains(@class,'resume-sub')]");
    private final SelenideElement trialTitle = $x("//div[@class='ttl']");
//    private final SelenideElement annualPriceFromPopup = $x("//p[@class='annual-modal__call']//span[@class='wide accent']");
//    private final SelenideElement closeAnnualPopup = $x("//button[contains(@class,'annual-modal__close')]");
//    private final SelenideElement stillCancelSub = $x("//button[contains(@class,'outline-secondary')]");
    private final SelenideElement titlePopupStars = $x("//div[@class='review__info-top']//h2[contains(@class,'heading')]");
    private final SelenideElement shareReview = $(By.id("share-review"));
    private final SelenideElement textReview = $x("//textarea[contains(@class,'field__input')]");
    private final SelenideElement successReviewText = $x("//div[@class='review__success']//h2");
    private final SelenideElement closeSuccessReview = $x("//div[@id='review-modal']//button[@class='close']");
    private final SelenideElement subStatus = $x("//span[@class='badge badge-danger']");
//    private final SelenideElement makeDefaultPayPal = $x("//button[@data-type='paypal']");
//    private final SelenideElement makeDefaultCreditCard = $x("//div[@class='payment-methods__item-top']//button[@data-type='cc']");
//    private final SelenideElement makeDefaultGooglePay = $x("//div[@class='payment-methods__item-top']//button[@data-type='google']");
//    private final SelenideElement paymentMethodCard = $x("//div[contains(@class,'payment-methods__item--visa')]//div[2]//p[1]");
//    private final SelenideElement paymentMethodPaypal = $x("//div[contains(@class,'payment-methods__item--paypal')]//div[2]//p[1]");
//    private final SelenideElement paymentMethodGoogle = $x("//div[contains(@class,'payment-methods__item--google')]//div[2]//p[1]");
//    private final SelenideElement methodGooglePay = $x("//ul[contains(@class,'pay-types')]//li[3]");
//    private final SelenideElement btnBuyWithGooglePay = $x("//button[@data-type='gp']");
//    private final SelenideElement btnPayByGooglePay = $(By.id("gpay-button-online-api-id"));



//    public void subscribeNowByCard(String card, String date, String cvc) {
//        if (btnCancelSubscription.isDisplayed()) {
//            System.out.println("Subscription is active");
//            cancelNowSubscription();
//        }
//        boolean cardExists = paymentMethodCard.exists() && paymentMethodCard.isDisplayed();
//        boolean correctCard = cardExists && "CARD NUMBER".equals(paymentMethodCard.getText());
//
//        if (!cardExists || !correctCard) {
//            System.out.println("Credit Card payment method is not added");
//            addNewPaymentMethod.click();
//            switchToIframe();
//            setCardNumber(card);
//            setMonthYear(date);
//            setCVC(cvc);
//            defaultIframe();
//            btnAddNewCard.click();
//            sleep(3000);
//            clickPayByCard();
//            sleep(10000);
//        } else {
//            System.out.println("Credit Card payment method added");
//            makeDefaultCreditCard();
//            clickSubscribeNow();
//            clickPayByCard();
//        }
//    }

    public PricePage subscribe() {
        if (btnCancelSubscription.isDisplayed()) {
            System.out.println("Subscription is active");
            cancelNowSubscription();
        }
        executeJavaScript("arguments[0].click()", subscribeNow);
        return new PricePage();
    }

//    public void subscribeNowByPayPal(String email, String password) {
//        if (btnCancelSubscription.isDisplayed()) {
//            System.out.println("Subscription is active");
//            cancelNowSubscription();
//        }
//        boolean payPalExists = paymentMethodPaypal.exists() && paymentMethodPaypal.isDisplayed();
//        boolean correctPayPal = payPalExists && "EMAIL".equals(paymentMethodPaypal.getText());
//
//        if (!payPalExists || !correctPayPal) {
//            System.out.println("PayPal payment method is not added");
//            BillingPage.addPaymentMethodPayPal();
//            makeDefaultPayPal();
//            clickPayPalButton();
//            processPayPalEpicvinPayment(email, password);
//        } else {
//            System.out.println("PayPal payment method added");
//            makeDefaultPayPal();
//            clickPayPalButton();
//            processPayPalEpicvinPayment(email, password);
//            sleep(10000);
//        }
//    }


//    private void makeDefaultPayPal() {
//        if(!makeDefaultPayPal.isDisplayed()){
//            System.out.println("PayPal is default now");
//        }else {
//            System.out.println("Make PayPal to default");
//            executeJavaScript("arguments[0].click()", makeDefaultPayPal);
//            System.out.println("Default method is PayPal");
//        }
//    }

//    private void makeDefaultCreditCard() {
//        if (!makeDefaultCreditCard.isDisplayed()) {
//            System.out.println("Credit Card is default now");
//        } else {
//            System.out.println("Make Credit Card to default");
//            executeJavaScript("arguments[0].click()", makeDefaultCreditCard);
//        }
//    }


//    public String textAlert() {
//        return alertText.getText();
//    }

    public void cancelNowSubscription() {
        if (btnCancelNow.exists()) {
            btnCancelNow.shouldBe(visible).click();
            System.out.println("CancelNow - successful");
        } else {
            System.out.println("CancelNow button is absent - skipping");
        }
    }
    private void clickCancelSubscription() {
        btnCancelSubscription.click();
    }

    private void confirmCancel() {
        btnConfirmCancel.click();
    }

    private void clickResumeSubscription() {
        resumeSub.click();
    }

    public void cancelAndResumeAnnualSub() {
        clickCancelSubscription();
        System.out.println("Cansel - successful");
        confirmCancel();
        sleep(10000);
        System.out.println("Confirm cancel - successful");
        clickResumeSubscription();
        System.out.println("Resume - successful");
        sleep(3000);
    }

    public String getActualSubPrice() {
        return subPrice.getText().replaceAll("[^\\d.]", "");
    }

    public String getTrialTitle() {
        return trialTitle.getText();
    }

    @Override
    public void clickFiveStars() {
        if (titlePopupStars.getText().equals("Give feedback")) {
            executeJavaScript("arguments[0].click()", fiveStarsReview);
        }
    }

    @Override
    public void setTextReview(String test) {
        textReview.setValue(test);
        sleep(1000);
    }

    @Override
    public void clickShareReview() {
        executeJavaScript("arguments[0].click()", shareReview);
        switchTo().window(1);
    }

    private void reviewSuccess() {
        if (successReviewText.getText().equals("Your review has been accepted and your subscription has been successfully stopped.")) {
            executeJavaScript("arguments[0].click()", closeSuccessReview);
            sleep(2000);
        }
    }

    public void sendReview() {
        clickCancelSubscription();
        sleep(2000);
//        clickCloseAnnualPopup();
//        clickStillCancelSub();
        clickFiveStars();
    }

    public void cancelSubAfterReview(String test) {
        clickCancelSubscription();
        sleep(2000);
//        clickCloseAnnualPopup();
//        clickStillCancelSub();
        clickOneStar();
        setTextReview(test);
        clickBtnSend();
        reviewSuccess();
    }

    public String getSubStatus() {
        return subStatus.getText();
    }

    public String resumeSubscription() {
        return resumeSub.getText();
    }
}
