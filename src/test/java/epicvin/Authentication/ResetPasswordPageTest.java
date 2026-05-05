package epicvin.Authentication;

import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Authentication.ResetPasswordPage;

import static constants.Constants.*;

public class ResetPasswordPageTest extends base.BaseTest {

    @EpicvinTest
    public void resetPasswordWithValidEmail() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        ResetPasswordPage resetPasswordPage = loginPage.clickForgotPassword();
        resetPasswordPage.resetPassword(VALID_EMAIL);
        Assertions.assertEquals("We have e-mailed your password reset link!", resetPasswordPage.alertSuccess());
    }

    @EpicvinTest
    public void resetPasswordWithInvalidEmail() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        ResetPasswordPage resetPasswordPage = loginPage.clickForgotPassword();
        resetPasswordPage.resetPassword(INVALID_EMAIL);
        Assertions.assertEquals("We couldn't find an account with this email.", resetPasswordPage.invalidEmail());
    }
}
