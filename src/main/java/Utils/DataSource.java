package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private static DataSource instance = null;
    private Connection conn = null;

    private DataSource() {
    }

    private void init() throws SQLException {
        final String DB_URL = "jdbc:mysql://localhost:3306/profit_db";
        final String USER = "root";
        final String PASS = "";

        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        System.out.println("Connected to database");
    }

    public Connection getConnection() {
        return conn;
    }

    public static DataSource getInstance() throws SQLException {
        if (instance != null && !instance.getConnection().isClosed()) {
        } else {
            instance = new DataSource();
            instance.init();
        }
        return instance;
    }
}
