package interfaces;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public interface Refund {

    SelenideElement titleCheck = $x("//h1[contains(text(),'Refund Policy')]");
    SelenideElement fieldEmail = $(By.id("email"));
    SelenideElement fieldVin = $(By.id("vin"));
    SelenideElement fieldReason = $x("//select[@id='reason']//option[contains(text(),'Other')]");
    SelenideElement fieldName = $(By.id("name"));
    SelenideElement fieldLAstName = $(By.id("surname"));
    SelenideElement fieldDate = $(By.id("date_purchase"));
    SelenideElement fieldPayment = $(By.id("payment"));
    SelenideElement fieldTransaction = $(By.id("transaction_id"));
    SelenideElement fieldNotes = $(By.id("notes"));
    SelenideElement btnSubmit = $x("//button[contains(text(),'Submit')]");
    SelenideElement alertBanner = $x("//div[contains(@class,'alert-success')]");
    SelenideElement currentDate = $x("//td[@class='today day']");


    default String checkTitle() {
        return titleCheck.getText();
    }

    private void setEmail(String email) {
        fieldEmail.setValue(email);
    }

    private void setVin(String vin) {
        fieldVin.setValue(vin);
    }

    private void setReason() {
        fieldReason.click();
    }

    private void setName(String name) {
        fieldName.setValue(name);
    }

    private void setLastName(String name) {
        fieldLAstName.setValue(name);
    }

    private void clickPurchaseDate() {
        fieldDate.click();
    }

    private String getCurrentDate() {
        return currentDate.getAttribute("data-date");
    }

    private void clickNextDate(String timeStamp) {
        var timeStampNew = Long.parseLong(timeStamp) - 86400000;
        SelenideElement currentDate = $x("//td[@data-date='" + timeStampNew + "']");
        executeJavaScript("arguments[0].click()", currentDate);
    }

    default void setPurchaseDate() {
        sleep(1000);
        clickPurchaseDate();
        sleep(1000);
        var timeStamp = getCurrentDate();
        clickNextDate(timeStamp);
    }

    private void setPayment(String name) {
        fieldPayment.setValue(name);
    }

    private void setTransaction(String name) {
        fieldTransaction.setValue(name);
    }

    private void setNotes(String name) {
        fieldNotes.setValue(name);
    }

    default void clickSubmit() {
        executeJavaScript("arguments[0].click()", btnSubmit);
    }

    default String alertSuccess() {
        return alertBanner.getText();
    }

    default void refundForm(String email, String vin, String name, String lastName, String payment, String transaction, String note) {
        setEmail(email);
        setVin(vin);
        setReason();
        setName(name);
        setLastName(lastName);
        setPurchaseDate();
        setPayment(payment);
        setTransaction(transaction);
        setNotes(note);
        clickSubmit();
    }
}
