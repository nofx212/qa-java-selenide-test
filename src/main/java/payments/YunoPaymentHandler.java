package payments;

import com.codeborne.selenide.SelenideElement;
import org.opentest4j.TestAbortedException;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.*;
import static interfaces.Precheck.*;

/**
 * Единый класс для обработки всех платежей через Yuno (оплата по карте).
 * Обрабатывает логику с момента заполнения данных карты до завершения оплаты.
 */
public class YunoPaymentHandler {

    /**
     * Основной метод для обработки Yuno оплаты после заполнения данных карты.
     *
     * @param card        Номер карты
     * @param date        Дата (MM/YY)
     * @param cvc         CVC код
     * @param zip         ZIP код (опционально)
     * @param requires3Ds Требуется ли обработка 3DS аутентификации
     */
    public static void processYunoPayment(String name, String card, String date, String cvc, String zip, boolean requires3Ds, String code) {
        System.out.println("Начинаем обработку Yuno оплаты");
        fillCardData(name, card, date, cvc, zip);
        clickSecureCheckoutAndWait();

        if (requires3Ds) {
            System.out.println("3ds is required");
            process3DsAuthentication(code);
        }

        System.out.println("Yuno оплата успешно прошла");
    }

    /**
     * Публичный метод для заполнения данных карты и нажатия Secure Checkout.
     * Используется в специальных случаях, когда нужна кастомная обработка ошибок или 3DS.
     *
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc  CVC код
     * @param zip  ZIP код (опционально)
     */
    public static void fillCardDataAndClickSecureCheckout(String name, String card, String date, String cvc, String zip) {
        fillCardData(name, card, date, cvc, zip);
        clickSecureCheckoutAndWait();
    }

    /**
     * Заполняет данные карты в iframe Yuno.
     * Пока используется текущий набор локаторов Ixopay.
     *
     * @param name Имя владельца карты
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc  CVC код
     * @param zip  ZIP код (опционально)
     */
    private static void fillCardData(String name, String card, String date, String cvc, String zip) {
        System.out.println("Заполняем данные карты");

        System.out.println("Вводим имя владельца карты");
        executeJavaScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                cardholderName, name
        );

        switchTo().frame(ixopayYunoIframe);
        System.out.println("Вводим номер карты");
        inputCard.setValue(card);
        switchTo().defaultContent();

        switchTo().frame(yunoDateIframe);
        System.out.println("Вводим дату карты");
        inputMonthYear.setValue(date);
        switchTo().defaultContent();

        switchTo().frame(ixopayYunoCVCIframe);
        System.out.println("Вводим CVC");
        inputYunoCVC.setValue(cvc);
        switchTo().defaultContent();

        if (inputPostalCode.exists() && inputPostalCode.isDisplayed()) {
            System.out.println("Вводим ZIP");
            inputPostalCode.setValue(zip);
        }

        System.out.println("Данные карты заполнены");
    }

    /**
     * Нажимает Secure Checkout и ожидает обработки.
     */
    private static void clickSecureCheckoutAndWait() {
        sleep(2000);
        SelenideElement checkoutButton;
        if (btnPay.exists() && btnPay.isDisplayed()) {
            System.out.println("Нажимаем Secure Checkout");
            checkoutButton = btnPay;
        } else {
            System.out.println("Нажимаем Subscribe now (страница дилера)");
            checkoutButton = subscribeNow;
        }
        executeJavaScript("arguments[0].click()", checkoutButton);
        sleep(5000);
        System.out.println("Кнопка оплаты нажата");
        checkPrecheckModal();
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
     * Метод для обработки Yuno оплаты с 3DS аутентификацией.
     *
     * @param card Номер карты
     * @param date Дата (MM/YY)
     * @param cvc  CVC код
     * @param zip  ZIP код (опционально)
     */
    public static void processYunoPaymentWith3Ds(String name, String card, String date, String cvc, String zip, String code) {
        processYunoPayment(name, card, date, cvc, zip, true, code);
    }

    public static void processYunoPaymentWithFail3Ds(String name, String card, String date, String cvc, String zip) {
        System.out.println("Начинаем обработку Yuno оплаты");
        fillCardData(name, card, date, cvc, zip);
        clickSecureCheckoutAndWait();

        System.out.println("3ds is required");
        fail3DsAuthentication();

        System.out.println("Модальное окно Yuno 3Ds закрыто");
    }

    /**
     * Фэйлит 3DS аутентификацию после нажатия Secure Checkout.
     */
    private static void fail3DsAuthentication() {
        System.out.println("Обрабатываем 3DS аутентификацию");

        System.out.println("Переключаемся на iframe 3DS");
        switchTo().frame(iframeYuno3Ds);

        System.out.println("Переключаемся на внутренний iframe 3DS");
        switchTo().frame(iframeYuno3Ds1);

        System.out.println("Нажимаем кнопку Cancel 3DS");
        cancelYuno3Ds.shouldBe(enabled).click();

        switchTo().defaultContent();

    }

    /**
     * Обрабатывает 3DS аутентификацию после нажатия Secure Checkout.
     */
    private static void process3DsAuthentication(String code) {
        System.out.println("Обрабатываем 3DS аутентификацию");

        System.out.println("Переключаемся на iframe 3DS");
        switchTo().frame(iframeYuno3Ds);

        System.out.println("Переключаемся на внутренний iframe 3DS");
        switchTo().frame(iframeYuno3Ds1);

        sleep(2000);

        System.out.println("Вводим 3ds код");
        inputYuno3Ds.setValue(code).pressTab();

        System.out.println("Нажимаем кнопку завершения 3DS");
        completeYuno3Ds.shouldBe(enabled).click();

        check3DsErrors();

        switchTo().defaultContent();

        System.out.println("3DS аутентификация завершена");

        sleep(3000);
    }

    private static void check3DsErrors() {
        sleep(2000);
        Object text = executeJavaScript("return (document && document.body) ? document.body.innerText : '';");
        String frameText = text == null ? "" : text.toString();

        if (frameText.contains("Whitelabel Error Page") ||
                frameText.contains("Internal Server Error") ||
                frameText.contains("status=500")) {
            throw new TestAbortedException("Пропуск теста из-за ошибки Yuno 3DS: " + frameText);
        }

        System.out.println("Ошибки 3DS не найдены, продолжаем выполнение теста");
    }
}
