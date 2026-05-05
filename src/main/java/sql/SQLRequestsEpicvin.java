package sql;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.sleep;
import static constants.Constants.*;

public class SQLRequestsEpicvin {

    public String loadSqlQueryEpicvin(String fileName) throws Exception {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("sqlEpicvin/" + fileName)).toURI());
        return Files.readString(path);
    }

    public static ResultSet getUserByEmail(String email) throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_user_by_email.sql");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        return statement.executeQuery();
    }

    public static ResultSet getVinNotDB() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_vin_not_in_db.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getValidVinFromDB() throws Exception {
        Connection connection = SQLConnection.getConnection(PARSERS_DB_URL, PARSERS_DB_USER, PARSERS_DB_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_valid_vin_from_db.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getAuctionVinFromDB() throws Exception {
        Connection connection = SQLConnection.getConnection(PARSERS_DB_URL, PARSERS_DB_USER, PARSERS_DB_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_auction_vin.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getAnswer(String answer) throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_answer.sql");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, answer);
        return statement.executeQuery();
    }

    public static ResultSet getOrderID() throws Exception {
        sleep(2000);
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_orders_id.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getEndAt() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_endAt_date.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getNextPaymentDateForOldSubs() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_next_payment_date_for_old_subs.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getNextPaymentDateForNewSubs() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_next_payment_date_for_new_subs.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getDoubleSub() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_double_sub.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getActiveSubAfterDispute() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_active_sub_after_dispute.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLimitReportsByTrial() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_limit_report_by_trial_sub.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLimitReportsByAnnual() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_limit_report_by_annual_sub.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLimitReportsPerDayByFullSub() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_limit_reports_per_day_by_full_sub.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getMembershipRenewalMessage() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_membership_renewal_message.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getDoubleTrials() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_double_trials.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getChargeAttempts() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_charge_attempts.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLastRefundOnPaypal() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_last_refund_on_paypal.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLastRefundOnIXOpay() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_last_refund_on_ixopay.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLastRequestOnVinhub() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_last_request_on_vinhub.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getFailedPayPalAttempts() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_fail_paypal_attempts.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLastSuccessOrderYunoBraintree() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_last_yuno_braintree_order.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLastSuccessOrderYunoAirwallex() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_last_yuno_airwallex_order.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getDailyOrderRenew() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_daily_order_renew.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);

    } public static ResultSet getDailyOrderNew() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_daily_order_new.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getFailedYunoCharges() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_fail_yuno_charges.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLastNMVTIS() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_last_nmvtis.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getTrialCancellationRecords() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_trial_cancellation_by_user.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getDuplicateTrialCancellation() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_duplicate_cancellation_by_user.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getYesterdayCommission() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_affiliate_commissions_per_day.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getLastAffiliateCommission() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_last_affiliate_commissions.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getGoogleCaptchaScore() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_google_captcha_score.sql");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static List<SaleRecord> getAllRecordsByVin(String vin) throws Exception {
        List<SaleRecord> result = new ArrayList<>();
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_all_records_by_vin.sql");
        try (Connection connection = SQLConnection.getConnection(PARSERS_DB_URL, PARSERS_DB_USER, PARSERS_DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vin);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    SaleRecord record = new SaleRecord(
                            rs.getString("id"),
                            rs.getString("created_at"),
                            rs.getInt("count_images")
                    );
                    System.out.printf(
                            "Fetched from DB → id=%s, created_at=%s, count_images=%d%n",
                            record.id(), record.createdAt(), record.countImages()
                    );
                    result.add(record);
                }
            }
        }
        return result;
    }

    public record SaleRecord(String id, String createdAt, int countImages) {
    }
}