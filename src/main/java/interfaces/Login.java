package interfaces;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.TypeOptions.text;

public interface Login {

    SelenideElement inputEmail = $(By.id("email"));
    SelenideElement inputPassword = $(By.id("password"));
    SelenideElement btnLogin = $x("//button[@type='submit']");
    SelenideElement errorText = $x("//p[@class='field__error']");


    default void setEmail(String email) {
        sleep(2000);
        inputEmail.setValue(email);
    }

    default void setPassword(String password) {
        inputPassword.type(text(password).sensitive());
    }

    default void clickLoginButton() {
        executeJavaScript("arguments[0].click()", btnLogin);
    }

    default String userExistText() {
        return errorText.getText();
    }
}
