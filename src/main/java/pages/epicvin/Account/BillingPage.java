package pages.epicvin.Account;

import com.codeborne.selenide.SelenideElement;
import interfaces.Menu;
import interfaces.Precheck;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BillingPage implements Menu<MyReportsPage, SubscriptionsPage>, Precheck {
    private final SelenideElement price = $x("//table[contains(@class,'table billing')]//tr[1]//td[3]//p[2]");
    private final SelenideElement orderId = $x("//table[contains(@class,'table billing')]//tr[1]//td[2]//p[2]");
    private static final SelenideElement addMethod = $(By.id("add-open"));
    private final SelenideElement btnAddCard = $x("//button[@data-type='cc']");
    private static final SelenideElement btnProceedWithPayPal = $x("//button[@data-type='pp']");
    private final SelenideElement btnBuyWithGP = $x("//button[@data-type='gp']");
    private final SelenideElement successAlert = $x("//div[@role='alert']");
    private final SelenideElement btnDelete = $x("//button[@class='payment-methods__delete']");
    private static final SelenideElement methodPayPal = $x("//ul[contains(@class,'pay-types')]//li[2]");
    private final SelenideElement methodGooglePay = $x("//ul[contains(@class,'pay-types')]//li[3]");


    public String billingPrice() {
        System.out.println("PRICE ON BILLING: " + price.getText().replaceAll("[^\\d.]|\\.00$", ""));
        return price.getText().replaceAll("[^\\d.]|\\.00$", "");
    }

    public String getOrderId() {
        sleep(1000);
        return orderId.getText();
    }

    public static void clickAddPaymentMethod() {
        executeJavaScript("arguments[0].click()", addMethod);
    }

    private void clickBtnAddCard() {
        sleep(3000);
        executeJavaScript("arguments[0].click()", btnAddCard);
    }

    public static void clickBtnProceedWithPayPal() {
        if (btnProceedWithPayPal.shouldBe(visible, Duration.ofSeconds(10)).exists()) {
            System.out.println("Button PayPal is displayed");
            executeJavaScript("arguments[0].click()", btnProceedWithPayPal);
        }
    }

    private void clickBtnBuyWithGP() {
        executeJavaScript("arguments[0].click()", btnBuyWithGP);
    }

    public String getSuccessAlert() {
        return successAlert.getText();
    }

    public static void clickPaymentMethodPayPal() {
        executeJavaScript("arguments[0].click()", methodPayPal);
    }

    private void clickPaymentMethodGooglePay() {
        executeJavaScript("arguments[0].click()", methodGooglePay);
    }

    public void addPaymentMethodCard(String card, String date, String cvv) {
        clickAddPaymentMethod();
        switchToIframe();
        setCardNumber(card);
        setMonthYear(date);
        setCVC(cvv);
        defaultIframe();
        clickBtnAddCard();
        sleep(5000);
        new BillingPage();
    }

    public void addPaymentMethodCard3Ds(String card, String date, String cvv){
        clickAddPaymentMethod();
        switchToIframe();
        setCardNumber(card);
        setMonthYear(date);
        setCVC(cvv);
        defaultIframe();
        clickBtnAddCard();
        switchTo3DsIframe();
        switchToComplete3DsIframe();
        clickComplete3Ds();
        defaultIframe();
        sleep(3000);
    }

    public static void addPaymentMethodPayPal() {
        clickAddPaymentMethod();
        clickPaymentMethodPayPal();
        clickBtnProceedWithPayPal();
    }

    public void addPaymentMethodGooglePay() {
        clickAddPaymentMethod();
        clickPaymentMethodGooglePay();
        clickBtnBuyWithGP();
    }

    public void clickDelete() {
        executeJavaScript("arguments[0].click()",  btnDelete);
    }

    public void deleteAllPaymentMethods() {
        var deleteAllPaymentMethods = $$x("//button[@class='payment-methods__delete']");
        while (!deleteAllPaymentMethods.isEmpty()) {
            System.out.println("Payment methods available for deletion = " + deleteAllPaymentMethods.size());
            clickDelete();
            sleep(1000);
            deleteAllPaymentMethods = $$x("//button[@class='payment-methods__delete']");
            System.out.println("Payment methods available after deletion = " + deleteAllPaymentMethods.size());
        }
        System.out.println("✅ All payment methods have been deleted.");
    }
}
