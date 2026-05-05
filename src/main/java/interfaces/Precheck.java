package interfaces;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public interface Precheck {
    SelenideElement pagePrecheck = $(By.id("precheck"));
    SelenideElement btnPay = $(By.id("pay-btn"));
    SelenideElement subscribeNow = $(By.id("subscribe-btn"));
    SelenideElement btnPayPal = $x("//div[contains(@id,'zoid-paypal-buttons')]");
    SelenideElement getFirstPack = $x("//div[@class='packets']//button[@data-id='1']");
    SelenideElement ixopayYunoIframe = $x("//div[@id='cc-number']//iframe");
    SelenideElement ixopayYunoCVCIframe = $x("//div[@id='cc-csc']//iframe");
    SelenideElement yunoDateIframe = $x("//div[@id='cc-exp-date']//iframe");
    SelenideElement cardholderName = $(By.id("cc-name"));
    SelenideElement inputCard = $x("//input[contains(@name,'number')]");
    SelenideElement inputMonthYear = $x("//input[contains(@name,'exp')]");
    SelenideElement inputYunoCVC = $(By.name("cvv"));
    SelenideElement inputPostalCode = $(By.name("cc-zip"));
    SelenideElement getPayPal = $x("//button[contains(@class,'p-pp')]");
    SelenideElement getPayPalEmail = $(By.id("email"));
    SelenideElement btnNext = $(By.id("btnNext"));
    SelenideElement getPayPalPassword = $(By.id("password"));
    SelenideElement btnPayPalLogin = $(By.id("btnLogin"));
    SelenideElement btnPayNow = $x("//button[@data-id='payment-submit-btn']");
    SelenideElement btnAgreeContinue = $(By.id("confirmButtonTop"));
    SelenideElement btnAgreeContinueNew = $(By.id("consentButton"));
    SelenideElement btnContinue = $x("//div[@id='button']//button");
    SelenideElement country = $x("//dl[contains(@class,'precheck-sample')]//div[1]//dt");
    SelenideElement engine = $x("//dl[contains(@class,'precheck-sample')]//div[2]//dt");
    SelenideElement lastMileage = $x("//dl[contains(@class,'precheck-sample')]//div[3]//dt");
    SelenideElement lastPrice = $x("//dl[contains(@class,'precheck-sample')]//div[4]//dt");
    SelenideElement viewAllPackets = $x("//button[@data-id='1']");
    SelenideElement modalWindow = $x("//div[@class='modal-content']");
    SelenideElement btnContinueIfYouHaveReport = $(By.id("continue-pay-report"));
    SelenideElement btnContinueToPay = $(By.id("continue-pay"));
    SelenideElement errorMessage = $x("//div[contains(@class,'text-legacy-title-medium')]");
    SelenideElement errorModalMessage = $x("//p[@class='message']");
    SelenideElement iframeYuno3Ds = $x("//iframe[@class='Yuno-iframe-challenge__iframe css-coxqpp']");
    SelenideElement iframeYuno3Ds1 = $x("//iframe[@id='threeDSCReqIframe']");
    SelenideElement inputYuno3Ds = $x("//input[@id='otp']");
    SelenideElement completeYuno3Ds = $x("//button[@id='sendOtp']");
    SelenideElement precheckPriceCard = $x("//button[contains(@class,'cc-pay')]//span[@class='payment-btns__price']");
    SelenideElement clickAgree = $(By.name("agree"));
    SelenideElement clickFirstAgree = $(By.id("agreeFirst"));
    SelenideElement clickSecondAgree = $(By.id("agreeSecond"));
    SelenideElement firstCheckboxTextUS = $x("//div[contains(@class,'agree-option__first')]//span");
    SelenideElement secondCheckboxTextUS = $x("//div[@class='agree-option__second']//span");
    SelenideElement firstCheckboxTextROW = $x("//div[contains(@class,'agree-option')]//span");

    default String precheckPageAll() {
        return pagePrecheck.getText();
    }

    default void clickPayPalButton() {
        sleep(3000);
        btnPayPal.click();
    }

    default void clickViewAllPackets() {
        viewAllPackets.click();
    }

    default void clickFirstPack() {
        clickViewAllPackets();
        if (!precheckPriceCard.isDisplayed()) {
            System.out.println("Package on precheck is not selected");
            getFirstPack.click();
        }
    }

    default void checkbox(String price) {
        if (firstCheckboxTextUS.exists()) {
            System.out.println("Company: 'Epicvin Inc'");
            clickFirstAgreeCheckBoxUS();
            if(secondCheckboxTextUS.isDisplayed()) {
                System.out.println("Second checkbox is displayed");
                clickSecondAgreeCheckBoxUS(price);
            }
            return;
        }
        if (firstCheckboxTextROW.exists()) {
            System.out.println("Company: 'INFOSPHERE W.L.L.'");
            clickFirstAgreeCheckBoxRow(price);
        }
    }

    default void clickFirstAgreeCheckBoxUS() {
        String expectedUSText = "By clicking the checkbox, you represent that you are over 18 years of age and agree to " +
                "the EpicVIN Vehicle History Terms and Conditions, Privacy Policy and NMVTIS disclaimer, and you agree " +
                "to receive email from EpicVIN Vehicle History.";

        String expectedUSTextPricePage = "By clicking the checkbox, you represent that you are over 18 years of age and " +
                "agree to the EpicVIN Vehicle History Terms and Conditions, Privacy Policy, Refund Policy and " +
                "NMVTIS disclaimer, and you agree to receive email from EpicVIN Vehicle History.";

        String actualText = firstCheckboxTextUS.getText().trim();
        System.out.println("Actual text: " + firstCheckboxTextUS.getText());

        if (actualText.equals(expectedUSText)) {
            System.out.println("Text in first checkbox true ✅");
            executeJavaScript("arguments[0].click()", clickFirstAgree);
            return;
        }
        if (actualText.equals(expectedUSTextPricePage)) {
            System.out.println("Text in first checkbox true ✅");
            executeJavaScript("arguments[0].click()", clickFirstAgree);
            return;
        }
        System.out.println("Text in first checkbox for US false ❌");
    }

    default void clickFirstAgreeCheckBoxRow(String price) {
        String expectedRowText = "I agree to EpicVIN Terms and Conditions, Privacy Policy and NMVTIS disclaimer.";

        String expectedRowTextPackPricePage = "I agree to EpicVIN Terms and Conditions, Privacy Policy, Refund Policy and NMVTIS disclaimer.";

        String expectedRowTextFullSub = "I agree to EpicVIN Terms and Conditions, Privacy Policy and NMVTIS disclaimer. " +
                "Your subscription will continue at $" + price + " per month (plus any applicable sales tax). Cancel anytime " +
                "by visiting your account settings on our website or contact us for assistance. " +
                "If a payment fails, we may make one or more subsequent attempts to process the payment. " +
                "We may also attempt to reinitiate the payment at a lower temporary promotional amount, " +
                "and if successful that payment will satisfy your monthly payment obligation for that particular month.";

        String expectedRowTextFullSubPricePage = "I agree to EpicVIN Terms and Conditions, Privacy Policy, Refund Policy " +
                "and NMVTIS disclaimer. Your subscription will continue at $" + price + " per month (plus any applicable sales tax)." +
                " Cancel anytime by visiting your account settings on our website or contact us for assistance. " +
                "If a payment fails, we may make one or more subsequent attempts to process the payment. " +
                "We may also attempt to reinitiate the payment at a lower temporary promotional amount, and if successful " +
                "that payment will satisfy your monthly payment obligation for that particular month.";

        String expectedRowTextTrialSubPricePage = "I agree to EpicVIN`s Terms and Conditions, Privacy Policy, Refund Policy " +
                "and NMVTIS Disclaimer. Your 3-day trial, which includes up to 5 reports, begins immediately. " +
                "By starting your trial, you acknowledge that your subscription will automatically renew at $99.99 per month " +
                "(plus applicable taxes) after the trial period ends unless you cancel. You can cancel anytime before the " +
                "trial ends to avoid charges. Manage your subscription through your account settings or contact us for assistance. " +
                "If a payment fails, we may make one or more subsequent attempts to process the payment. We may also attempt " +
                "to reinitiate the payment at a lower temporary promotional amount, and if successful that payment will " +
                "satisfy your monthly payment obligation for that particular month.";

        String expectedRowTextTrialSub = "I agree to EpicVIN`s Terms and Conditions, Privacy Policy, and NMVTIS Disclaimer. " +
                "Your 3-day trial, which includes up to 5 reports, begins immediately. By starting your trial, you acknowledge " +
                "that your subscription will automatically renew at $99.99 per month (plus applicable taxes) after the " +
                "trial period ends unless you cancel. You can cancel anytime before the trial ends to avoid charges. " +
                "Manage your subscription through your account settings or contact us for assistance. " +
                "If a payment fails, we may make one or more subsequent attempts to process the payment. " +
                "We may also attempt to reinitiate the payment at a lower temporary promotional amount, and " +
                "if successful that payment will satisfy your monthly payment obligation for that particular month.";

        String actualText = firstCheckboxTextROW.getText().trim();
        System.out.println("Actual text: " + firstCheckboxTextROW.getText());
        if (actualText.equals(expectedRowText) ||
                actualText.equals(expectedRowTextFullSub) ||
                actualText.equals(expectedRowTextTrialSub)) {
            System.out.println("Text in first checkbox on Precheck Page for ROW version true ✅");
            executeJavaScript("arguments[0].click()", clickAgree);
            return;
        }
        if (actualText.equals(expectedRowTextPackPricePage) ||
                actualText.equals(expectedRowTextFullSubPricePage) ||
                actualText.equals(expectedRowTextTrialSubPricePage)
        ) {
            System.out.println("Text in first checkbox on Price Page for ROW version true ✅");
            executeJavaScript("arguments[0].click()", clickAgree);
            return;
        }
        System.out.println("Text in first checkbox for ROW false ❌");
    }

    default void clickSecondAgreeCheckBoxUS(String price) {
        String expectedText = "By clicking the checkbox, you represent that you understand and agree to our Membership Policy. " +
                "By starting your membership, you acknowledge that your subscription will automatically renew at $" + price + " per month (plus applicable taxes) unless you cancel. " +
                "You can cancel anytime before the trial ends to avoid charges. " +
                "Manage your subscription through your account settings or contact us for assistance. " +
                "If a payment fails, we may make one or more subsequent attempts to process the payment. We may also attempt to reinitiate the payment at a lower temporary promotional amount, and if successful that payment will satisfy your monthly payment obligation for that particular month.";

        String expectedTextTrial = "By clicking the checkbox, you represent that you understand and agree to our Membership Policy. " +
                "Your 3-day trial, which includes up to 5 reports, begins immediately. " +
                "By starting your trial, you acknowledge that your subscription will automatically renew at $49.99 per month (plus applicable taxes) after the trial period ends unless you cancel. " +
                "You can cancel anytime before the trial ends to avoid charges. " +
                "Manage your subscription through your account settings or contact us for assistance. " +
                "If a payment fails, we may make one or more subsequent attempts to process the payment. We may also attempt to reinitiate the payment at a lower temporary promotional amount, and if successful that payment will satisfy your monthly payment obligation for that particular month.";

        String actualText = secondCheckboxTextUS.getText().trim();

        if (actualText.equals(expectedText) || actualText.equals(expectedTextTrial)) {
            System.out.println("Text in second checkbox true ✅");
            executeJavaScript("arguments[0].click()", clickSecondAgree);
        } else {
            System.out.println("Text in second checkbox false ❌");
            System.out.println("Actual text: " + actualText);
        }
    }

    default void clickPayPal() {
        executeJavaScript("arguments[0].click()", getPayPal);
    }

    default String textVehicleEngine() {
        return engine.getText();
    }

    default String textLastOdometer() {
        return lastMileage.getText();
    }

    default String textCountry() {
        return country.getText();
    }

    default String textLastSellingPrice() {
        return lastPrice.getText();
    }

    default void modalWindowIsDisplay() {
        sleep(3000);
        if (modalWindow.getText().contains("You have already purchased Vehicle History Report")) {
            btnContinueIfYouHaveReport.click();
            System.out.println("if");
        } else if (modalWindow.getText().contains("Are you sure you want to make a transaction and buy more reports?")) {
            btnContinueToPay.click();
            System.out.println("else");
        } else {
            System.out.println("modal window is not displayed");
        }
    }

    default void checkPrecheckModal() {
        sleep(3000);
        if (modalWindow.isDisplayed()) {
            System.out.println("Modal window is displayed");
            modalWindowIsDisplay();
        }
    }
}
