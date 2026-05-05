package payments;

import com.codeborne.selenide.SelenideElement;
import org.opentest4j.TestAbortedException;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.*;
import static interfaces.Precheck.*;

/**
 * Unified handler for all Yuno card payments.
 * Covers the flow from filling card data to payment completion.
 */
public class YunoPaymentHandler {

    /**
     * Main method to process a Yuno payment after filling card data.
     *
     * @param card        Card number
     * @param date        Date (MM/YY)
     * @param cvc         CVC code
     * @param zip         ZIP code (optional)
     * @param requires3Ds Whether 3DS authentication is required
     */
    public static void processYunoPayment(String name, String card, String date, String cvc, String zip, boolean requires3Ds, String code) {
        System.out.println("Starting Yuno payment processing");
        fillCardData(name, card, date, cvc, zip);
        clickSecureCheckoutAndWait();

        if (requires3Ds) {
            System.out.println("3ds is required");
            process3DsAuthentication(code);
        }

        System.out.println("Yuno payment completed successfully");
    }

    /**
     * Public method to fill card data and click Secure Checkout.
     * Used in special cases when custom error or 3DS handling is required.
     *
     * @param card Card number
     * @param date Date (MM/YY)
     * @param cvc  CVC code
     * @param zip  ZIP code (optional)
     */
    public static void fillCardDataAndClickSecureCheckout(String name, String card, String date, String cvc, String zip) {
        fillCardData(name, card, date, cvc, zip);
        clickSecureCheckoutAndWait();
    }

    /**
     * Fills card data inside the Yuno iframe.
     * Currently uses the Ixopay locator set.
     *
     * @param name Cardholder name
     * @param card Card number
     * @param date Date (MM/YY)
     * @param cvc  CVC code
     * @param zip  ZIP code (optional)
     */
    private static void fillCardData(String name, String card, String date, String cvc, String zip) {
        System.out.println("Filling card data");

        System.out.println("Entering cardholder name");
        executeJavaScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                cardholderName, name
        );

        switchTo().frame(ixopayYunoIframe);
        System.out.println("Entering card number");
        inputCard.setValue(card);
        switchTo().defaultContent();

        switchTo().frame(yunoDateIframe);
        System.out.println("Entering card expiration date");
        inputMonthYear.setValue(date);
        switchTo().defaultContent();

        switchTo().frame(ixopayYunoCVCIframe);
        System.out.println("Entering CVC");
        inputYunoCVC.setValue(cvc);
        switchTo().defaultContent();

        if (inputPostalCode.exists() && inputPostalCode.isDisplayed()) {
            System.out.println("Entering ZIP");
            inputPostalCode.setValue(zip);
        }

        System.out.println("Card data has been filled");
    }

    /**
     * Clicks Secure Checkout and waits for processing.
     */
    private static void clickSecureCheckoutAndWait() {
        sleep(2000);
        SelenideElement checkoutButton;
        if (btnPay.exists() && btnPay.isDisplayed()) {
            System.out.println("Clicking Secure Checkout");
            checkoutButton = btnPay;
        } else {
            System.out.println("Clicking Subscribe now (dealer page)");
            checkoutButton = subscribeNow;
        }
        executeJavaScript("arguments[0].click()", checkoutButton);
        sleep(5000);
        System.out.println("Payment button clicked");
        checkPrecheckModal();
    }

    /**
     * Checks and handles the precheck modal window if it appears.
     */
    private static void checkPrecheckModal() {
        sleep(3000);
        if (modalWindow.isDisplayed()) {
            System.out.println("Modal window is displayed");
            modalWindowIsDisplay();
        }
    }

    /**
     * Handles the precheck modal window.
     */
    private static void modalWindowIsDisplay() {
        if (modalWindow.getText().contains("You have already purchased Vehicle History Report")) {
            btnContinueIfYouHaveReport.click();
            System.out.println("Modal: Continue if you have report");
        } else if (modalWindow.getText().contains("Are you sure you want to make a transaction and buy more reports?")) {
            btnContinueToPay.click();
            System.out.println("Modal: Continue to pay");
        } else {
            System.out.println("Modal window is not displayed or has different text");
        }
    }

    /**
     * Handles 3DS authentication after clicking Secure Checkout.
     */
    private static void process3DsAuthentication(String code) {
        System.out.println("Processing 3DS authentication");

        System.out.println("Switching to 3DS iframe");
        switchTo().frame(iframeYuno3Ds);

        System.out.println("Switching to inner 3DS iframe");
        switchTo().frame(iframeYuno3Ds1);

        sleep(2000);

        System.out.println("Entering 3ds code");
        inputYuno3Ds.setValue(code).pressTab();

        System.out.println("Clicking 3DS complete button");
        completeYuno3Ds.shouldBe(enabled).click();

        check3DsErrors();

        switchTo().defaultContent();

        System.out.println("3DS authentication completed");

        sleep(3000);
    }

    private static void check3DsErrors() {
        sleep(2000);
        Object text = executeJavaScript("return (document && document.body) ? document.body.innerText : '';");
        String frameText = text == null ? "" : text.toString();

        if (frameText.contains("Whitelabel Error Page") ||
                frameText.contains("Internal Server Error") ||
                frameText.contains("status=500")) {
            throw new TestAbortedException("Skipping test due to Yuno 3DS error: " + frameText);
        }

        System.out.println("No 3DS errors found, continuing test execution");
    }
}
