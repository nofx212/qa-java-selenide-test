package pages.epicvin.Footer.Dealer;

import base.BaseSubscriptionPage;
import com.codeborne.selenide.SelenideElement;
import interfaces.Precheck;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static payments.YunoPaymentHandler.processYunoPayment;

public class DealerSubscriptionPage extends BaseSubscriptionPage implements Precheck {

    private final SelenideElement alert = $x("//p[@class='alert-v2__text']");
    private final SelenideElement inputVin = $(By.id("vin-inputpromo"));
    private final SelenideElement tabLicensePlate = $(By.id("checkPlateTabpromo"));
    private final SelenideElement inputPlate = $(By.id("plateNumberpromo"));
    private final SelenideElement inputState = $(By.id("stateCode"));
    private final SelenideElement btnStart = $x("//button[contains(@class,'find-vin__plate-btn')]");
    private final SelenideElement dealerLogo = $x("//a[@class='header-dealer__logo']");


    public String getAlert() {
        return alert.getText();
    }

    public void subscribe(String name, String card, String date, String cvc, String zip, String code) {
        processYunoPayment(name, card, date, cvc, zip, true, code);
    }

    public DealerReportPage setInputVin(String vin) {
        inputVin.setValue(vin);
        return new DealerReportPage();
    }

    public DealerReportPage setInputPlate(String plate) {
        tabLicensePlate.click();
        inputPlate.setValue(plate);
        inputState.selectOptionByValue("fl");
        btnStart.click();
        return new DealerReportPage();
    }

    public void clickDealerLogo() {
        dealerLogo.click();
    }
}
