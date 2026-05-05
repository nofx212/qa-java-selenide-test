package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class ContactUsPage {

    public static SelenideElement title = $x("//h1[contains(@class,'contacts__title')]");
    public static SelenideElement block1 = $x("//section[@id='sendMessageSection']//h2");
    public static SelenideElement block2 = $x("//div[@class='contacts__info']//section[2]//h2");
    public static SelenideElement block3 = $x("//section[@class='contacts__bottom']//h2");
    private final SelenideElement getName = $(By.id("name"));
    private final SelenideElement getEmail = $(By.id("email"));
    private final SelenideElement getMessage = $(By.id("message"));
    private final SelenideElement btnSend = $x("//button[contains(@class,'contact__form-btn')]");
    private final SelenideElement alertSend = $x("//div[contains(@class,'contacts__ms')]");
    private final SelenideElement btnGeneralQuestions = $x("//div[@class='contacts__btns-group']//a[2]");
    private final SelenideElement agreeCheckbox = $(By.id("agree"));
    public static SelenideElement validationErrorName = $x("//div[@class='contact__fields']//div[1]//p");
    public static SelenideElement validationErrorEmail = $x("//div[@class='contact__fields']//div[2]//p");
    public static SelenideElement validationErrorMessage = $x("//form[@id='contact-form']//div[3]//p");

    public String contactUsBlock(SelenideElement block) {
        return block.getText();
    }

    private void setName(String name) {
        getName.setValue(name);
    }

    private void setEmail(String email) {
        getEmail.setValue(email);
    }

    private void setMessage(String message) {
        getMessage.setValue(message);
    }

    public void clickSendMessage() {
        executeJavaScript("arguments[0].click()", btnSend);
    }

    public String alertSuccessfulMessage() {
        return alertSend.getText();
    }

    public void clickUncheckAgree() {
        executeJavaScript("arguments[0].click()", agreeCheckbox);
    }

    public void contactUsForm(String name, String email, String message) {
        setName(name);
        setEmail(email);
        setMessage(message);
        clickSendMessage();
    }

    public void contactUsFormWithoutNewsletter(String name, String email, String message) {
        clickUncheckAgree();
        setName(name);
        setEmail(email);
        setMessage(message);
        clickSendMessage();
    }

    public FaqPage clickGeneralQuestions() {
        btnGeneralQuestions.click();
        return new FaqPage();
    }
}
