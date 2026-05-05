package pages.epicvin.Account;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.*;


public class SettingsPage extends PrecheckPage {

    private final SelenideElement km = $(By.id("numberSystemKm"));
    private final SelenideElement mi = $(By.id("numberSystemMi"));
    private final SelenideElement firstReport = $x("//div[@class='profile__table-btns']//a[1]");
    private final SelenideElement currentPassword = $(By.id("passwordCurrentSettings"));
    private final SelenideElement newPassword = $(By.id("passwordNewSettings"));
    private final SelenideElement confirmNewPassword = $(By.id("passwordConfirmSettings"));
    private final SelenideElement btnChangePassword = $x("//div[@class='profile__sections']//section[2]//button[@class='btn-reset button button--accent']");
    private final SelenideElement successChange = $x("//div[@class='alert-v2']//p");
    private final SelenideElement failChange = $x("//div[@class='invalid-feedback']//strong");
    private final SelenideElement validationText = $x("//p[contains(@class,'is-invalid')]");


    public void clickKm() {
        executeJavaScript("arguments[0].click()", km);
        myAccountNewReport.click();
        myReportsNew.click();
        firstReport.click();
    }

    public void clickMi() {
        executeJavaScript("arguments[0].click()", mi);
    }

    public void changePassword(String currentPass, String newPass, String confirmNewPass) {
        currentPassword.setValue(currentPass);
        newPassword.setValue(newPass);
        confirmNewPassword.setValue(confirmNewPass);
        executeJavaScript("arguments[0].click()", btnChangePassword);
    }

    public String getSuccess() {
        return successChange.getText();
    }

    public String getFail() {
        sleep(5000);
        return failChange.getText();
    }

    public String getValidationText() {
        return validationText.getText();
    }
}
