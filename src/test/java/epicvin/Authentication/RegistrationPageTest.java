package epicvin.Authentication;

import annotations.epicvin.EpicvinRegistrationTest;
import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Account.DashboardPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Authentication.RegistrationPage;
import sql.SQLRequestsEpicvin;

import java.sql.ResultSet;

import static constants.Constants.*;

public class RegistrationPageTest extends base.BaseTest {

    @EpicvinTest
    public void registrationWithCorrectCredentials() throws Exception {
        MainPage mainPage = new MainPage();
        RegistrationPage registrationPage = mainPage.clickRegistration();
        var email = registrationPage.registration(TEST);
        DashboardPage dashboardPage = mainPage.clickDashboard();
        var checkEmail = dashboardPage.getAccountEmail();
        Assertions.assertEquals(email, checkEmail);
        ResultSet resultSet = SQLRequestsEpicvin.getUserByEmail(email);
        if (resultSet.next()) {
            String actualUsername = resultSet.getString("name");
            System.out.println("USER NAME IN DATABASE: " + actualUsername);
            assert actualUsername.equals(TEST) : "Incorrect username";
        } else {
            assert false : "User not found in the database";
        }
    }

    @EpicvinRegistrationTest
    public void emailValidationOnRegister(String caseType) {
        MainPage mainPage = new MainPage();
        RegistrationPage registrationPage = mainPage.clickRegistration();
        String invalidEmail = registrationPage.generateInvalidEmail(caseType);
        System.out.println("Email: " + invalidEmail);
        registrationPage.invalidRegistration(TEST, invalidEmail);
        String actual = registrationPage.getValidationMessage();
        Assertions.assertTrue(
                actual.contains("That doesn’t look like a valid email. Please check for typos") ||
                        actual.contains("Typo in email? Did you mean ") ||
                        actual.contains("Please enter a valid email address."),
                "Unexpected validation message: " + actual
        );
    }
}
