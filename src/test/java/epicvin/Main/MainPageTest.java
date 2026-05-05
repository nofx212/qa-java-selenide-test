package epicvin.Main;

import annotations.epicvin.EpicvinTest;
import clearAccount.ClearAccount;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import sql.SQLRequestsEpicvin;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.title;
import static constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static pages.epicvin.Main.MainPage.*;

public class MainPageTest extends base.BaseTest {

    @EpicvinTest
    public void checkValidVin() {
        MainPage mainPage = new MainPage();
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        String textGreat = precheckPage.greatText();
        Assertions.assertTrue(textGreat.contains("Get unlimited access to detailed reports"));
    }

    @EpicvinTest
    public void checkInvalidVin() {
        MainPage mainPage = new MainPage();
        PrecheckPage precheckPage = mainPage.searchLotByVin(INVALID_VIN);
        String textSorry = precheckPage.sorryText();
        Assertions.assertEquals("So sorry!", textSorry);
    }

    @EpicvinTest
    public void checkVinNotInDatabase() throws Exception {
        ResultSet resultSet = SQLRequestsEpicvin.getVinNotDB();
        try {
            resultSet.next();
            String vin = resultSet.getString("vin");
            System.out.println("VIN: " + vin);
            MainPage mainPage = new MainPage();
            PrecheckPage precheckPage = mainPage.searchLotByVin(vin);
            if (precheckPage.precheckPageAll().contains("So sorry!")) {
                Assertions.assertEquals("So sorry!", precheckPage.sorryText());
            } else {
                Assertions.assertTrue(precheckPage.greatText().contains("Get unlimited access to detailed reports"));
            }
        } catch (Exception e) {
            assert false : "vin not found in database";
        }
    }

    @EpicvinTest
    public void checkVinLessFiveSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.searchLotByVin(LESS_FIVE_SYMBOLS_VIN);
        mainPage.validationError.shouldHave(text("Please, ensure that your VIN is in proper format. 5 - 17 digits"), Duration.ofSeconds(3));
    }

    @EpicvinTest
    public void checkValidationForTooLongVIN() {
        MainPage mainPage = new MainPage();
        mainPage.searchLotByVin(VIN_MORE_THAN_17SYMBOLS);
        mainPage.validationError.shouldHave(text("Please, ensure that your VIN is in proper format. 5 - 17 digits"), Duration.ofSeconds(3));
    }

    @EpicvinTest
    public void checkValidVinInLowercase() {
        MainPage mainPage = new MainPage();
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN_IN_LOWERCASE);
        String textGreat = precheckPage.greatText();
        Assertions.assertTrue(textGreat.contains("Get unlimited access to detailed reports"));
    }

    @EpicvinTest
    public void checkEmptyVin() {
        MainPage mainPage = new MainPage();
        mainPage.searchLotByVin(EMPTY_VIN);
        mainPage.validationError.shouldHave(text("Please, ensure that your VIN is in proper format. 5 - 17 digits"), Duration.ofSeconds(3));
    }

    @EpicvinTest
    public void checkValidPlate() {
        MainPage mainPage = new MainPage();
        PrecheckPage precheckPage = mainPage.searchLotByPlate(LICENSE_PLATE);
        assertAll(
                () -> assertTrue(precheckPage.greatText().contains("Get unlimited access to detailed reports")),
                () -> assertTrue(precheckPage.getVin().contains("******"))
        );
    }

    @EpicvinTest
    public void checkValidPlateWithoutState() {
        MainPage mainPage = new MainPage();
        mainPage.searchByPlateWithoutState(LICENSE_PLATE);
        Assertions.assertEquals("The state field is required.", mainPage.getValidationState());
    }

    @EpicvinTest
    public void checkStateWithoutPlate() {
        MainPage mainPage = new MainPage();
        mainPage.searchByStateWithoutPlate();
        Assertions.assertEquals("The plate field is required.", mainPage.getValidationPlate());
    }

    @EpicvinTest
    public void checkValidationForTooLongPlate() {
        MainPage mainPage = new MainPage();
        mainPage.validatePlate(LICENSE_PLATE_WITH_MORE_THAN_10SYMBOLS);
        mainPage.validationError.shouldBe(visible, Duration.ofSeconds(10));
        var error = mainPage.validationError.getText();
        Assertions.assertTrue(
                error.contains("The plate field value is greater than 10.") ||
                        error.contains("Captcha failed to load. Disable blocker and try again."),
                "Unexpected validation message: " + error);
    }


    @EpicvinTest
    public void checkLanguagesInDropDown() {
        MainPage mainPage = new MainPage();
        mainPage.clickLangDropDown();
        assertAll(
                () -> assertEquals("Español", mainPage.langES()),
                () -> assertEquals("Français", mainPage.langFR()),
                () -> assertEquals("Polski", mainPage.langPL()),
                () -> assertEquals("Русский", mainPage.langRU()),
                () -> assertEquals("العربية", mainPage.langAR())
        );
    }

    @EpicvinTest
    public void checkContentOnMainPage() {
        MainPage mainPage = new MainPage();
        assertAll(
                () -> assertEquals("Free VIN Check & Vehicle History Report", title()),
                () -> assertEquals("Check your vehicle history report with EpicVIN. Get a free and reliable ⚡ VIN check about the vehicle’s history, price, damages, title records, recalls.", mainPage.getDescription()),
                () -> {
                    String actual = mainPage.mainBlocks(mainTitle);
                    List<String> expectedTitle = List.of(
                            "Save Yourself Thousands With A Comprehensive Vehicle History Report",
                            "VIN Check Can Save You Thousands — Get a Full Vehicle History Report"
                    );
                    assertTrue(expectedTitle.contains(actual), "Unexpected title: " + actual);
                },
                () -> assertEquals("Skip the headache - run a free 30-second VIN Lookup.", mainPage.mainBlocks(videoBlock)),
                () -> assertEquals("Always Check the History of a Car Before Buying It", mainPage.mainBlocks(checkHistoryBlock)),
                () -> assertEquals("What Customers Say About EpicVIN", mainPage.mainBlocks(reviewsBlock)),
                () -> assertEquals("Dedicated Customer Support: We're Here to Help", mainPage.mainBlocks(supportBlock)),
                () -> assertEquals("We Provide VIN Decoding for the Following Makes", mainPage.mainBlocks(carSearchBlock)),
                () -> assertEquals("Auto Insights Blog", mainPage.mainBlocks(similarBlogBlock)),
                () -> assertEquals("Frequently Asked Questions", mainPage.mainBlocks(faqBlock)),
                () -> assertEquals("Save Thousands of Dollars", mainPage.mainBlocks(saveBlock))
        );
    }

    @EpicvinTest
    public void checkAccessibilityIcon() {
        MainPage mainPage = new MainPage();
        mainPage.clickAccessibilityWidget();
        Assertions.assertEquals("Accessibility Menu", mainPage.mainBlocks(accessibilityWidgetMenu));
    }

    @EpicvinTest
    public void checkVinWithDispute() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        ClearAccount.disputeMainPageEpicvin(ADD_DISPUTE_EPICVIN);
        mainPage.searchLotByVin(VALID_VIN);
        if (mainPage.getModalDispute().equals("Your Account Temporarily Limited")) {
            String actual = mainPage.getModalDisputeText();
            String base = "We notice a dispute has been raised with your bank. " +
                    "While it’s being resolved, access to certain features is temporarily limited. " +
                    "Cancelling dispute will help restore full access. " +
                    "We’re here for any support you might need at ";
            Assertions.assertTrue(
                    actual.equals(base + "global@epicvin.com.") || actual.equals(base + "info@epicvin.com."),
                    "Unexpected modal text: " + actual
            );
        }
        ClearAccount.disputeMainPageEpicvin(CLOSE_DISPUTE_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        Assertions.assertTrue(precheckPage.greatText().contains("Get unlimited access to detailed reports"));
    }

    @EpicvinTest
    public void checkPlateWithDispute() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL16, VALID_PASSWORD);
        ClearAccount.disputeMainPageEpicvin(ADD_DISPUTE_EPICVIN);
        mainPage.searchLotByPlate(LICENSE_PLATE);
        if (mainPage.getModalDispute().equals("Your Account Temporarily Limited")) {
            String actual = mainPage.getModalDisputeText();
            String base = "We notice a dispute has been raised with your bank. " +
                    "While it’s being resolved, access to certain features is temporarily limited. " +
                    "Cancelling dispute will help restore full access. " +
                    "We’re here for any support you might need at ";
            Assertions.assertTrue(
                    actual.equals(base + "global@epicvin.com.") || actual.equals(base + "info@epicvin.com."),
                    "Unexpected modal text: " + actual
            );
        }
        ClearAccount.disputeMainPageEpicvin(CLOSE_DISPUTE_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByPlate(LICENSE_PLATE);
        Assertions.assertTrue(precheckPage.greatText().contains("Get unlimited access to detailed reports"));
    }

}
