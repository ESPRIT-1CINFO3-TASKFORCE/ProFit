package TestMain;


import Entites.UserEntity;
import Services.UserService;

import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class main {
    public static void main(String[] args) {
        // Ajout dans la base de données a travers le main
        try {
            // Créer une instance de UserService
            UserService userService = new UserService();

            // Créer un nouvel utilisateur pour tester
            UserEntity newUser = new UserEntity();
            newUser.setNom("Moalla");
            newUser.setPrenom("Malek");
            newUser.setAge(29);
            newUser.setEmail("malek.moalla888@gmail.com");
            newUser.setN_tel(20676913);
            newUser.setLogin("malek");
            newUser.setMdp("m94");
            newUser.setPoids(56);
            newUser.setLongeur(170);
            newUser.setNote_c(0);
            newUser.setNote_n(0);
            newUser.setRole("ADMIN"); // Assure-toi que le rôle correspond à ce que tu attends

            // Appeler la méthode d'ajout de l'utilisateur
            userService.ajouter(newUser);

            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
        }
    }}