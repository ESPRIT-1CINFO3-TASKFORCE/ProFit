package Services;

import java.sql.*;

public class RegimeUserService {
    private Connection conn;



    public int getIdByEmail(String email) {
        String idByEmail = "SELECT id FROM user WHERE email = ?";
        int userId = -1;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit_db", "root", "");
            PreparedStatement ps = conn.prepareStatement(idByEmail);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userId;
    }
    public void insererDonnees( String dateDebut, String dateFin, String nomRegime,int id_client ) {
        Connection conn = null;
        PreparedStatement psUser = null;
        PreparedStatement psRegime = null;
        PreparedStatement psRegimeUser = null;
        ResultSet rsUser = null;
        ResultSet rsRegime = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit_db", "root", "");
            conn.setAutoCommit(false);

            /*// Insérer dans la table user
            String sqlUser = "INSERT INTO user (nom,prenom) VALUES (?, ?) ";
            psUser = conn.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
            psUser.setString(1, nomUser);
            psUser.setString(2, prenomUser);
            psUser.executeUpdate();
            rsUser = psUser.getGeneratedKeys();
            int userId = 0;
            if (rsUser.next()) {
                userId = rsUser.getInt(1);
            }*/

            // Insérer dans la table regime
            String sqlRegime = "INSERT INTO régime (date_debut, date_fin, nom_regime,id_client) VALUES (?, ?, ?, ?)";
            psRegime = conn.prepareStatement(sqlRegime, PreparedStatement.RETURN_GENERATED_KEYS);
            psRegime.setString(1, dateDebut);
            psRegime.setString(2, dateFin);
            psRegime.setString(3, nomRegime);
            psRegime.setInt(4, id_client);

            psRegime.executeUpdate();
            rsRegime = psRegime.getGeneratedKeys();
            int regimeId = 0;
            if (rsRegime.next()) {
                regimeId = rsRegime.getInt(1);
            }

            // Insérer dans la table regimeuser
            String sqlRegimeUser = "INSERT INTO regimuser (id_C, id_R) VALUES (?, ?)";
            psRegimeUser = conn.prepareStatement(sqlRegimeUser);
            psRegimeUser.setInt(1, id_client);
            psRegimeUser.setInt(2, regimeId);
            psRegimeUser.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (rsUser != null) rsUser.close();
                if (rsRegime != null) rsRegime.close();
                if (psUser != null) psUser.close();
                if (psRegime != null) psRegime.close();
                if (psRegimeUser != null) psRegimeUser.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
