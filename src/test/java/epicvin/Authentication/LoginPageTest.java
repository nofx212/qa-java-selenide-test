package epicvin.Authentication;

import annotations.epicvin.EpicvinTest;

import org.junit.jupiter.api.Assertions;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;

import static constants.Constants.*;

public class LoginPageTest extends base.BaseTest {

    @EpicvinTest
    public void loginWithCorrectCredentials() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        String iDAccount = mainPage.accountID();
        Assertions.assertEquals("534923", iDAccount);
    }

    @EpicvinTest
    public void loginWithIncorrectCredentials() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton().login(INVALID_EMAIL, INVALID_PASSWORD);
        String textUserExist = loginPage.userExistText();
        Assertions.assertEquals("This user does not exist. Please, register.", textUserExist);
    }

    @EpicvinTest
    public void loginWithNullCredentials() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton().login(NULL_EMAIL, NULL_PASSWORD);
        String textTitleLogin = loginPage.titleLoginText();
        Assertions.assertEquals("Login", textTitleLogin);
    }
}
