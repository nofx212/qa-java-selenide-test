package interfaces;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

public interface ResetPassword {

    SelenideElement inputEmail = $x("//input[@id='email']");
    SelenideElement sendPassword = $x("//button[@type='submit']");
    SelenideElement alertSuccess = $x("//div[contains(@class,'alert-success')]");
    SelenideElement invalidText = $x("//p[@class='field__error']");

    default void setInputEmail(String email) {
        sleep(2000);
        inputEmail.setValue(email);
    }

    default void clickSendPassword() {
        sendPassword.click();
    }

    default void resetPassword(String email) {
        setInputEmail(email);
        clickSendPassword();
    }

    default String alertSuccess() {
        return alertSuccess.getText();
    }

    default String invalidEmail() {
        return invalidText.getText();
    }
}
