package sql;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static constants.Constants.*;

public class SQLRequestsEpicvin {

    public String loadSqlQueryEpicvin(String fileName) throws Exception {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("sqlEpicvin/" + fileName)).toURI());
        String query = Files.readString(path);
        String testUserIds = (TEST_USER_IDS == null || TEST_USER_IDS.isBlank()) ? "0" : TEST_USER_IDS;
        String excludedAuctions = (EXCLUDED_AUCTIONS == null || EXCLUDED_AUCTIONS.isBlank()) ? "''" : EXCLUDED_AUCTIONS;
        return query
                .replace("${TEST_USER_IDS}", testUserIds)
                .replace("${EXCLUDED_AUCTIONS}", excludedAuctions);
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

    public static ResultSet getLimitReportsPerDayByFullSub() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_limit_reports_per_day_by_full_sub.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

    public static ResultSet getDoubleTrials() throws Exception {
        Connection connection = SQLConnection.getConnection(DATABASE_URL_EPICVIN, DATABASE_USER, DATABASE_PASSWORD);
        String query = new SQLRequestsEpicvin().loadSqlQueryEpicvin("get_double_trials.sql");
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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