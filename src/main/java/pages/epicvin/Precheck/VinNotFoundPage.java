package pages.epicvin.Precheck;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.Precheck;
import org.openqa.selenium.By;
import pages.epicvin.Account.DashboardPage;

import static com.codeborne.selenide.Selenide.*;
import static payments.PayPalPaymentHandler.processPayPalEpicvinPayment;
import static payments.YunoPaymentHandler.fillCardDataAndClickSecureCheckout;

public class VinNotFoundPage implements Meta, Precheck {

    public static SelenideElement titleH1 = $x("//div[@id='precheck']//h1");
    public static SelenideElement block1 = $x("//p[@class='p-email']");
    public static SelenideElement block2 = $x("//p[@class='include__title']");
    public static SelenideElement block3 = $x("//div[@class='packets-no-vin']//h3//span");
    private final SelenideElement inputEmail = $(By.id("subscribe-vin"));
    private final SelenideElement btnSubmit = $(By.id("subscribe-btn"));
    public static SelenideElement successSubscribe = $(By.id("sub-success"));
    public final SelenideElement btnEdit = $x("//button[@class='edit-link btn-reset']");
    public final SelenideElement inputVin = $(By.id("vin-inp"));
    public final SelenideElement getFirstPacket = $x("//div[@data-id='1']");
    public final SelenideElement getPayPal = $x("//ul[contains(@class,'pay-types')]//li[2]");
    public final SelenideElement vinNotFoundPrice = $(By.id("tot-val"));

    public String vinNotFoundBlock(SelenideElement block) {
        return block.getText();
    }

    public void submitEmail(String email) {
        inputEmail.setValue(email);
        btnSubmit.click();
        sleep(3000);
    }

    private void clickEdit() {
        btnEdit.click();
    }

    private void setValidVin(String vin) {
        inputVin.setValue(vin);
    }

    public PrecheckPage changeVin(String vin) {
        clickEdit();
        setValidVin(vin);
        return new PrecheckPage();
    }

    @Override
    public void clickFirstPack() {
        getFirstPacket.click();
    }

    @Override
    public void clickPayPal() {
        executeJavaScript("arguments[0].click()", getPayPal);
    }

    private String price;

    public DashboardPage purchaseFirstPacketCreditCard(String name, String card, String date, String cvc, String zip ) {
        clickFirstPack();
        price = getVinNotFoundPrice();
        System.out.println("PRICE ON VIN NOT FOUND: " + price);
        checkbox(price);
        fillCardDataAndClickSecureCheckout(name, card, date, cvc, zip);
        sleep(2000);
        return new DashboardPage();
    }

    public DashboardPage purchaseFirstPacketPayPal(String email, String password) {
        clickFirstPack();
        clickPayPal();
        price = getVinNotFoundPrice();
        System.out.println("PRICE ON VIN NOT FOUND: " + price);
        checkbox(price);
        clickPayPalButton();
        checkPrecheckModal();
        sleep(2000);
        processPayPalEpicvinPayment(email, password);
        sleep(2000);
        return new DashboardPage();
    }

    public String getVinNotFoundPrice() {
        sleep(2000);
        return vinNotFoundPrice.getText().replaceAll("[^\\d.]", "");
    }

    public String getPaymentPrice() {
        return price;
    }
}
