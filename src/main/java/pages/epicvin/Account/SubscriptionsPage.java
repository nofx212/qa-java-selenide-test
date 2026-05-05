package pages.epicvin.Account;

import base.BaseSubscriptionPage;
import com.codeborne.selenide.SelenideElement;
import interfaces.Dispute;
import interfaces.Precheck;
import interfaces.Report;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class SubscriptionsPage extends BaseSubscriptionPage implements Precheck, Report, Dispute {

    private final SelenideElement btnCancelSubscription = $x("//button[contains(@class,'cancel-sub')]");
    private final SelenideElement resumeSub = $x("//button[contains(@class,'resume-sub')]");
    private final SelenideElement titlePopupStars = $x("//div[@class='review__info-top']//h2[contains(@class,'heading')]");
    private final SelenideElement shareReview = $(By.id("share-review"));
    private final SelenideElement textReview = $x("//textarea[contains(@class,'field__input')]");
    private final SelenideElement successReviewText = $x("//div[@class='review__success']//h2");
    private final SelenideElement closeSuccessReview = $x("//div[@id='review-modal']//button[@class='close']");
    private final SelenideElement subStatus = $x("//span[@class='badge badge-danger']");


    private void clickCancelSubscription() {
        btnCancelSubscription.click();
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
        clickFiveStars();
    }

    public void cancelSubAfterReview(String test) {
        clickCancelSubscription();
        sleep(2000);
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
