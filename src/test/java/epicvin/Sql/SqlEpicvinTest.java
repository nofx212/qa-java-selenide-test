package epicvin.Sql;

import annotations.epicvin.EpicvinSqlTest;
import clearAccount.ClearAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import pages.epicvin.Account.BillingPage;
import pages.epicvin.Account.SubscriptionsPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.ReportPage;
import sql.SQLRequestsEpicvin;

import java.sql.ResultSet;
import java.sql.SQLException;

import static constants.Constants.*;
import static constants.Constants.CVC;

public class SqlEpicvinTest extends base.BaseTest {

    @EpicvinSqlTest
    public void checkPaymentId() throws Exception {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL17, VALID_PASSWORD);
        ClearAccount.clearEpicvinMainPage(TRIAL_SUB_EPICVIN, FINGERPRINTS_EPICVIN, REPORTS_EPICVIN, FULL_SUB_EPICVIN);
        PrecheckPage precheckPage = mainPage.searchLotByVin(VALID_VIN);
        ReportPage reportPage = precheckPage.fullSubscription(TEST, CARD_NUMBER, MONTH_YEAR, CVC, ZIP, YUNO_3DS_CODE);
        if (reportPage.epicVinReport().contains("EpicVIN vehicle history report for")) {
            BillingPage billingPage = reportPage.clickBilling();
            var orderId = billingPage.getOrderId();
            System.out.println("ID ON BILLING PAGE: " + orderId);
            ResultSet resultSet = SQLRequestsEpicvin.getOrderID();
            try {
                if (resultSet.next()) {
                    String actualStripeID = resultSet.getString("paypal_pay_id");
                    System.out.println("ID IN DATABASE: " + actualStripeID);
                    assert actualStripeID.equals(orderId) : "❌Incorrect Payment ID";
                }
            } catch (SQLException e) {
                Assertions.fail("❌Payment ID not found in the database", e);
            } finally {
                SubscriptionsPage subscriptionsPage = mainPage.subscriptions(SubscriptionsPage.class);
                subscriptionsPage.cancelNowSubscription();
            }
        }
    }

    /**
     * Проверяет, что среди подписок со статусом, отличным от "canceled", нет записей, у которых дата окончания подписки (ends_at)
     * истекла более чем на 1 день относительно текущей даты.
     */
    @EpicvinSqlTest
    public void checkEndAtSub() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getEndAt()) {
            if (resultSet != null && resultSet.next()) {
                String count = resultSet.getString("count_result");
                if (count != null) {
                    assert count.equals("0") : "❌Test failed  = " + count;
                } else {
                    System.out.println("❌Count is null. Test failed.");
                }
            } else {
                System.out.println("ResultSet is null or empty. Test passed.");
            }
        }
    }

    /**
     * Проверяет отсутствие активных PayPal-подписок с просроченным next_payment_date (> 1 дня). Для старых подписок,
     * где scheme_id is null
     */
    @EpicvinSqlTest
    public void checkNextPaymentDateForOldSubs() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getNextPaymentDateForOldSubs()) {
            if (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("ids");
                assert "0".equals(id) : "❌Test failed. Found subscriptions with invalid next_payment_date:\n"
                        + (id != null ? id.replace(",", "\n") : "null");
            } else {
                System.out.println("ResultSet is null or empty. Test passed.");
            }
        }
    }

    /**
     * Проверяет отсутствие активных PayPal-подписок с просроченным next_payment_date (но не > 21 дня). Для новых подписок,
     * где scheme_id=1. Если статус подписки будет past_due, то через 21 день крон должен проставить canceled.
     */
    @EpicvinSqlTest
    public void checkNextPaymentDateForNewSubs() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getNextPaymentDateForNewSubs()) {
            if (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("ids");
                assert "0".equals(id) : "❌Test failed. Found subscriptions with invalid next_payment_date:\n"
                        + (id != null ? id.replace(",", "\n") : "null");
            } else {
                System.out.println("ResultSet is null or empty. Test passed.");
            }
        }
    }


    @EpicvinSqlTest
    public void checkDoubleSub() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getDoubleSub()) {
            if (resultSet != null) {
                boolean hasRows = false;
                while (resultSet.next()) {
                    hasRows = true;
                    System.out.println("Double subscriptions found.");
                    String userId = resultSet.getString("user_id");
                    if (userId == null) {
                        System.out.println("userId is null");
                    } else {
                        System.out.println("userId: " + userId);
                    }
                    userIdsStringBuilder.append(userId).append("\n");
                }
                if (!hasRows) {
                    System.out.println("No double subscriptions found. Test passed successfully.");
                } else {
                    assert userIdsStringBuilder.isEmpty() : "❌Test failed: \n" + userIdsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    @EpicvinSqlTest
    public void checkActiveSubAfterDispute() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getActiveSubAfterDispute()) {
            if (resultSet != null) {
                boolean ids = false;
                while (resultSet.next()) {
                    ids = true;
                    String id = resultSet.getString("user_id");
                    userIdsStringBuilder.append(id).append("\n");
                }
                if (!ids) {
                    System.out.println("No active subscriptions after dispute. Test passed successfully.");
                } else {
                    assert userIdsStringBuilder.isEmpty() : "❌Test failed: \n" + userIdsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    @EpicvinSqlTest
    public void checkUsersWithDoubleTrials() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getDoubleTrials()) {
            if (resultSet != null) {
                boolean users = false;
                while (resultSet.next()) {
                    users = true;
                    String id = resultSet.getString("user_id");
                    userIdsStringBuilder.append(id).append("\n");
                }
                if (!users) {
                    System.out.println("No users with double trials. Test passed successfully.");
                } else {
                    assert userIdsStringBuilder.isEmpty() : "❌Test failed: \n" + userIdsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    @EpicvinSqlTest
    public void checkUsersWithLimitReportsByTrialSub() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getLimitReportsByTrial()) {
            if (resultSet != null) {
                boolean users = false;
                while (resultSet.next()) {
                    users = true;
                    String id = resultSet.getString("user_id");
                    int reportCount = resultSet.getInt("cnt");
                    userIdsStringBuilder.append("User ID: ").append(id)
                            .append(", Reports: ").append(reportCount)
                            .append("\n");
                }
                if (!users) {
                    System.out.println("There are no users who have exceeded the report limit on a trial subscription > 15. Test passed successfully.");
                } else {
                    assert userIdsStringBuilder.isEmpty() : "❌Test failed: \n" + userIdsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    @EpicvinSqlTest
    public void checkUsersWithLimitReportsByAnnualSub() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getLimitReportsByAnnual()) {
            if (resultSet != null) {
                boolean users = false;
                while (resultSet.next()) {
                    users = true;
                    String id = resultSet.getString("user_id");
                    int reportCount = resultSet.getInt("cnt");
                    userIdsStringBuilder.append("User ID: ").append(id)
                            .append(", Reports: ").append(reportCount)
                            .append("\n");
                }
                if (!users) {
                    System.out.println("There are no users who have exceeded the report limit on a annual subscription > 100. Test passed successfully.");
                } else {
                    assert userIdsStringBuilder.isEmpty() : "❌Test failed: \n" + userIdsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    @EpicvinSqlTest
    public void checkUsersWithLimitReportsPerDayByFullSub() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getLimitReportsPerDayByFullSub()) {
            if (resultSet != null) {
                boolean users = false;
                while (resultSet.next()) {
                    users = true;
                    String id = resultSet.getString("user_id");
                    int reportCount = resultSet.getInt("cnt");
                    userIdsStringBuilder.append("User ID: ").append(id)
                            .append(", Reports: ").append(reportCount)
                            .append("\n");
                }
                if (!users) {
                    System.out.println("There are no users who have exceeded the report limit on a full subscription > 20 per day. Test passed successfully.");
                } else {
                    assert userIdsStringBuilder.isEmpty() : "❌Test failed: \n" + userIdsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    @EpicvinSqlTest
    public void checkMembershipRenewalMessageOnlyOnce() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getMembershipRenewalMessage()) {
            if (resultSet != null) {
                boolean users = false;
                while (resultSet.next()) {
                    users = true;
                    String id = resultSet.getString("user_id");
                    int reportCount = resultSet.getInt("cnt");
                    userIdsStringBuilder.append("User ID: ").append(id)
                            .append(", Sending message: ").append(reportCount)
                            .append("\n");
                }
                if (!users) {
                    System.out.println("There are no users who more than 1 sending message. Test passed successfully.");
                } else {
                    assert userIdsStringBuilder.isEmpty() : "❌Test failed: \n" + userIdsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    /**
     * Проверяет, что последний рефанд на PayPal был не больше 10 дней назад
     */
    @EpicvinSqlTest
    public void checkLastRefundsOnPaypal() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getLastRefundOnPaypal()) {
            if (resultSet != null && resultSet.next()) {
                int countDays = resultSet.getInt("cnt_days");
                System.out.println("Days since last refund: " + countDays);
                assert countDays < 10 : "❌ Test failed: Last refund was " + countDays + " days ago";
            } else {
                throw new AssertionError("❌ Test failed: No refund data found for Paypal.");
            }
        }
    }

    /**
     * Проверяет, что последний рефанд на Ixopay был не больше 10 дней назад
     */
    @EpicvinSqlTest
    public void checkLastRefundsOnIxopay() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getLastRefundOnIXOpay()) {
            if (resultSet != null && resultSet.next()) {
                int countDays = resultSet.getInt("cnt_days");
                System.out.println("Days since last refund: " + countDays);
                assert countDays < 10 : "❌ Test failed: Last refund was " + countDays + " days ago";
            } else {
                throw new AssertionError("❌ Test failed: No refund data found for IXOpay.");
            }
        }
    }

    /**
     * Проверяет, что послдений запрос к NMVTIS был не больше 3 часов назад
     */
    @EpicvinSqlTest
    public void checkLastNMVTIS() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getLastNMVTIS()) {
            if (resultSet != null && resultSet.next()) {
                int hours = resultSet.getInt("last_nmvtis");
                System.out.println("Hours since last NMVTIS: " + hours);
                assert hours < 3 : "❌ Test failed: Last NMVTIS was " + hours + " hours ago";
            } else {
                throw new AssertionError("❌ Test failed: No data.");
            }
        }
    }

    /**
     * Проверяет, что среди 10 последних записей в tbl_captcha_log не все записи имеют пустой score. Если все
     * 10 записей имеют score=null -> google api не работает.
     */
    @EpicvinSqlTest
    public void checkGoogleCaptchaApi() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getGoogleCaptchaScore()) {
            if (resultSet != null && resultSet.next()) {
                int score = resultSet.getInt("cnt_score");
                System.out.println("Among the last 10 records, null score count is: " + score);
                assert score < 10 : "❌ Test failed: All last 10 records have null score";
            } else {
                throw new AssertionError("❌ Test failed: No captcha log records found");
            }
        }
    }

    /**
     * Проверяет наличие записей об отмене триальной подписки (event unlim-pause) в tbl_user_logs,
     * для которых отсутствует соответствующая запись в tbl_subscription_trial_stop_log,
     * и выводит user_id таких пользователей. Падает если найдено больше 3-х пользователей.
     */
    @EpicvinSqlTest
    public void checkTrialSubscriptionCancellationByUser() throws Exception {
        StringBuilder userIdsStringBuilder = new StringBuilder();
        int count = 0;
        try (ResultSet resultSet = SQLRequestsEpicvin.getTrialCancellationRecords()) {
            if (resultSet != null) {
                while (resultSet.next()) {
                    count++;
                    userIdsStringBuilder.append(resultSet.getString("user_id")).append("\n");
                }
                assert count <= 3 : "❌ Test failed. More than 3 users missing: \n" + userIdsStringBuilder;

                System.out.println("✅ Users count OK. Found " + count + " user: \n" + userIdsStringBuilder);

            } else {
                System.out.println("❌ ResultSet is null. Test failed.");
            }
        }
    }

    /**
     * Проверяет наличие дублей отмен треальной подписки в таблице tbl_subscription_trial_stop_log
     * и возвращает subscription_id таких записей.
     */
    @EpicvinSqlTest
    public void checkDuplicateTrialCancellationByUser() throws Exception {
        StringBuilder duplicateSubsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getDuplicateTrialCancellation()) {
            if (resultSet != null) {
                boolean subscriptions = false;
                while (resultSet.next()) {
                    subscriptions = true;
                    String id = resultSet.getString("subscription_id");
                    duplicateSubsStringBuilder.append(id).append("\n");
                }
                if (!subscriptions) {
                    System.out.println("✅ No duplicates");
                } else {
                    assert duplicateSubsStringBuilder.isEmpty() : "❌Test failed.Duplicate subscriptions: \n" + duplicateSubsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    /**
     * Ищет unpaid invoices, у которых количество попыток списания (tbl_charges) больше, чем attempt_count + 1.
     */
    @EpicvinSqlTest
    @DisplayName("После правок нужно поменять кол-во попыток снова на 1, пока временно поставил 10")
    public void checkUnpaidInvoicesExceedingChargeAttempts() throws Exception {
        StringBuilder invoicesSubsStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getChargeAttempts()) {
            if (resultSet != null) {
                boolean invoices = false;
                while (resultSet.next()) {
                    invoices = true;
                    String id = resultSet.getString("id");
                    invoicesSubsStringBuilder.append(id).append("\n");
                }
                if (!invoices) {
                    System.out.println("✅ No invoices found. All unpaid invoices have valid charge attempts count.");
                } else {
                    assert invoicesSubsStringBuilder.isEmpty() : "❌Test failed.Invoices exceeding allowed charge attempts: \n" + invoicesSubsStringBuilder;
                }
            } else {
                System.out.println("❌ResultSet is null. Test failed.");
            }
        }
    }

    /**
     * Проверяет, что последний запрос к Vinhub был не больше 6 часов назад
     */
    @EpicvinSqlTest
    public void checkLastRequestOnVinhub() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getLastRequestOnVinhub()) {
            if (resultSet != null && resultSet.next()) {
                int countHours = resultSet.getInt("cnt_hours");
                System.out.println("Hours since last request: " + countHours);
                assert countHours <= 6 : "❌ Test failed: Last request was " + countHours + " hours ago";
            } else {
                throw new AssertionError("❌ Test failed: No requests to Vinhub.");
            }
        }
    }

    /**
     * Проверяет, что количество неуспешных PayPal-попыток оплаты после последнего успешного платежа не превышает 10
     */
    @EpicvinSqlTest
    public void checkFailedPaypalAttemptsAfterLastSuccess() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getFailedPayPalAttempts()) {
            if (resultSet != null && resultSet.next()) {
                int cntAttempts = resultSet.getInt("attempts_count_pp");
                System.out.println("Attempts since last fail: " + cntAttempts);
                assert cntAttempts <= 10 : "❌ Test failed: Last success payment was" + cntAttempts + " attempts ago";
            } else {
                throw new AssertionError("❌ Test failed: No attempts");
            }
        }
    }

    /**
     * Проверяет, что количесвтво неуспешных INITIAL-платежей после последнего успешного Yuno-платежа не превышает 10
     */
    @EpicvinSqlTest
    public void checkFailedYunoChargesAfterLastSuccess() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getFailedYunoCharges()) {
            if (resultSet != null && resultSet.next()) {
                int cntAttempts = resultSet.getInt("attempts_failed_charges");
                System.out.println("Attempts since last fail: " + cntAttempts);
                assert cntAttempts <= 10 : "❌ Test failed: Last success payment was" + cntAttempts + " attempts ago";
            } else {
                throw new AssertionError("❌ Test failed: No attempts");
            }
        }
    }

    /**
     * Получает кол-во комиссий за вчера и среднее кол-во комиссий за предыдущие 7 дней,
     * и возвращает результат только если кол-во комиссий за вчера меньше 40% от среднего значения.
     */
    @EpicvinSqlTest
    public void checkYesterdayAffiliateCommissionsBelowAverage() throws Exception {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (ResultSet resultSet = SQLRequestsEpicvin.getYesterdayCommission()) {
            if (resultSet != null) {
                boolean hasRows = false;
                while (resultSet.next()) {
                    hasRows = true;
                    int yesterday = (int) Math.round(resultSet.getDouble("yesterday_commission"));
                    int avg7 = (int) Math.round(resultSet.getDouble("avg_prev_7_days"));
                    resultStringBuilder
                            .append("Yesterday: ").append(yesterday)
                            .append(", Avg 7 Days: ").append(avg7)
                            .append("\n");
                }
                if (!hasRows) {
                    System.out.println("✅ Test passed. Yesterday's commission is NOT below 40% of average — no rows returned.");
                } else {
                    assert resultStringBuilder.isEmpty() : "❌ Test failed. Yesterday's commission is below 40% of average:\n" + resultStringBuilder;
                }
            } else {
                System.out.println("❌ ResultSet is null. Test failed.");
            }
        }
    }

    /**
     * Проверяет, что последняя комиссия была начислена не более 8 часов назад.
     */
    @EpicvinSqlTest
    public void checkLastAffiliateCommission() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getLastAffiliateCommission()) {
            if (resultSet != null && resultSet.next()) {
                int countHours = resultSet.getInt("cnt_hours");
                System.out.println("Hours since last commission: " + countHours);
                assert countHours <= 8 : "❌ Test failed: Last commission was " + countHours + " hours ago";
            } else {
                throw new AssertionError("❌ Test failed: No affiliate commissions.");
            }
        }
    }

    /**
     * Проверяет, что последняя успешная оплата через Yuno/Braintree была не больше 4 часов назад
     */
    @EpicvinSqlTest
    public void checkLastSuccessOrderOnYunoBraintree() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getLastSuccessOrderYunoBraintree()) {
            if (resultSet != null && resultSet.next()) {
                int countHours = resultSet.getInt("hours");
                System.out.println("Hours since last order: " + countHours);
                assert countHours <= 4 : "❌ Test failed: Last order was " + countHours + " hours ago";
            } else {
                throw new AssertionError("❌ Test failed: No orders.");
            }
        }
    }

    /**
     * Проверяет, что последняя успешная оплата через Yuno/Airwallex была не больше 4 часов назад
     */
    @EpicvinSqlTest
    public void checkLastSuccessOrderOnYunoAirwallex() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getLastSuccessOrderYunoAirwallex()) {
            if (resultSet != null && resultSet.next()) {
                int countHours = resultSet.getInt("hours");
                System.out.println("Hours since last order: " + countHours);
                assert countHours <= 4 : "❌ Test failed: Last order was " + countHours + " hours ago";
            } else {
                throw new AssertionError("❌ Test failed: No orders.");
            }
        }
    }

    /**
     * Проверяет падение суммы продлений за текущий день сравнивая с медианой за соедение 10 дней.
     * Тест падает только если падение превышает 20% от медианы.
     */
    @EpicvinSqlTest
    public void checkDailyOrderRenewDrop() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getDailyOrderRenew()) {
            if (resultSet != null && resultSet.next()) {
                int todayAmount = resultSet.getInt("today_amount");
                int median10Days = resultSet.getInt("median_last_10_days");
                double diffPercent = resultSet.getDouble("diff_percent");
                String status = resultSet.getString("status");

                String logMessage = "Сегодня: " + todayAmount +
                        ", Медиана: " + median10Days +
                        ", Отклонение: " + String.format("%.2f", diffPercent) + "%, Статус: " + status;
                System.out.println(logMessage);

                Assertions.assertEquals("OK", status,
                        "❌ Падение больше допустимого: " + logMessage);
            } else {
                throw new AssertionError("❌ Test failed: No orders");
            }
        }
    }

    /**
     * Проверяет падение суммы новых (триальных) ордеров за текущий день сравнивая с медианой за соедение 10 дней.
     * Тест падает только если падение превышает 20% от медианы.
     */
    @EpicvinSqlTest
    public void checkDailyOrderNewDrop() throws Exception {
        try (ResultSet resultSet = SQLRequestsEpicvin.getDailyOrderNew()) {
            if (resultSet != null && resultSet.next()) {
                int todayAmount = resultSet.getInt("today_amount");
                int median10Days = resultSet.getInt("median_last_10_days");
                double diffPercent = resultSet.getDouble("diff_percent");
                String status = resultSet.getString("status");

                String logMessage = "Сегодня: " + todayAmount +
                        ", Медиана: " + median10Days +
                        ", Отклонение: " + String.format("%.2f", diffPercent) + "%, Статус: " + status;
                System.out.println(logMessage);

                Assertions.assertEquals("OK", status,
                        "❌ Падение больше допустимого: " + logMessage);
            } else {
                throw new AssertionError("❌ Test failed: No orders");
            }
        }
    }
}
