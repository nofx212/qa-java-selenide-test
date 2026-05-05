package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class ReviewPage implements Meta {

    public static SelenideElement h1Title = $x("//h1[contains(@class,'reviews__title')]");
    public static SelenideElement widgetPart1 = $x("//div[@class='reviews__widget']//div[1]//p");
    public static SelenideElement widgetPart2 = $x("//div[@class='reviews__widget']//div[2]");
    public static SelenideElement widgetPart3 = $x("//div[@class='reviews__widget']//div[3]//p");
    public static SelenideElement newReviewBlock = $x("//section[@id='addReview']//h2");
    public static SelenideElement successReview = $x("//div[@id='successMessage']//h2");
    public static SelenideElement errorComment = $x("//p[@class='error-message show']");
    private final SelenideElement rate = $x("//div[@class='new-review__stars']//input[5]");
    private final SelenideElement review = $(By.id("reviewText"));
    private final SelenideElement btnSend = $x("//button[contains(@class,'new-review__btn')]");

    public String reviewBlock(SelenideElement block) {
        return block.getText();
    }

    private void clickRate() {
        executeJavaScript("arguments[0].click()", rate);
    }

    private void setReview(String text) {
        review.setValue(text);
    }

    private void clickSend() {
        executeJavaScript("arguments[0].click()", btnSend);
    }

    public void addNewReview(String text) {
        clickRate();
        setReview(text);
        clickSend();
        sleep(3000);
    }

    public void addNewReviewWithoutComment() {
        clickRate();
        clickSend();
    }
}
