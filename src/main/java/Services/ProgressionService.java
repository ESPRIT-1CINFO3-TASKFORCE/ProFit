package Services;

import java.sql.*;

import Controllers.AjouterProgresController;
import Entites.ProgressionEntity;
import Entites.RegimeEntity;
import Entites.UserEntity;
import Utils.DataSource;
import com.mysql.cj.protocol.a.LocalDateTimeValueEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgressionService {
    private Connection connectio;

    public ProgressionService(Connection connectio) {
        this.connectio = connectio;
    }


    //********************************SINGLETON et CONNEXION BD****************************************

    private Connection con1= DataSource.getInstance().getCon();
    private List<ProgressionEntity> progressions;
    private Statement ste;
    // Déclaration de l'instance unique du singleton
    private static ProgressionService instance;

    // Méthode privée pour le constructeur privé du singleton
    public ProgressionService() {

    }

    // Méthode publique pour obtenir l'instance unique du singleton
    public static ProgressionService getInstance() {
        if (instance == null) {
            instance = new ProgressionService();
        }
        return instance;
    }

    // Obtenez la connexion à la base de données à partir de la source de données
    private Connection connection = DataSource.getInstance().getCon();

    //******************************************CRUD*************************************************

    // Méthode pour ajouter une progression à la base de données
    public int getIdByEmailAjoutProg(String email) {
        String idByEmail = "SELECT p.id FROM progression p JOIN users u ON p.id=u.id where email= ?";
        //String idByEmail="SELECT u.id FROM user u WHERE u.email = ?";
        int userId = -1;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit2_db", "root", "");
             PreparedStatement ps = connection.prepareStatement(idByEmail)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }
    public void AjouterProgression(ProgressionEntity progression) throws SQLException {
        String insertProgressionSQL = "INSERT INTO progression (id, poids, longueur, date_inscri) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit2_db", "root", "");
             PreparedStatement ps = connection.prepareStatement(insertProgressionSQL)) {

            ps.setInt(1, progression.getId());
            ps.setInt(2, progression.getPoids());
            ps.setDouble(3, progression.getLongueur());
            ps.setDate(4, java.sql.Date.valueOf(progression.getDate_inscri()));

            ps.executeUpdate();
        }
    }



   public List<ProgressionEntity> readAll() throws SQLException {
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT p.id_prg, p.id, p.poids, p.longueur, p.IMC, p.date_inscri, p.description, p.masse_musc, u.email FROM progression p JOIN users u ON p.id = u.id;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id_prg = resultSet.getInt("id_prg");
                int id = resultSet.getInt("id");
                int poids = resultSet.getInt("poids");
                double longueur = resultSet.getDouble("longueur");
                int IMC = resultSet.getInt("IMC");
                LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                String description = resultSet.getString("description");
                int masse_musc = resultSet.getInt("masse_musc");
                String email = resultSet.getString("email");
                ProgressionEntity progression = new ProgressionEntity(id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc,email);
                progressions.add(progression);



            }
        }
        return progressions;
    }



    //méthode pour supprimer un progrés

    public void deleteProgression(int id) throws SQLException {
        String supprimer = "DELETE FROM progression WHERE id_prg = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(supprimer)) {
            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Progression supprimée avec succès !");
            } else {
                System.out.println("Aucune progression trouvée avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //methode pour modifier un progres

    public  void modifierProgres(Long id, ProgressionEntity progressionDetails) throws SQLException {
        String query = "UPDATE progression SET poids = ?, longueur = ?, IMC = ?, Masse_musc = ?, date_inscri = ?, description = ? WHERE id_prg = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(query)) {
            preparedStatement.setInt(1, progressionDetails.getPoids());
            preparedStatement.setDouble(2, progressionDetails.getLongueur());
            preparedStatement.setInt(3, progressionDetails.getIMC());
            preparedStatement.setInt(4, progressionDetails.getMasse_musc());
            preparedStatement.setDate(5, java.sql.Date.valueOf(progressionDetails.getDate_inscri()));
            preparedStatement.setString(6, progressionDetails.getDescription());
            preparedStatement.setInt(7, progressionDetails.getId_prg());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Progression modifiée avec succès !");
            } else {
                System.out.println("Aucune progression trouvée avec l'ID à modifier : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    //Afficher progression par id client

    public List<ProgressionEntity> getProgressionsByClientId(int clientId) {
        List<ProgressionEntity> progressions = new ArrayList<>();

        String query = "SELECT id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc FROM progression WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_prg = resultSet.getInt("id_prg");
                int id_client = resultSet.getInt("id");
                int poids = resultSet.getInt("poids");
                double longueur = resultSet.getDouble("longueur");
                int IMC = resultSet.getInt("IMC");
                LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                String description = resultSet.getString("description");
                int masse_musc = resultSet.getInt("masse_musc");

                ProgressionEntity progression = new ProgressionEntity(id_prg, id_client, poids, longueur, IMC, date_inscri, description, masse_musc);
                progressions.add(progression);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progressions;
    }



    //**********************jointure de la classe progresion et user pour afficher nom prenom dans progression*************
    public List<ProgressionEntity> getProgressionsWithUserDetails()
    {
        String join="SELECT p.id, u.nom, u.prenom " +
                "FROM progression p " +
                "JOIN users u ON p.id = u.id";
        List<ProgressionEntity> progressions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit2_db", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(join)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                //int poids= rs.getInt("poids");
                //String description =rs.getString("description");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");

                ProgressionEntity progression = new ProgressionEntity(id,nom, prenom);
                progressions.add(progression);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progressions;


    }
    //*******************recupére poids et description***************
    public List<ProgressionEntity> getDESCPOIDSdeatils() {
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT poids, description FROM progression WHERE id = 1234 ORDER BY date_inscri DESC LIMIT 1"; // Ajustez selon votre table
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit2_db", "root", "");
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProgressionEntity progression = new ProgressionEntity();
                progression.setPoids(resultSet.getInt("poids"));
                progression.setDescription(resultSet.getString("description"));
                progressions.add(progression);

                // Ajoutez des messages de debug
                System.out.println("Poids from DB: " + resultSet.getDouble("poids"));
                System.out.println("Description from DB: " + resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progressions;
    }


    // Méthode pour chercher les progressions par id_client

    public List<ProgressionEntity> chercherParEmail(String email) throws SQLException {
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT p.poids, p.longueur, p.IMC, p.date_inscri, p.description, u.email FROM progression p JOIN users u ON p.id = u.id WHERE u.email =?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int poids = resultSet.getInt("poids");
                    double longueur = resultSet.getDouble("longueur");
                    int IMC = resultSet.getInt("IMC");
                    LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                    String description = resultSet.getString("description");
                    String retrievedEmail = resultSet.getString("email");

                    ProgressionEntity progression = new ProgressionEntity(poids, longueur, IMC, date_inscri, description, retrievedEmail);
                    progressions.add(progression);
                }
            }
        }
        return progressions;
    }







    private static void insererIMCDansBaseDeDonnees(int id, int poids, double longueur, double imc, String description, LocalDate date_inscri) {
        String DB_URL = "jdbc:mysql://localhost:3306/profit2_db";
        String DB_USER = "root";
        String DB_PASSWORD = "";

        String sql = "INSERT INTO progression (id, poids, longueur, IMC, description, date_inscri) VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("Valeurs pour la déclaration préparée: " + id + ", " + poids + ", " + longueur + ", " + imc + ", " + description + ", " + date_inscri);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, poids);
            pstmt.setDouble(3, longueur);
            pstmt.setDouble(4, imc);
            pstmt.setString(5, description);
            pstmt.setDate(6, java.sql.Date.valueOf(date_inscri));

            pstmt.executeUpdate();
            System.out.println("IMC inséré avec succès pour la progression avec l'ID " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void calculerEtAfficherIMC(int id_prg, int poids, double longueur, LocalDate date_inscri) {
        double imc = poids / (longueur * longueur);

        String description;
        if (imc < 18.5) {
            description = "Insuffisance pondérale";
        } else if (imc < 25) {
            description = "Poids normal";
        } else if (imc < 30) {
            description = "Surpoids";
        } else {
            description = "Obésité";
        }

        System.out.println("IMC calculé : " + imc);
        System.out.println("Description : " + description);

        insererIMCDansBaseDeDonnees(id_prg, poids, longueur, imc, description, date_inscri);
    }



    public List<ProgressionEntity> getProgressionsByEmail(String email) {
        String DB_URL = "jdbc:mysql://localhost:3306/profit2_db";
        String DB_USER = "root";
        String DB_PASSWORD = "";
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT p.*, u.nom, u.prenom, r.nom_regime, r.date_fin FROM progression p JOIN users u ON p.id = u.id JOIN régime r ON p.id = r.id_client WHERE u.email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProgressionEntity progression = new ProgressionEntity();
                progression.setPoids(rs.getInt("poids"));
                progression.setIMC(rs.getInt("imc"));
                progression.setDate_inscri(rs.getDate("date_inscri").toLocalDate());
                progression.setNom(rs.getString("nom"));
                progression.setPrenom(rs.getString("prenom"));
                progression.setNomregime(rs.getString("nom_regime"));
                progression.setDate_finR(rs.getDate("date_fin").toLocalDate());
                progressions.add(progression);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progressions;
    }



}






















