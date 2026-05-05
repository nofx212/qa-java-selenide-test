package pages.epicvin.Price;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import org.openqa.selenium.By;
import pages.epicvin.Account.BillingPage;
import pages.epicvin.Account.DashboardPage;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.*;
import static payments.YunoPaymentHandler.processYunoPayment;
import static payments.YunoPaymentHandler.processYunoPaymentWith3Ds;

public class PricePage extends PrecheckPage implements Meta {
    private final SelenideElement titleH1 = $x("//h1[contains(@class,'prices__title')]");
    private final SelenideElement subTitle = $x("//p[@class='prices__text']");
    public static SelenideElement packet1 = $x("//select[@id='packetVariants']//option[@value='1']");
    public static SelenideElement packet4 = $x("//select[@id='packetVariants']//option[@value='3']");
    public static SelenideElement packet16 = $x("//select[@id='packetVariants']//option[@value='38']");
    public static SelenideElement trialPacket = $x("//p[contains(@class,'packets__name')]//span[2]");
    public static SelenideElement infoBlock = $(By.id("reportInfoLabel"));
    public static SelenideElement fullPacket = $x("//div[@class='packets']//article[3]//p");
    private final SelenideElement btnGetPackage = $(By.id("paymentLink"));
    private final SelenideElement getFirstPack = $x("//div[@data-id='1']");
    private final SelenideElement getTrialPack = $(By.id("buyTrialLink"));
    private final SelenideElement getFullPack = $(By.id("buyUnlimLink"));
    public static SelenideElement subModal = $x("//div[@id='isSubModal']//h2");

    public String titleH1() {
        return titleH1.getText();
    }

    public String subTitle() {
        return subTitle.getText();
    }

    public String checkPackages(SelenideElement packet) {
        return packet.getText();
    }

    private void buyFirstPack() {
        choseFirstPack();
        clickGetPackage();
    }

    private void choseFirstPack() {
        packet1.click();
    }

    private void clickGetPackage() {
        executeJavaScript("arguments[0].click()", btnGetPackage);
    }

    @Override
    public void clickFirstPack() {
        getFirstPack.click();
    }

    private String price;

    public DashboardPage paymentFirstPack(String name, String card, String date, String cvc, String zip, String code) {
        buyFirstPack();
        sleep(1000);
        checkbox(price);
        processYunoPayment(name, card, date, cvc, zip, false, code);
        sleep(1000);
        return new DashboardPage();
    }

    public void clickGetTrialPack() {
        executeJavaScript("arguments[0].click()", getTrialPack);
        sleep(3000);
    }

    public void clickGetFullPack() {
        executeJavaScript("arguments[0].click()", getFullPack);
        sleep(3000);
    }

    public BillingPage trialSubFromPrice(String name, String card, String date, String cvc, String zip, String code) {
        clickGetTrialPack();
        sleep(1000);
        price = getPrecheckPrice();
        checkbox(price);
        processYunoPaymentWith3Ds(name, card, date, cvc, zip, code);
        return new BillingPage();
    }

    public BillingPage fullSubFromSubscription(String name, String card, String date, String cvc, String zip, String code) {
        price = getPrecheckPrice();
        System.out.println("PRICE SUBSCRIPTION PAGE " + price);
        checkbox(price);
        processYunoPaymentWith3Ds(name, card, date, cvc, zip, code);
        return new BillingPage();
    }

    public BillingPage fullSubFromPrice(String name, String card, String date, String cvc, String zip, String code){
        clickGetFullPack();
        price = getPrecheckPrice();
        System.out.println("PRICE PRICE PAGE " + price);
        checkbox(price);
        processYunoPaymentWith3Ds(name, card, date, cvc, zip, code);
        return new BillingPage();
    }

    public String getPaymentPrice() {
        return price;
    }
}
