package interfaces;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import enums.InvalidEmailType;
import generators.EmailGenerator;

import java.util.UUID;

import static com.codeborne.selenide.Selenide.*;

public interface Register {

    SelenideElement inputName = $(By.id("name"));
    SelenideElement inputEmail = $(By.id("email"));
    SelenideElement btnSignUp = $x("//button[contains(text(),'Sign Up')]");
    SelenideElement errorText = $x("//p[@class='field__error']");


    default void setName(String test) {
        sleep(1000);
        inputName.setValue(test);
    }

    default void setEmail(String email) {
        inputEmail.setValue(email);
    }

    static String getUniqueId() {
        return UUID.randomUUID().toString().substring(0, 5) + System.currentTimeMillis() / 1000;
    }

    default void clickSignUpButton() {
        executeJavaScript("arguments[0].click()", btnSignUp);
    }

    default String registration(String test) {
        setName(test);
        var email = (String.format("test.autotest+%s@gmail.com", getUniqueId()));
        setEmail(email);
        clickSignUpButton();
        return email;
    }

    default void invalidRegistration(String test, String email) {
        setName(test);
        setEmail(email);
        clickSignUpButton();
    }

    default String getValidationMessage() {
        return errorText.getText();
    }

    default String generateInvalidEmail(String caseType) {
        return EmailGenerator.generateInvalidEmail(InvalidEmailType.valueOf(caseType));
    }
}
