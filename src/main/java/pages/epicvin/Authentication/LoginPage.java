package pages.epicvin.Authentication;

import com.codeborne.selenide.SelenideElement;
import interfaces.Login;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage implements Login {

    private final SelenideElement textLogin = $x("//h1[contains(text(),'Login')]");
    private final SelenideElement forgotPassword = $x("//p[@class='auth__note']//a");

    public String titleLoginText() {
        return textLogin.getText();
    }

    public LoginPage login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
        sleep(1000);
        return new LoginPage();
    }

    public ResetPasswordPage clickForgotPassword() {
        executeJavaScript("arguments[0].click()", forgotPassword);
        return new ResetPasswordPage();
    }
}
