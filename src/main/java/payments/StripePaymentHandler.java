package payments;

import org.opentest4j.TestAbortedException;

import static com.codeborne.selenide.Selenide.*;
import static interfaces.Precheck.*;

/**
 * Единый класс для обработки всех платежей через Stripe (оплата по карте).
 * Обрабатывает логику с момента заполнения данных карты до завершения оплаты.
 */
public class StripePaymentHandler {


    /**
     * Основной метод для обработки Stripe оплаты после заполнения данных карты.
     *
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc  CVC код
     * @param zip  ZIP код (опционально)
     */
    public static void processStripePayment(String card, String date, String cvc, String zip) {
        processStripePayment(card, date, cvc, zip, false);
    }

    /**
     * Основной метод для обработки Stripe оплаты после заполнения данных карты.
     *
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc  CVC код
     * @param zip  ZIP код (опционально)
     * @param requires3Ds Требуется ли обработка 3DS аутентификации
     */
    public static void processStripePayment(String card, String date, String cvc, String zip, boolean requires3Ds) {
        System.out.println("Начинаем обработку Stripe оплаты");
        fillCardData(card, date, cvc, zip);
        clickSecureCheckoutAndWait();

        checkStripeErrors();

        if (requires3Ds) {
            process3DsAuthentication();
            checkStripeErrors();
        }

        System.out.println("Stripe оплата успешно прошла");
    }

    /**
     * Публичный метод для заполнения данных карты и нажатия Secure Checkout.
     * Используется в специальных случаях, когда нужна кастомная обработка ошибок или 3DS.
     *
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc CVC код
     * @param zip ZIP код (опционально)
     */
    public static void fillCardDataAndClickSecureCheckout(String card, String date, String cvc, String zip) {
        fillCardData(card, date, cvc, zip);
        clickSecureCheckoutAndWait();
    }

    /**
     * Заполняет данные карты в iframe Stripe.
     *
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc CVC код
     * @param zip ZIP код (опционально)
     */
    private static void fillCardData(String card, String date, String cvc, String zip) {
        System.out.println("Заполняем данные карты");

        switchTo().frame(stripeIframe);

        System.out.println("Вводим номер карты");
        executeJavaScript(("arguments[0].scrollIntoView();"), inputCard);
        inputCard.setValue(card);

        System.out.println("Вводим дату карты");
        inputMonthYear.setValue(date);

        System.out.println("Вводим CVC");
        inputCVC.setValue(cvc);

        if (inputZIP.exists() && inputZIP.isDisplayed()) {
            System.out.println("Вводим ZIP");
            inputZIP.setValue(zip);
        }

        switchTo().defaultContent();

        System.out.println("Данные карты заполнены");
    }

    /**
     * Нажимает Secure Checkout и ожидает обработки.
     */
    private static void clickSecureCheckoutAndWait() {
        System.out.println("Нажимаем Secure Checkout");
        executeJavaScript("arguments[0].click()", btnPay);
        sleep(5000);
        System.out.println("Secure Checkout нажата");
        checkPrecheckModal();
        sleep(3000);
    }

    /**
     * Проверяет и обрабатывает модальное окно precheck если оно появилось.
     */
    private static void checkPrecheckModal() {
        sleep(3000);
        if (modalWindow.isDisplayed()) {
            System.out.println("Modal window is displayed");
            modalWindowIsDisplay();
        }
    }

    /**
     * Обрабатывает модальное окно precheck.
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
     * Проверяет наличие ошибок Stripe и обрабатывает их.
     */
    private static void checkStripeErrors() {
        System.out.println("Проверяем наличие ошибок Stripe");
        sleep(5000);

        if (errorCard.exists() && errorCard.isDisplayed()) {
            String errorText = errorCard.getText();
            System.out.println("Обнаружена ошибка карты: " + errorText);

            if (errorText.contains("This object cannot be accessed right now because another API request or Stripe process is currently accessing it")) {
                throw new TestAbortedException("Пропуск теста из-за ошибки Stripe: " + errorText);
            }
        }

        System.out.println("Ошибок Stripe не обнаружено");
    }

    /**
     * Метод для обработки Stripe оплаты с 3DS аутентификацией.
     *
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc CVC код
     * @param zip ZIP код (опционально)
     */
    public static void processStripePaymentWith3Ds(String card, String date, String cvc, String zip) {
        processStripePayment(card, date, cvc, zip, true);
    }

    /**
     * Обрабатывает 3DS аутентификацию после нажатия Secure Checkout.
     */
    private static void process3DsAuthentication() {
        System.out.println("Обрабатываем 3DS аутентификацию");

        sleep(7000);

        System.out.println("Переключаемся на iframe 3DS");
        switchTo().frame(iframe3Ds);

        System.out.println("Переключаемся на внутренний iframe 3DS");
        switchTo().frame(iframeComplete3Ds);

        System.out.println("Нажимаем кнопку завершения 3DS");
        executeJavaScript("arguments[0].click()", complete3Ds);

        switchTo().defaultContent();
        sleep(2000);

        System.out.println("3DS аутентификация завершена");
    }

}
