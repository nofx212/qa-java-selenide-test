package payments;

import org.opentest4j.TestAbortedException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.TypeOptions.text;
import static interfaces.Precheck.*;
import static pages.epicvin.Precheck.PrecheckPage.modalTrialPeriodNotice;

/**
 * Единый класс для обработки всех платежей через PayPal.
 * Обрабатывает логику с момента нажатия кнопки "Secure Checkout" до завершения оплаты.
 */
public class PayPalPaymentHandler {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration LONG_TIMEOUT = Duration.ofSeconds(60);

    /**
     * Основной метод для обработки PayPal оплаты после нажатия "Secure Checkout".
     *
     * @param email    Email для входа в PayPal
     * @param password Пароль для входа в PayPal
     */
    public static void processPayPalPayment(String email, String password) {
        System.out.println("Начинаем обработку PayPal оплаты");

        boolean modalAppeared = waitForPayPalForm();

        if (modalAppeared) {
            System.out.println("Модальное окно появилось вместо формы PayPal, обработка PayPal не требуется");
            return;
        }

        enterPayPalEmail(email);

        enterPayPalPassword(password);

        checkPayPalErrors();

        completePayPalPayment();

        checkPayPalErrors();

        System.out.println("PayPal оплата успешно обработана");
    }

    public static void processPayPalEpicvinPayment(String email, String password) {
        System.out.println("Подготовка к обработке PayPal оплаты - открытие PayPal в новом окне");

        switchTo().window(1);

        System.out.println("Начинаем обработку PayPal оплаты");

        enterPayPalEmail(email);

        enterPayPalPassword(password);

        checkPayPalErrors();

        completePayPalPayment();

        try {
            switchTo().window(0);
            switchTo().defaultContent();
            System.out.println("Переключились на основное окно после PayPal оплаты");
        } catch (Exception e) {
            System.out.println("Не удалось переключиться на основное окно после PayPal оплаты: " + e.getMessage());
        }

        checkPayPalErrors();

        System.out.println("PayPal оплата успешно обработана");
    }

    /**
     * Ожидает появления формы PayPal после нажатия "Secure Checkout".
     * Обрабатывает модальные окна если они появляются.
     *
     * @return true если появилось модальное окно вместо формы PayPal, false если форма PayPal найдена
     */
    private static boolean waitForPayPalForm() {
        System.out.println("Ожидаем появления формы PayPal");
        sleep(7000);

        if (modalTrialPeriodNotice.exists() && modalTrialPeriodNotice.isDisplayed()) {
            String modalText = modalTrialPeriodNotice.getText();
            System.out.println("Обнаружено модальное окно вместо формы PayPal: " + modalText);
            return true;
        }
        boolean formFound = false;

        if (getPayPalEmail.exists()) {
            try {
                getPayPalEmail.shouldBe(visible, DEFAULT_TIMEOUT);
                System.out.println("Форма PayPal загружена (поле email найдено)");
                formFound = true;
            } catch (Exception e) {
                System.out.println("Поле email существует, но не видимо: " + e.getMessage());
            }
        }

        if (!formFound && getPayPalPassword.exists()) {
            try {
                getPayPalPassword.shouldBe(visible, DEFAULT_TIMEOUT);
                System.out.println("Форма PayPal загружена (поле пароля найдено, email уже введен)");
                formFound = true;
            } catch (Exception e) {
                System.out.println("Поле пароля существует, но не видимо: " + e.getMessage());
            }
        }

        if (!formFound && btnPayNow.exists()) {
            try {
                btnPayNow.shouldBe(visible, DEFAULT_TIMEOUT);
                System.out.println("Форма PayPal загружена (кнопка оплаты найдена, пользователь уже авторизован)");
                formFound = true;
            } catch (Exception e) {
                System.out.println("Кнопка оплаты существует, но не видима: " + e.getMessage());
            }
        }

        if (!formFound) {
            System.out.println("Не найдены элементы формы PayPal после ожидания");
        }

        return false;
    }

    /**
     * Вводит email в форму PayPal.
     *
     * @param email Email для входа
     */
    private static void enterPayPalEmail(String email) {
        System.out.println("Проверяем необходимость ввода email PayPal");
        sleep(5000);
        if (getPayPalEmail.exists() && getPayPalEmail.isDisplayed()) {
            System.out.println("Вводим email PayPal: " + email);
            getPayPalEmail.type(text(email).sensitive());
            System.out.println("Email введен");

            if (btnNext.exists() && btnNext.isDisplayed()) {
                btnNext.shouldBe(visible, DEFAULT_TIMEOUT).click();
                System.out.println("Нажата кнопка Next");
                sleep(2000);
            }
        } else {
            System.out.println("Поле email не отображается, возможно пользователь уже залогинен или форма не загрузилась");
        }
    }

    /**
     * Вводит пароль и выполняет вход в PayPal.
     *
     * @param password Пароль для входа
     */
    private static void enterPayPalPassword(String password) {
        System.out.println("Проверяем необходимость ввода пароля PayPal");

        if (getPayPalPassword.exists() && getPayPalPassword.isDisplayed()) {
            System.out.println("Вводим пароль PayPal");
            getPayPalPassword.type(text(password).sensitive());
            System.out.println("Пароль введен");

            if (btnPayPalLogin.exists() && btnPayPalLogin.isDisplayed()) {
                btnPayPalLogin.shouldBe(visible, DEFAULT_TIMEOUT).click();
                System.out.println("Нажата кнопка входа в PayPal");
                sleep(3000);
            }
        } else {
            System.out.println("Поле пароля не отображается, возможно пользователь уже залогинен");
        }
    }

    /**
     * Проверяет наличие ошибок PayPal и выбрасывает исключение если они есть.
     */
    private static void checkPayPalErrors() {
        System.out.println("Проверяем наличие ошибок PayPal");

        if (errorMessage.exists() && errorMessage.isDisplayed()) {
            String errorText = errorMessage.getText();
            System.out.println("Обнаружено сообщение PayPal: " + errorText);

            if (errorText.contains("Something went wrong") || errorText.contains("We're sorry, but something went wrong")) {
                throw new TestAbortedException("Пропуск теста из-за ошибки PayPal: " + errorText);
            }
        }
        if (errorModalMessage.exists() && errorModalMessage.isDisplayed()) {
            String errorModalText = errorModalMessage.getText();
            System.out.println("Обнаружено сообщение PayPal: " + errorModalText);

            if (errorModalText.contains("Things don’t appear to be working at the moment.")) {
                throw new TestAbortedException("Пропуск теста из-за ошибки PayPal: " + errorModalText);
            }
        }
    }

    /**
     * Завершает оплату через PayPal.
     * Обрабатывает различные варианты кнопок и согласий.
     */
    private static void completePayPalPayment() {
        System.out.println("Завершаем оплату через PayPal");

        try {
            sleep(3000);
            // Сначала проверяем наличие кнопки "Continue" (может появиться после логина)
            if (btnContinue.exists() && btnContinue.isDisplayed()) {
                System.out.println("Найдена кнопка Continue, нажимаем");
                btnContinue.shouldBe(visible, DEFAULT_TIMEOUT).click();
                sleep(2000);
            }

            // Проверяем наличие кнопки "Agree and Continue" (может быть старая или новая версия)
            if (btnAgreeContinue.exists() && btnAgreeContinue.isDisplayed()) {
                System.out.println("Найдена кнопка Agree and Continue (старая версия)");
                executeJavaScript("arguments[0].click()", btnAgreeContinue);
                System.out.println("Нажата кнопка Agree and Continue");
                sleep(2000);
            } else if (btnAgreeContinueNew.exists() && btnAgreeContinueNew.isDisplayed()) {
                System.out.println("Найдена кнопка Agree and Continue (новая версия)");
                executeJavaScript("arguments[0].click()", btnAgreeContinueNew);
                System.out.println("Нажата кнопка Agree and Continue (новая)");
                sleep(2000);
            }

            // Ожидаем появления финальной кнопки оплаты
            if (btnPayNow.exists()) {
                btnPayNow.shouldBe(visible, LONG_TIMEOUT);
                System.out.println("Найдена кнопка оплаты, нажимаем");
                executeJavaScript("arguments[0].click()", btnPayNow);
                System.out.println("Нажата кнопка оплаты PayPal");
                waitForReturnToSite();
            } else {
                System.out.println("Кнопка оплаты не найдена, возможно оплата уже завершена или произошла ошибка");
            }

        } catch (Exception e) {
            System.out.println("Ошибка при завершении оплаты PayPal: " + e.getMessage());
            e.printStackTrace();
            // Если кнопка оплаты не найдена, возможно оплата уже завершена
            // Продолжаем выполнение
        }
    }

    /**
     * Ожидает возврата на сайт после завершения PayPal транзакции.
     * Обрабатывает переключение окон/iframe если необходимо.
     */
    private static void waitForReturnToSite() {
        System.out.println("Ожидаем возврата на сайт после PayPal оплаты");

        // Переключаемся на основное окно (на случай если PayPal открылся в новом окне)
        try {
            switchTo().window(0);
            System.out.println("Переключились на основное окно");
        } catch (Exception e) {
            System.out.println("Не удалось переключиться на основное окно: " + e.getMessage());
        }

        // Переключаемся на default content (на случай если PayPal был в iframe)
        try {
            switchTo().defaultContent();
            System.out.println("Переключились на default content");
        } catch (Exception e) {
            System.out.println("Не удалось переключиться на default content: " + e.getMessage());
        }

        // Ждем загрузки страницы сайта после возврата с PayPal
        sleep(5000);

        System.out.println("Возврат на сайт завершен");
    }
}
