package Services;

import Entites.UserEntity;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IServices<UserEntity> {

    private static Statement ste;

    public UserService(Connection connection) {
    }

    public UserService() {

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
         try {
             PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, mdp);
            ResultSet rs = preparedStatement.executeQuery();
             System.out.println("Here: "+login);
            if (rs.next()) {
                UserEntity usr=new UserEntity();
                usr.setId(rs.getInt("id"));
                usr.setNom(rs.getString("nom"));
                usr.setPrenom(rs.getString("prenom"));
                usr.setAge(rs.getInt("age"));
                usr.setEmail(rs.getString("email"));
                usr.setN_tel(rs.getInt("n_tel"));
                usr.setPoids(rs.getInt("poids"));
                usr.setLongeur(rs.getInt("longeur"));
                usr.setNote_c(rs.getInt("note_c"));
                usr.setNote_n(rs.getInt("note_n"));
                usr.setRole(rs.getString("role"));

                System.out.println(usr.getRole());
                return usr;
            } else {
                System.out.println("Nothing found");
                return null; // ou lever une exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode ajouter utilisateurs
    public void ajouter(UserEntity user) throws SQLException {
        String req = "INSERT INTO users (nom, prenom, age, email, n_tel, login, mdp, poids, longeur, note_c, note_n, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
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
            preparedStatement.setString(12, user.getRole());

            preparedStatement.executeUpdate();
        }
    }

    //Méthode afficher les utilisateurs
    public List<UserEntity> readAll()  {
        try {

            List<UserEntity> users = new ArrayList<>();
            String query = "SELECT * FROM users";
            try (Statement statement = DataSource.getInstance().getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    UserEntity user = new UserEntity();
                    user.setId(resultSet.getInt("id"));
                    user.setNom(resultSet.getString("nom"));
                    user.setPrenom(resultSet.getString("prenom"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRole(resultSet.getString("role"));
                    user.setN_tel(resultSet.getInt("n_tel"));
                    users.add(user);
                }return users;
            }
        }catch (SQLException se){ return null;}

    }



    //  suppression des utilisateurs
    public void supprimer(UserEntity user) throws SQLException {
        String req = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
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
        String req = "UPDATE users SET nom = ?, prenom = ?, email = ?, mdp = ?, n_tel = ?, role = ?, age = ?, active = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom ());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMdp());
            preparedStatement.setInt(5, user.getN_tel());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setInt(7, user.getAge());
            preparedStatement.setBoolean(8, user.isActive());
            preparedStatement.setInt(9, user.getId());

            System.out.println("Executing query: " + preparedStatement);
            preparedStatement.executeUpdate();
            System.out.println("Update successful for user ID: " + user.getId());

            /*int rowsAffected = preparedStatement.executeUpdate();
            //preparedStatement.setInt(10, user.getId());
            //preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }*/
        }
    }


    @Override
    public UserEntity findbyId(int e) throws SQLException {
        return null;
    }


    // Trouver un utilisateur par ID

    public UserEntity findById(int id) {
        String req = "SELECT * FROM users WHERE id = ?";
        UserEntity user = null;
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
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
                        resultSet.getString("role")

                );
            }
        }catch (Exception e){}
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
                    resultSet.getString("role")
            );
        }
        return null;
    }


    public boolean getUserByEmail(String email) {

        String req = "SELECT * FROM users WHERE email = ?";
        UserEntity user = null;
        System.out.println(email);
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) return true;
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserEntity findUser(String nom, String prenom, String email, int tel) {
        List<UserEntity> users = readAll();
        System.out.println("0 "+tel);
        if (nom.length() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (!users.get(i).getNom().toLowerCase().contains(nom.toLowerCase())) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("NAME FILTER "+users.size());
        if (prenom.length() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (!users.get(i).getPrenom().toLowerCase().contains(prenom.toLowerCase())) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("PRENAME FILTER "+users.size());
        if (email.length() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (!users.get(i).getEmail().toLowerCase().contains(email.toLowerCase())) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("EMAIL FILTER "+users.size());
        if (tel != 0) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getN_tel() != tel) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("TEL FILTER "+users.size());
    if(users.size()>0) return users.get(0);
    return null;
    }
}
