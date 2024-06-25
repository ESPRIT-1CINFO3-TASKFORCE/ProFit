package Services;

import Entites.RoleEntity;
import Entites.UserEntity;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IServices<UserEntity> {

    private static Connection con = DataSource.getInstance().getCon();
    private static Statement ste;

    public UserService() {
        // Etablir la connexion BD
        try {
            ste = UserService.con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /*public static void insertRolesIfNotExist() throws SQLException {
        Connection conn = DataSource.getInstance().getConnection();
        if (conn == null) {
            throw new SQLException("La connexion à la base de données est nulle");
        }

        String query = "SELECT COUNT(*) FROM role";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        if (count == 0) {
            String insertQuery = "INSERT INTO role (id, libelle) VALUES "
                    + "(1, 'Admin'), "
                    + "(2, 'Coach'), "
                    + "(3, 'Nutritioniste'), "
                    + "(4, 'Adhérent')";
            PreparedStatement insertPst = conn.prepareStatement(insertQuery);
            insertPst.executeUpdate();
        }
    }*/

    // Méthode de connexion

    public static UserEntity login(String login, String mdp)  {
        String query = "SELECT * FROM users WHERE login = ? AND mdp = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, mdp);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                int n_tel = rs.getInt("n_tel");
                int poids = rs.getInt("poids");
                int longeur = rs.getInt("longeur");
                int note_c = rs.getInt("note_c");
                int note_n = rs.getInt("note_n");
                int roleId = rs.getInt("role"); // Récupérer l'ID du rôle depuis la colonne role

                // Charger le libellé du rôle (facultatif)
                String roleQuery = "SELECT libelle FROM roles WHERE id = ?";
                String roleLibelle = null;
                try (PreparedStatement roleStatement = con.prepareStatement(roleQuery)) {
                    roleStatement.setInt(1, roleId);
                    ResultSet roleRs = roleStatement.executeQuery();
                    if (roleRs.next()) {
                        roleLibelle = roleRs.getString("libelle");
                    }
                }

                RoleEntity role = new RoleEntity(roleId, null); // Vous devez charger le libellé du rôle dans votre entité RoleEntity

                UserEntity user = new UserEntity(id, age, poids, longeur, note_c, note_n, n_tel, nom, prenom, login, mdp, email, roleId);
                return user;
            } else {
                return null; // ou lever une exception
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode ajouter utilisateurs
    public void ajouter(UserEntity user) throws SQLException {
        String req = "INSERT INTO users (nom, prenom, age, email, n_tel, login, mdp, poids, longeur, note_c, note_n, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getN_tel());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.setString(7, user.getMdp());
            preparedStatement.setInt(8, user.getPoids());
            preparedStatement.setInt(9, user.getLongeur());
            preparedStatement.setInt(10, user.getNote_c());
            preparedStatement.setInt(11, user.getNote_n());
            preparedStatement.setInt(12, user.getRole());

            preparedStatement.executeUpdate();
        }
    }

    //  suppression des utilisateurs
    public void supprimer(UserEntity user) throws SQLException {
        String req = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, user.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    // Mise à jour
    public void update(UserEntity user) throws SQLException {
        String req = "UPDATE users SET nom = ?, prenom = ?, email = ?, mdp = ?, n_tel = ? role = ?, age = ?, WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom ());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMdp());
            preparedStatement.setInt(5, user.getN_tel());
            preparedStatement.setInt(6, user.getRole());
            preparedStatement.setInt(7, user.getAge());
            preparedStatement.setInt(8, user.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    @Override
    public UserEntity findbyId(int e) throws SQLException {
        return null;
    }

    @Override
    /*public List<UserEntity> readAll() throws SQLException {
        return null;
    }*/

    public List<UserEntity> readAll() throws SQLException {
        List<UserEntity> user1 = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                int n_tel = resultSet.getInt("n_tel");


               UserEntity user2 = new UserEntity(nom,prenom,age,email,n_tel);
                user1.add(user2);
            }
        }
        return user1;
    }

    // Trouver un utilisateur par ID

    public UserEntity findById(int id) throws SQLException {
        String req = "SELECT * FROM users WHERE id = ?";
        UserEntity user = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("age"),
                        resultSet.getInt("poids"),
                        resultSet.getInt("longeur"),
                        resultSet.getInt("note_c"),
                        resultSet.getInt("note_n"),
                        resultSet.getInt("n_tel"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("login"),
                        resultSet.getString("mdp"),
                        resultSet.getString("email"),
                        resultSet.getInt("role")
                        //new RoleEntity(resultSet.getInt("id"), resultSet.getString("libelle"))
                );
            }
        }
        return user;
    }

    // Afficher les utilisateurs
    public UserEntity readLastUser() throws SQLException {
        String req = "SELECT * FROM users ORDER BY id DESC LIMIT 1"; // Assuming 'id' is the primary key
        ResultSet resultSet = ste.executeQuery(req);

        if (resultSet.next()) {
            return new UserEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("age"),
                    resultSet.getInt("poids"),
                    resultSet.getInt("longeur"),
                    resultSet.getInt("note_c"),
                    resultSet.getInt("note_n"),
                    resultSet.getInt("n_tel"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("login"),
                    resultSet.getString("mdp"),
                    resultSet.getString("email"),
                    resultSet.getInt("role")
            );
        }
        return null;
    }


}
