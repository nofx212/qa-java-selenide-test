package pages.epicvin.Precheck;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import interfaces.Dispute;
import interfaces.Precheck;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.openqa.selenium.By;
import pages.epicvin.Report.ReportPage;
import pages.epicvin.Report.SampleVinPage;

import java.io.IOException;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static payments.PayPalPaymentHandler.processPayPalEpicvinPayment;
import static payments.YunoPaymentHandler.*;


public class PrecheckPage extends SampleVinPage implements Precheck, Dispute {
    private final SelenideElement textGreat = $x("//h1[contains(@class,'trial-block')]");
    private final SelenideElement vin = $x("//p[contains(@class,'precheck-sample__vin')]");
    private final SelenideElement textSorry = $x("//span[contains(text(),'So sorry!')]");
    private final SelenideElement btnPrepaidReport = $(By.id("use-prepaid"));
    public static SelenideElement modalTrialPeriodNotice = $x("//div[@class='modal-body']//h2");
    private final SelenideElement insufficientError = $(By.id("pay-error-message"));
    private final SelenideElement errorBlock = $(By.id("pay-fail"));
    public static SelenideElement btnFundsInsufficient = $x("//button[@data-type='funds']");
    public static SelenideElement btnFundsActive = $x("//button[contains(@class,'prepaid-txt__use active')]");
    public static SelenideElement imgURL = $(By.id("img-sample"));
    public static SelenideElement inputEmail = $(By.id("email"));
    private final SelenideElement salvageAlertBanner = $x("//div[@class='salvage-banner']//h3");


    public String greatText() {
        return textGreat.getText();
    }

    public String getVin() {
        return vin.getText();
    }

    public String sorryText() {
        return textSorry.getText();
    }

    private void clickPrepaidReport() {
        executeJavaScript("scroll(0, 500)");
        if (btnPrepaidReport.isDisplayed()) {
            executeJavaScript("arguments[0].click()", btnPrepaidReport);
        } else {
            System.out.println("No Prepaid Reports. Please added");
        }
    }

    public ReportPage prepaidReport() {
        sleep(1000);
        clickFirstPack();
        price = getPrecheckPrice();
        clickPrepaidReport();
        modalWindowIsDisplay();
        return new ReportPage();
    }

    public String getPrecheckPrice() {
        sleep(2000);
        return precheckPriceCard.getText().replaceAll("[^\\d.]", "");
    }

    private String price;

    public ReportPage fullReportByCard(String name, String card, String date, String cvc, String zip) {
        clickFirstPack();
        price = getPrecheckPrice();
        checkbox(price);
        fillCardDataAndClickSecureCheckout(name, card, date, cvc, zip);
        checkPrecheckModal();
        return new ReportPage();
    }

    public ReportPage fullReportByFunds() {
        sleep(2000);
        clickFirstPack();
        price = getPrecheckPrice();
        clickPrepaidFunds();
        checkPrecheckModal();
        return new ReportPage();
    }

    private void setEmail(String email) {
        inputEmail.shouldBe(Condition.empty);
        inputEmail.setValue(email);
    }

    public ReportPage fullReportByCardUnloggedUser(String name, String email, String card, String date, String cvc, String zip) {
        clickFirstPack();
        setEmail(email);
        price = getPrecheckPrice();
        checkbox(price);
        fillCardDataAndClickSecureCheckout(name, card, date, cvc, zip);
        checkPrecheckModal();
        return new ReportPage();
    }

    public String getFullPrice() {
        return price;
    }

    public ReportPage fullReportByPayPal(String email, String password) {
        clickFirstPack();
        clickPayPal();
        checkbox(price);
        clickPayPalButton();
        checkPrecheckModal();
        processPayPalEpicvinPayment(email, password);
        return new ReportPage();
    }

    private void clickPrepaidFunds() {
        executeJavaScript("scroll(0, 500)");
        if (btnFundsActive.isDisplayed()) {
            executeJavaScript("arguments[0].click()", btnFundsActive);
        } else {
            System.out.println("No Prepaid Funds");
        }
    }

    public ReportPage fullSubscription(String name, String card, String date, String cvc, String zip, String code) {
        sleep(2000);
        clickFullPack();
        price = getPrecheckPrice();
        checkbox(price);
        processYunoPayment(name, card, date, cvc, zip, true, code);
        return new ReportPage();
    }

    public ReportPage fullSubscriptionUnloggedUser(String name, String email, String card, String date, String cvc, String zip, String code) {
        sleep(2000);
        clickFullPack();
        setEmail(email);
        sleep(1000);
        price = getPrecheckPrice();
        checkbox(price);
        processYunoPayment(name, card, date, cvc, zip, true, code);
        return new ReportPage();
    }

    public ReportPage fullSubscriptionByFunds() {
        sleep(2000);
        clickFullPack();
        price = getPrecheckPrice();
        clickPrepaidFunds();
        checkPrecheckModal();
        return new ReportPage();
    }

    public ReportPage trialSubscriptionUnloggedUser(String name, String email, String card, String date, String cvc, String zip, String code) {
        sleep(1000);
        setEmail(email);
        price = getPrecheckPrice();
        checkbox(price);
        processYunoPayment(name, card, date, cvc, zip, true, code);
        return new ReportPage();
    }

    public ReportPage trialSubsUnloggedUserPaymentValidation(String name, String email, String card, String date, String cvc, String zip) {
        sleep(1000);
        setEmail(email);
        price = getPrecheckPrice();
        checkbox(price);
        fillCardDataAndClickSecureCheckout(name, card, date, cvc, zip);
        return new ReportPage();
    }

    public ReportPage trialSubscription(String name, String card, String date, String cvc, String zip, String code) {
        sleep(1000);
        price = getPrecheckPrice();
        checkbox(price);
        //        clickSecondAgreeCheckBoxUS(price);
        processYunoPayment(name, card, date, cvc, zip, true, code);
        return new ReportPage();
    }

//    public ReportPage fullReportByCardWith3Ds(String name, String card, String date, String cvc, String zip) {
//        clickFirstPack();
//        checkbox(price);
//        price = getPrecheckPrice();
//        processIxopayPaymentWith3Ds(name, card, date, cvc, zip);
//        return new ReportPage();
//    }

//    public ReportPage trialSubscriptionByCardWith3Ds(String name, String card, String date, String cvc, String zip) {
//        price = getPrecheckPrice();
//        checkbox(price);
//        processIxopayPaymentWith3Ds(name, card, date, cvc, zip);
//        return new ReportPage();
//    }

    public ReportPage trialSubscriptionByCardWithFail3Ds(String name, String card, String date, String cvc, String zip) {
        price = getPrecheckPrice();
        checkbox(price);
        processYunoPaymentWithFail3Ds(name, card, date, cvc, zip);
        return new ReportPage();
    }

    public ReportPage chooseTrialByPayPal(String email, String password) {
        clickPayPal();
        checkbox(price);
        sleep(1000);
        clickPayPalButton();
        checkPrecheckModal();
        processPayPalEpicvinPayment(email, password);
        return new ReportPage();
    }

    public String getInsufficientError() {
        errorBlock.shouldBe(visible);
        System.out.println("Error block displayed");
        return insufficientError.shouldBe(visible).getText();
    }

    public boolean checkImageByContentType(SelenideElement imgElement) throws IOException {
        // 1. Ждём, пока картинка загрузится и в src будет нужный домен
        imgElement.shouldBe(visible).shouldHave(attributeMatching("src",
                "(https://photogenerate\\.vinchain\\.io/.*)|(https://epicvin\\.com/checkout/image-proxy.*)"));

        // 2. Получаем URL картинки
        String imageUrl = imgElement.getAttribute("src");
        System.out.println("IMG URL: " + imageUrl);

        // 3. Проверяем по Content-Type, что это изображение
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(imageUrl);

            return httpClient.execute(request, response -> {
                String contentType = response.getFirstHeader("Content-Type").getValue();
                System.out.println("Content-Type: " + contentType);
                EntityUtils.consume(response.getEntity());
                return contentType != null && contentType.toLowerCase().startsWith("image/");
            });
        }
    }

    public String getSalvageAlertBanner() {
        return salvageAlertBanner.getText();
    }
}