package services;

import entites.ProgressionEntity;
import utils.MySQLConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProgressionService {
    private Connection con1;

    // Déclaration de l'instance unique du singleton
    private static ProgressionService instance;

    // Méthode privée pour le constructeur privé du singleton

    public ProgressionService() {
        this.con1 = MySQLConnector.getInstance().getConnection();
    }

    // Méthode publique pour obtenir l'instance unique du singleton
    public static ProgressionService getInstance() {
        if (instance == null) {
            instance = new ProgressionService();
        }
        return instance;
    }

    // Méthode pour calculer et afficher la masse musculaire
    public static void calculerEtAfficherMasseMusc(int id, int poids, double longueur, LocalDate dateInscri, String gender) {
        double masseMusc;

        if ("homme".equalsIgnoreCase(gender)) {
            masseMusc = 0.407 * poids + 0.267 * longueur - 19.2;
        } else if ("femme".equalsIgnoreCase(gender)) {
            masseMusc = 0.252 * poids + 0.473 * longueur - 48.3;
        } else {
            throw new IllegalArgumentException("Genre non reconnu : " + gender);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatted = dateInscri.format(formatter);

        System.out.println("ID: " + id);
        System.out.println("Date d'inscription: " + dateFormatted);
        System.out.println("Genre: " + gender);
        System.out.println("Masse musculaire maigre: " + masseMusc + " kg");
    }

    // Méthode pour calculer et afficher l'IMC
    public static void calculerEtAfficherIMC(int id, int poids, double longueur, LocalDate dateInscri) {
        // Calcul de l'IMC
        double tailleM = longueur / 100; // Conversion de la taille en mètres
        double IMC = poids / (tailleM * tailleM);

        // Formatage de la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatted = dateInscri.format(formatter);

        // Affichage des résultats
        System.out.println("ID: " + id);
        System.out.println("Date d'inscription: " + dateFormatted);
        System.out.println("IMC: " + IMC);
    }

    // Méthode pour obtenir l'ID utilisateur par e-mail
    public int getIdByEmailAjoutProg(String email) {
        String idByEmail = "SELECT u.id FROM users u WHERE email = ?";
        int userId = -1;

        try (PreparedStatement ps = con1.prepareStatement(idByEmail)) {
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

    // Méthode pour ajouter une progression dans la base de données


    // Méthode pour lire toutes les progressions depuis la base de données
    public List<ProgressionEntity> lireToutesLesProgressions() throws SQLException {
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc, gender FROM progression";

        try (Statement statement = con1.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id_prg = resultSet.getInt("id_prg");
                int id = resultSet.getInt("id");
                int poids = resultSet.getInt("poids");
                double longueur = resultSet.getDouble("longueur");
                double IMC = resultSet.getDouble("IMC");
                LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                String description = resultSet.getString("description");
                int masse_musc = resultSet.getInt("masse_musc");
                String gender = resultSet.getString("gender");

                ProgressionEntity progression = new ProgressionEntity(id_prg, id, poids, longueur,(int) IMC, date_inscri, description, masse_musc, gender);
                progressions.add(progression);
            }
        }

        return progressions;
    }

    // Méthode pour supprimer une progression de la base de données par ID
    public void supprimerProgression(int id) throws SQLException {
        String deleteProgressionSQL = "DELETE FROM progression WHERE id_prg = ?";

        try (PreparedStatement ps = con1.prepareStatement(deleteProgressionSQL)) {
            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Progression supprimée avec succès !");
            } else {
                System.out.println("Aucune progression trouvée avec l'ID : " + id);
            }
        }
    }

    // Méthode pour modifier une progression dans la base de données
    public void modifierProgression(int id, ProgressionEntity progressionDetails) throws SQLException {
        String updateProgressionSQL = "UPDATE progression SET poids = ?, longueur = ?, IMC = ?, date_inscri = ?, description = ?, masse_musc = ?, gender = ? WHERE id_prg = ?";

        try (PreparedStatement ps = con1.prepareStatement(updateProgressionSQL)) {
            ps.setInt(1, progressionDetails.getPoids());
            ps.setDouble(2, progressionDetails.getLongueur());
            ps.setDouble(3, progressionDetails.getIMC());
            ps.setDate(4, Date.valueOf(progressionDetails.getDate_inscri()));
            ps.setString(5, progressionDetails.getDescription());
            ps.setDouble(6, progressionDetails.getMasse_musc());
            ps.setString(7, progressionDetails.getGender());
            ps.setInt(8, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Progression mise à jour avec succès !");
            } else {
                System.out.println("Aucune progression trouvée avec l'ID : " + id);
            }
        }
    }

    // Méthode pour rechercher les progressions par e-mail utilisateur
    public List<ProgressionEntity> chercherParEmail(String email) {
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT p.id_prg, p.id, p.poids, p.longueur, p.IMC, p.date_inscri, p.description, p.masse_musc, p.gender " +
                "FROM progression p " +
                "JOIN users u ON p.id = u.id " +
                "WHERE u.email = ?";

        try (PreparedStatement ps = con1.prepareStatement(query)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id_prg = rs.getInt("id_prg");
                    int id = rs.getInt("id");
                    int poids = rs.getInt("poids");
                    double longueur = rs.getDouble("longueur");
                    double IMC = rs.getDouble("IMC");
                    LocalDate date_inscri = rs.getDate("date_inscri").toLocalDate();
                    String description = rs.getString("description");
                    int masse_musc = rs.getInt("masse_musc");
                    String gender = rs.getString("gender");

                    ProgressionEntity progression = new ProgressionEntity(id_prg, id, poids, longueur, (int) IMC, date_inscri, description, masse_musc, gender);
                    progressions.add(progression);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progressions;
    }

    // Méthode pour afficher la progression d'un utilisateur par e-mail
    public ProgressionEntity afficherProgressionParEmail(String email) {
        ProgressionEntity progression = null;
        String query = "SELECT p.id_prg, p.id, p.poids, p.longueur, p.IMC, p.date_inscri, p.description, p.masse_musc, p.gender " +
                "FROM progression p " +
                "JOIN users u ON p.id = u.id " +
                "WHERE u.email = ?";

        try (PreparedStatement ps = con1.prepareStatement(query)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id_prg = rs.getInt("id_prg");
                    int id = rs.getInt("id");
                    int poids = rs.getInt("poids");
                    double longueur = rs.getDouble("longueur");
                    double IMC = rs.getDouble("IMC");
                    LocalDate date_inscri = rs.getDate("date_inscri").toLocalDate();
                    String description = rs.getString("description");
                    int masse_musc = rs.getInt("masse_musc");
                    String gender = rs.getString("gender");

                    progression = new ProgressionEntity(id_prg, id, poids, longueur, (int) IMC, date_inscri, description, masse_musc, gender);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progression;
    }

    public List<ProgressionEntity> readAll() throws SQLException {
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc, gender FROM progression";

        try (Statement statement = con1.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id_prg = resultSet.getInt("id_prg");
                int id = resultSet.getInt("id");
                int poids = resultSet.getInt("poids");
                double longueur = resultSet.getDouble("longueur");
                double IMC = resultSet.getDouble("IMC");
                LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                String description = resultSet.getString("description");
                int masse_musc = resultSet.getInt("masse_musc");
                String gender = resultSet.getString("gender");

                ProgressionEntity progression = new ProgressionEntity(id_prg, id, poids, longueur, (int) IMC, date_inscri, description, masse_musc, gender);
                progressions.add(progression);
            }
        }

        return progressions;
    }

    public ProgressionEntity afficherProgres(String mail) {
        ProgressionEntity progression = null;
        String query = "SELECT p.id_prg, p.id, p.poids, p.longueur, p.IMC, p.date_inscri, p.description, p.masse_musc, p.gender " +
                "FROM progression p " +
                "JOIN users u ON p.id = u.id " +
                "WHERE u.email = ?";

        try (PreparedStatement statement = con1.prepareStatement(query)) {
            statement.setString(1, mail);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id_prg = resultSet.getInt("id_prg");
                    int id = resultSet.getInt("id");
                    int poids = resultSet.getInt("poids");
                    double longueur = resultSet.getDouble("longueur");
                    int IMC = resultSet.getInt("IMC");
                    LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                    String description = resultSet.getString("description");
                    int masse_musc = resultSet.getInt("masse_musc");
                    String gender = resultSet.getString("gender");

                    progression = new ProgressionEntity(id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc, mail, gender);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progression;
    }

    public void modifierProgres(int id, ProgressionEntity selectedProgression) throws SQLException {
        String query = "UPDATE progression SET id = ?, poids = ?, longueur = ?, IMC = ?, date_inscri = ?, description = ?, masse_musc = ?, gender = ? WHERE id_prg = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(query)) {
            preparedStatement.setInt(1, selectedProgression.getId());
            preparedStatement.setInt(2, selectedProgression.getPoids());
            preparedStatement.setDouble(3, selectedProgression.getLongueur());
            preparedStatement.setDouble(4, selectedProgression.getIMC());
            preparedStatement.setDate(5, Date.valueOf(selectedProgression.getDate_inscri()));
            preparedStatement.setString(6, selectedProgression.getDescription());
            preparedStatement.setInt(7, selectedProgression.getMasse_musc());
            preparedStatement.setString(8, selectedProgression.getGender());
            preparedStatement.setInt(9, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La progression a été mise à jour avec succès !");
            } else {
                System.out.println("Aucune progression trouvée avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteProgression(int idPrg) throws SQLException {
        String deleteQuery = "DELETE FROM progression WHERE id_prg = ?";

        try (PreparedStatement ps = con1.prepareStatement(deleteQuery)) {
            ps.setInt(1, idPrg);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Progression with id_prg " + idPrg + " deleted successfully.");
            } else {
                System.out.println("No progression found with id_prg " + idPrg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public double calculerMasseMusc(int poids, double longueur, String genre) {
        double masseMusc = 0.0;

        // Exemple de formule simplifiée pour calculer la masse musculaire
        // (Ceci est une formule hypothétique et peut ne pas être scientifiquement correcte)
        if ("Homme".equalsIgnoreCase(genre)) {
            masseMusc = (poids * 0.3) + (longueur * 0.5);
        } else if ("Femme".equalsIgnoreCase(genre)) {
            masseMusc = (poids * 0.2) + (longueur * 0.4);
        } else {
            // Pour d'autres genres ou si le genre n'est pas spécifié
            masseMusc = (poids * 0.25) + (longueur * 0.45);
        }

        return masseMusc;
    }

    public void ajouterProgression(ProgressionEntity progression) {
        String sql = "INSERT INTO progression (id, poids, longueur, IMC, date_inscri, description, masse_musc, nom, prenom, email, nomRegime, date_finR, gender, muscle_mass) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        String url = "jdbc:mysql://localhost:3306/profit2_db";
        String username = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters
            stmt.setInt(1, progression.getId());
            stmt.setDouble(2, progression.getPoids());
            stmt.setDouble(3, progression.getLongueur());
            stmt.setDouble(4, progression.getIMC());
            stmt.setDate(5, Date.valueOf(progression.getDateInscri()));
            stmt.setString(6, progression.getDescription());
            stmt.setDouble(7, progression.getMasseMusc());
            stmt.setString(8, progression.getNom());
            stmt.setString(9, progression.getPrenom());
            stmt.setString(10, progression.getEmail());
            stmt.setString(11, progression.getNomRegime());
            stmt.setDate(12, Date.valueOf(progression.getDateFinR()));
            stmt.setString(13, progression.getGender());
            stmt.setDouble(14, progression.getMuscleMass());

            // Execute the statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle or log the exception appropriately
        }
    }


    public void ajouterProgression(String email, double longueur, double poids, Date date, String gender, double massMusc) {

    }
}
