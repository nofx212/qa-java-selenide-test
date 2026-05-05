package pages.epicvin.Report;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static constants.Constants.*;

public class ReportInaccuracyPage {

    private final SelenideElement confirmEmail = $(By.id("confirm"));
    private final SelenideElement btnNext1 = $x("//button[@class='btn btn-blue-notice btn-one-next']");
    private final SelenideElement fieldVin = $(By.id("vin"));
    private final SelenideElement fieldMake = $(By.id("make"));
    private final SelenideElement fieldLicenseNumber = $(By.id("number"));
    private final SelenideElement fieldModel = $(By.id("model"));
    private final SelenideElement fieldLicenseState = $(By.id("state"));
    private final SelenideElement fieldColor = $(By.id("color"));
    private final SelenideElement fieldYear = $(By.id("year"));
    private final SelenideElement fieldOdometer = $(By.id("odometer"));
    private final SelenideElement btnNext2 = $x("//button[@class='btn btn-blue-notice btn-two-next']");
    private final SelenideElement fieldDate = $(By.id("date-incorrect"));
    private final SelenideElement fieldType = $x("//select[@id='correction-type']//option[6]");
    private final SelenideElement fieldText = $(By.id("message"));
    private final SelenideElement btnSubmit = $x("//button[@class='btn btn-orange-notice btn-send-notice']");


    private void setConfirmEmail() {
        confirmEmail.setValue(VALID_EMAIL6);
    }

    private void clickBtnNext1() {
        executeJavaScript("arguments[0].click()", btnNext1);
    }

    private void setFieldVin() {
        fieldVin.setValue(VALID_VIN);
    }

    private void setFieldMake() {
        fieldMake.setValue(MAKE);
    }

    private void setFieldLicenseNumber() {
        fieldLicenseNumber.setValue(TEST);
    }

    private void setFieldModel() {
        fieldModel.setValue(MODEL);
    }

    private void setFieldLicenseState() {
        fieldLicenseState.setValue(TEST);
    }

    private void setFieldColor() {
        fieldColor.setValue(COLOR);
    }

    private void setFieldYear() {
        fieldYear.setValue(YEAR);
    }

    private void setFieldOdometer() {
        fieldOdometer.setValue(ODOMETER);
    }

    private void clickBtnNext2() {
        btnNext2.click();
    }

    private void setFieldDate() {
        fieldDate.setValue(TEST);
    }

    private void setFieldType() {
        fieldType.click();
    }

    private void setFieldText() {
        fieldText.setValue(TEST);
    }

    private void clickSubmit() {
        executeJavaScript(("arguments[0].scrollIntoView();"), btnSubmit);
        btnSubmit.click();
    }

    public void sendNoticeForm() {
        setConfirmEmail();
        clickBtnNext1();
        setFieldVin();
        setFieldMake();
        setFieldLicenseNumber();
        setFieldModel();
        setFieldLicenseState();
        setFieldColor();
        setFieldYear();
        setFieldOdometer();
        clickBtnNext2();
        setFieldDate();
        setFieldType();
        setFieldText();
        clickSubmit();
    }
}
