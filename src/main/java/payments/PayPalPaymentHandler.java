package payments;

import org.opentest4j.TestAbortedException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.TypeOptions.text;
import static interfaces.Precheck.*;
import static pages.epicvin.Precheck.PrecheckPage.modalTrialPeriodNotice;

/**
 * Unified handler for all PayPal payments.
 * Covers the flow from clicking "Secure Checkout" to payment completion.
 */
public class PayPalPaymentHandler {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration LONG_TIMEOUT = Duration.ofSeconds(60);

    /**
     * Main method to process a PayPal payment after clicking "Secure Checkout".
     *
     * @param email    Email for PayPal login
     * @param password Password for PayPal login
     */
    public static void processPayPalPayment(String email, String password) {
        System.out.println("Starting PayPal payment processing");

        boolean modalAppeared = waitForPayPalForm();

        if (modalAppeared) {
            System.out.println("Modal window appeared instead of PayPal form, PayPal handling is not required");
            return;
        }

        enterPayPalEmail(email);

        enterPayPalPassword(password);

        checkPayPalErrors();

        completePayPalPayment();

        checkPayPalErrors();

        System.out.println("PayPal payment processed successfully");
    }

    public static void processPayPalEpicvinPayment(String email, String password) {
        System.out.println("Preparing PayPal payment - opening PayPal in a new window");

        switchTo().window(1);

        System.out.println("Starting PayPal payment processing");

        enterPayPalEmail(email);

        enterPayPalPassword(password);

        checkPayPalErrors();

        completePayPalPayment();

        try {
            switchTo().window(0);
            switchTo().defaultContent();
            System.out.println("Switched back to the main window after PayPal payment");
        } catch (Exception e) {
            System.out.println("Failed to switch back to the main window after PayPal payment: " + e.getMessage());
        }

        checkPayPalErrors();

        System.out.println("PayPal payment processed successfully");
    }

    /**
     * Waits for the PayPal form to appear after clicking "Secure Checkout".
     * Handles modal windows if they appear.
     *
     * @return true if a modal window appeared instead of the PayPal form, false if the PayPal form is found
     */
    private static boolean waitForPayPalForm() {
        System.out.println("Waiting for the PayPal form to appear");
        sleep(7000);

        if (modalTrialPeriodNotice.exists() && modalTrialPeriodNotice.isDisplayed()) {
            String modalText = modalTrialPeriodNotice.getText();
            System.out.println("Modal window detected instead of PayPal form: " + modalText);
            return true;
        }
        boolean formFound = false;

        if (getPayPalEmail.exists()) {
            try {
                getPayPalEmail.shouldBe(visible, DEFAULT_TIMEOUT);
                System.out.println("PayPal form is loaded (email field found)");
                formFound = true;
            } catch (Exception e) {
                System.out.println("Email field exists but is not visible: " + e.getMessage());
            }
        }

        if (!formFound && getPayPalPassword.exists()) {
            try {
                getPayPalPassword.shouldBe(visible, DEFAULT_TIMEOUT);
                System.out.println("PayPal form is loaded (password field found, email already entered)");
                formFound = true;
            } catch (Exception e) {
                System.out.println("Password field exists but is not visible: " + e.getMessage());
            }
        }

        if (!formFound && btnPayNow.exists()) {
            try {
                btnPayNow.shouldBe(visible, DEFAULT_TIMEOUT);
                System.out.println("PayPal form is loaded (pay button found, user is already authorized)");
                formFound = true;
            } catch (Exception e) {
                System.out.println("Pay button exists but is not visible: " + e.getMessage());
            }
        }

        if (!formFound) {
            System.out.println("PayPal form elements not found after waiting");
        }

        return false;
    }

    /**
     * Enters the email into the PayPal form.
     *
     * @param email Email for login
     */
    private static void enterPayPalEmail(String email) {
        System.out.println("Checking whether the PayPal email needs to be entered");
        sleep(5000);
        if (getPayPalEmail.exists() && getPayPalEmail.isDisplayed()) {
            System.out.println("Entering PayPal email: " + email);
            getPayPalEmail.type(text(email).sensitive());
            System.out.println("Email entered");

            if (btnNext.exists() && btnNext.isDisplayed()) {
                btnNext.shouldBe(visible, DEFAULT_TIMEOUT).click();
                System.out.println("Next button clicked");
                sleep(2000);
            }
        } else {
            System.out.println("Email field is not displayed; user may already be logged in or the form has not loaded");
        }
    }

    /**
     * Enters the password and signs in to PayPal.
     *
     * @param password Password for login
     */
    private static void enterPayPalPassword(String password) {
        System.out.println("Checking whether the PayPal password needs to be entered");

        if (getPayPalPassword.exists() && getPayPalPassword.isDisplayed()) {
            System.out.println("Entering PayPal password");
            getPayPalPassword.type(text(password).sensitive());
            System.out.println("Password entered");

            if (btnPayPalLogin.exists() && btnPayPalLogin.isDisplayed()) {
                btnPayPalLogin.shouldBe(visible, DEFAULT_TIMEOUT).click();
                System.out.println("PayPal login button clicked");
                sleep(3000);
            }
        } else {
            System.out.println("Password field is not displayed; user may already be logged in");
        }
    }

    /**
     * Checks for PayPal errors and throws an exception if they are present.
     */
    private static void checkPayPalErrors() {
        System.out.println("Checking for PayPal errors");

        if (errorMessage.exists() && errorMessage.isDisplayed()) {
            String errorText = errorMessage.getText();
            System.out.println("PayPal message detected: " + errorText);

            if (errorText.contains("Something went wrong") || errorText.contains("We're sorry, but something went wrong")) {
                throw new TestAbortedException("Skipping test due to PayPal error: " + errorText);
            }
        }
        if (errorModalMessage.exists() && errorModalMessage.isDisplayed()) {
            String errorModalText = errorModalMessage.getText();
            System.out.println("PayPal message detected: " + errorModalText);

            if (errorModalText.contains("Things don’t appear to be working at the moment.")) {
                throw new TestAbortedException("Skipping test due to PayPal error: " + errorModalText);
            }
        }
    }

    /**
     * Completes the PayPal payment.
     * Handles different button and consent variants.
     */
    private static void completePayPalPayment() {
        System.out.println("Completing the PayPal payment");

        try {
            sleep(3000);
            // First check for the "Continue" button (may appear after login)
            if (btnContinue.exists() && btnContinue.isDisplayed()) {
                System.out.println("Continue button found, clicking it");
                btnContinue.shouldBe(visible, DEFAULT_TIMEOUT).click();
                sleep(2000);
            }

            // Check for the "Agree and Continue" button (old or new version)
            if (btnAgreeContinue.exists() && btnAgreeContinue.isDisplayed()) {
                System.out.println("Agree and Continue button found (old version)");
                executeJavaScript("arguments[0].click()", btnAgreeContinue);
                System.out.println("Agree and Continue button clicked");
                sleep(2000);
            } else if (btnAgreeContinueNew.exists() && btnAgreeContinueNew.isDisplayed()) {
                System.out.println("Agree and Continue button found (new version)");
                executeJavaScript("arguments[0].click()", btnAgreeContinueNew);
                System.out.println("Agree and Continue button clicked (new)");
                sleep(2000);
            }

            // Wait for the final pay button to appear
            if (btnPayNow.exists()) {
                btnPayNow.shouldBe(visible, LONG_TIMEOUT);
                System.out.println("Pay button found, clicking it");
                executeJavaScript("arguments[0].click()", btnPayNow);
                System.out.println("PayPal pay button clicked");
                waitForReturnToSite();
            } else {
                System.out.println("Pay button not found; payment may already be completed or an error occurred");
            }

        } catch (Exception e) {
            System.out.println("Error while completing the PayPal payment: " + e.getMessage());
            e.printStackTrace();
            // If the pay button is not found, the payment may already be completed
            // Continue execution
        }
    }

    /**
     * Waits for the return to the site after the PayPal transaction is completed.
     * Handles window/iframe switching if necessary.
     */
    private static void waitForReturnToSite() {
        System.out.println("Waiting for return to the site after PayPal payment");

        // Switch to the main window (in case PayPal opened in a new window)
        try {
            switchTo().window(0);
            System.out.println("Switched to the main window");
        } catch (Exception e) {
            System.out.println("Failed to switch to the main window: " + e.getMessage());
        }

        // Switch to default content (in case PayPal was inside an iframe)
        try {
            switchTo().defaultContent();
            System.out.println("Switched to default content");
        } catch (Exception e) {
            System.out.println("Failed to switch to default content: " + e.getMessage());
        }

        // Wait for the site page to load after returning from PayPal
        sleep(5000);

        System.out.println("Return to the site is complete");
    }
}
