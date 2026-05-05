package interfaces;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public interface Report {

    SelenideElement reviewBlock = $x("//div[@class='review-block']//h2");
    SelenideElement oneStarReview = $x("//input[@value='1']");
    SelenideElement fiveStarsReview = $x("//input[@value='5']");
    SelenideElement textReview = $(By.id("text-review"));
    SelenideElement btnSendReview = $(By.id("send-review"));
    SelenideElement modalReview = $x("//div[contains(@class,'modal')]");
    SelenideElement modalHighRating = $x("//div[@class='high-rating']//p");
    SelenideElement btnCloseModalHighRating = $x("//button[@class='close']");
    SelenideElement btnCloseModal = $(By.id("close-modal"));
    SelenideElement tblOdometers = $(By.id("odo-list"));
    SelenideElement shareReview = $x("//div[@class='review__share']//a");
    SelenideElement report = $("#report");


    default String reviewIsDisplayed() {
        executeJavaScript("scroll(0,1000)");
        sleep(2000);
        return reviewBlock.getText();
    }

    default void clickOneStar() {
        executeJavaScript("arguments[0].click()", oneStarReview);
    }
    default void clickFiveStars() {
        executeJavaScript("arguments[0].click()", fiveStarsReview);
    }

    default void setTextReview(String test) {
        textReview.setValue(test);
        sleep(1000);
    }

    default void clickBtnSend() {
        executeJavaScript("arguments[0].click()", btnSendReview);
        sleep(1000);
    }

    default void clickCloseModal() {
        btnCloseModal.click();
    }

    default void modalReviewDisplayed() {
        if (modalReview.isDisplayed()) {
            clickCloseModal();
            sleep(2000);
        }
    }

    default String highRatingText() {
       return modalHighRating.getText();
    }

    default void clickCloseModalHighRating() {
        executeJavaScript("arguments[0].click()", btnCloseModalHighRating);
        sleep(2000);
    }

    default void sendReviewOneStar(String test) {
        setTextReview(test);
        clickOneStar();
        clickBtnSend();
        modalReviewDisplayed();
        sleep(2000);
    }

    default void sendReviewFiveStars(String test) {
        setTextReview(test);
        clickFiveStars();
        clickBtnSend();
    }

    default String tblOdometerCheck() {
        return tblOdometers.shouldBe(visible).getText();
    }

    default void clickShareReview() {
        shareReview.click();
        switchTo().window(1);
    }
}
