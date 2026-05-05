package epicvin.Sql;

import annotations.epicvin.EpicvinSqlTest;
import sql.SQLRequestsEpicvin;

import java.sql.ResultSet;

public class SqlEpicvinTest extends base.BaseTest {


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
}