package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private String url = "jdbc:mysql://localhost:3306/profit_bd";
    private String login = "root";
    private String pwd = "";

    private static DataSource data;
    private Connection con;

    private DataSource() {
        try {
            con = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie avec la base de données profit_bd.");

        } catch (SQLException e) {
            System.out.println("Impossible de se connecter à la base de données : " + e.getMessage());
        }
    }

    public Connection getCon() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(url, login, pwd);
                System.out.println("Reconnexion établie avec la base de données profit_bd.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la reconnexion à la base de données : " + e.getMessage());
        }
        return con;
    }

    public static DataSource getInstance() {
        if (data == null) {
            synchronized (DataSource.class) {
                if (data == null) {
                    data = new DataSource();
                }
            }
        }
        return data;
    }
}
