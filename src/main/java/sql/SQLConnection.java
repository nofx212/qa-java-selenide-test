package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLConnection {

    private static final ThreadLocal<Map<String, Connection>> connections =
            ThreadLocal.withInitial(HashMap::new);

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        Map<String, Connection> threadConnections = connections.get();
        Connection connection = threadConnections.get(url);

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            threadConnections.put(url, connection);
            System.out.println("🌐 [" + Thread.currentThread().getName() + "] DB connection established");
        }
        return connection;
    }

    public static void closeAllConnections() throws SQLException {
        Map<String, Connection> threadConnections = connections.get();
        boolean closedAny = false;
        for (Connection connection : threadConnections.values()) {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                closedAny = true;
            }
        }
        threadConnections.clear();
        connections.remove();
        if (closedAny) {
            System.out.println("🔌 [" + Thread.currentThread().getName() + "] All DB connections closed");
        }
    }
}
