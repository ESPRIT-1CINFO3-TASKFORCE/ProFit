package TestMain;

import Entites.ProgressionEntity;
import Entites.RegimeEntity;
import Services.ProgressionService;
import Services.RegimeService;
import Utils.DataSource;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class main {
    public static void main(String[] args) {

        System.out.println("hello!");

        //*****************************************TESTER CRUD CLASSE PROGRESSION***************************

        //methode afficher tous les progression de le BD
        try {
            // Lecture de toutes les progressions et affichage
            ProgressionService prog = ProgressionService.getInstance();
            List<ProgressionEntity> progressions = prog.readAll();
            for (ProgressionEntity progression : progressions) {
                System.out.println(progression);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture des progressions : " + e.getMessage());
        }

// Ajout d'une progression
     /*  LocalDate date = LocalDate.of(2024, 2, 23);
        ProgressionEntity p1 = new ProgressionEntity(72, 65, 1.7, 179, date, "3eme progression ajouter", 567);

        try {
            ProgressionService prog = ProgressionService.getInstance();
            prog.AjouterProgression(p1);
            System.out.println("Progression ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la progression : " + e.getMessage());
        }*/

        //Tester pour supprimer une progression

        try {
            // Créez une instance de ProgressionService
            ProgressionService progressionService = ProgressionService.getInstance();

            // Supprimez une progression en appelant la méthode deleteProgression
            long idProgresSuppr = 10; // Entrer l'id que a supprimer
            progressionService.deleteProgression(idProgresSuppr);

        } catch (SQLException e) {
            // Gérez les exceptions SQL, par exemple, affichez simplement la trace de la pile
            e.printStackTrace();
        }

        //modifier un progres


        try {
            // Créez une instance de ProgressionService
            ProgressionService progressionService = ProgressionService.getInstance();

            // Créez une instance de ProgressionEntity avec les nouvelles valeurs
            ProgressionEntity progressionModifiee = new ProgressionEntity();
            progressionModifiee.setId_prg(3);
            progressionModifiee.setPoids(65);
            progressionModifiee.setLongueur(1.70);
            progressionModifiee.setIMC(179);
            progressionModifiee.setMasse_musc(567);
            progressionModifiee.setDate_inscri(LocalDate.of(2024, 2, 23));
            progressionModifiee.setDesciption("tester modification des methodes");

            // Appelez la méthode modifierProgres avec l'ID de la progression à modifier et les nouvelles valeurs
            long progressionIdAModifier = 9; // Remplacez 3 par l'ID de progression à modifier
            progressionService.modifierProgres(progressionIdAModifier, progressionModifiee);

        } catch (SQLException e) {
            // Gérez les exceptions SQL, par exemple, affichez simplement la trace de la pile
            e.printStackTrace();
        }



        //*************************************TESTER CRUD REGIME************************************

        //methode afficher tous les regimes de le BD
        try {
            // Lecture de toutes les regimes et affichage
           RegimeService reg = RegimeService.getInstance();
            List<RegimeEntity> regimes = reg.readAll();
            for (RegimeEntity regime : regimes) {
                System.out.println(regime);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture des regimes : " + e.getMessage());
        }




        // Ajout d'une progression
       /*LocalDate date_debut = LocalDate.of(2024, 2, 23);
        LocalDate date_fin = LocalDate.of(2024, 2, 23);
       RegimeEntity r1 = new RegimeEntity(2, 72,"regime bio", "ajouter 2eme regime",date_debut,date_fin);

        try {
            RegimeService regime = RegimeService.getInstance();
            regime.AjouterRegime(r1);
            System.out.println("Regime  ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de regime : " + e.getMessage());
        }*/

        //Tester pour supprimer une regime

        try {
            // Créez une instance de ProgressionService
          RegimeService regimeService = RegimeService.getInstance();

            // Supprimez une progression en appelant la méthode deleteProgression
            long idRegimeSuppr = 1; // Entrer l'id que a supprimer
            regimeService.deleteRegime(idRegimeSuppr);

        } catch (SQLException e) {
            // Gérez les exceptions SQL, par exemple, affichez simplement la trace de la pile
            e.printStackTrace();
        }


        //modifier un regime


        try {
            // Créez une instance de ProgressionService
            RegimeService regimeService = RegimeService.getInstance();

            // Créez une instance de RegimeEntity avec les nouvelles valeurs
            RegimeEntity regimeModifiee = new RegimeEntity();
            regimeModifiee.setNom_regime("regime minouch");
           regimeModifiee.setDescription("regime pour perte poids");
            regimeModifiee.setDate_debut(LocalDate.of(2024, 2, 23));
            regimeModifiee.setDate_fin(LocalDate.of(2024, 2, 23));



            // Appelez la méthode modifierregime avec l'ID de la regime à modifier et les nouvelles valeurs
            long regimeIdAModifier = 3; // Remplacez 3 par l'ID de regime à modifier
            //le regime d'id 3 a modifier avec les coordonnes au dessus (modifier dans la ligne d'id 3)
           regimeService.modifierRegime(regimeIdAModifier, regimeModifiee);

        } catch (SQLException e) {
            // Gérez les exceptions SQL, par exemple, affichez simplement la trace de la pile
            e.printStackTrace();
        }




























    }
}























/*
        RegimeService reg=new RegimeService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        RegimeEntity R2=new RegimeEntity();
        R2.setId_client(123);
        R2.setId_regime(02);
        R2.setNom_regime("regime bio");
        System.out.println(R2);





        try {
            reg.AjouterRegime(R2);
            System.out.println("regime ajouteé");
        }catch (SQLException e){
            System.out.println(e);
        }



        try {
            reg.readAll().forEach(e-> System.out.println(e));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



/*try {
        Connection connection = DriverManager.getConnection(url, login, pwd);
        RegimeService service = new RegimeService(connection);

        // Appel de la méthode pour modifier le nom du régime avec l'id_client 123
        service.modifierRegime(01L, "REGIME DETOX");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}   */
        //Tester tous le smethodes utiliser


   /* String jdbcURL = "jdbc:mysql://localhost:3306/profit_db";
    String dbUser = "root";
    String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
        RegimeService regimeService = new RegimeService(connection);

        // Test de la méthode pour afficher les régimes par ID client
        System.out.println("Affichage des régimes pour l'ID client 123 :");
        regimeService.afficherRegimesParIdClient(123L);
        System.out.println("------------------------------------");

        // Test de la méthode pour modifier un régime
        System.out.println("Modification du régime avec l'ID 2 :");
        RegimeEntity nouveauRegime = new RegimeEntity(2, 123, "Nouveau régime", "Nouvelle description", new Date(), new Date());
        regimeService.modifierRegime(2L, nouveauRegime);
        System.out.println("------------------------------------");

        // Test de la méthode pour supprimer un régime
        System.out.println("Suppression du régime avec l'ID 3 :");
        regimeService.deleteRegime(3L);
        System.out.println("------------------------------------");

        // Test de la méthode pour obtenir la progression par ID client
        System.out.println("Affichage de la progression pour l'ID client 123 :");
        ProgressionService progressionService = new ProgressionService(connection);
        ProgressionService.ProgressionEntity progression = progressionService.getProgressionById(123L);
        if (progression != null) {
            System.out.println("ID Progression: " + progression.getId());
            System.out.println("ID Client: " + progression.getId_client());
            System.out.println("Description: " + progression.getDescription());
            System.out.println("Progress: " + progression.getProgress());
        } else {
            System.out.println("Aucune progression trouvée pour l'ID client 123");
        }
        System.out.println("------------------------------------");

    } catch (SQLException e) {
        e.printStackTrace();
    }catch
}












*/
        /*
        //creer un instance de la classe progression
   LocalDate date =LocalDate.of(2024,02,23);

    ProgressionEntity p1 =new ProgressionEntity( 072 , 73,179, 23,123, date,"progression");

   ProgressionService prog=new ProgressionService();
        try {
            prog.AjouterProgression(p1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

*/





