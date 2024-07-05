package TestMain;



import Controllers.AjouterProgresController;
import Entites.MessageEntity;
import Entites.ProgressionEntity;
import Entites.RegimeEntity;
import Services.MessageService;
import Services.ProgressionService;
import Services.RegimeService;
import Utils.DataSource;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class main {
    public static void main(String[] args) throws SQLException {

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
            int idProgresSuppr = 10; // Entrer l'id que a supprimer
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
            progressionModifiee.setDescription("tester modification des methodes");

            // Appelez la méthode modifierProgres avec l'ID de la progression à modifier et les nouvelles valeurs
            long progressionIdAModifier = 9; // Remplacez 3 par l'ID de progression à modifier
            progressionService.modifierProgres(progressionIdAModifier, progressionModifiee);

        } catch (SQLException e) {
            // Gérez les exceptions SQL, par exemple, affichez simplement la trace de la pile
            e.printStackTrace();
        }


        //******************Tester la methode d'affichage par id client

        ProgressionService progressionService=new ProgressionService();
        // ID du client à rechercher
        int clientId = 2208;

        // Récupérer les progressions pour ce client
        List<ProgressionEntity> progressions = progressionService.getProgressionsByClientId(clientId);

        // Afficher les progressions
        for (ProgressionEntity progression : progressions) {
            System.out.println("ID Progression: " + progression.getId_prg());
            System.out.println("ID Client: " + progression.getId());
            System.out.println("Poids: " + progression.getPoids());
            System.out.println("Longueur: " + progression.getLongueur());
            System.out.println("IMC: " + progression.getIMC());
            System.out.println("Date d'inscription: " + progression.getDate_inscri());
            System.out.println("Description: " + progression.getDescription());
            System.out.println("Masse musculaire: " + progression.getMasse_musc());
            System.out.println("---------------------------");
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

        //***********Tester pour supprimer une regime

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


        //****************modifier un regime


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

        //Tester la methode calcule imc
        double poids = 70.0; // Exemple de poids en kg
        double longueur = 175.0; // Exemple de longueur en cm
       // AjouterProgresController.calculerEtAfficherIMC(clientId,poids, longueur);































        MessageService messageService = new MessageService();

        MessageEntity message = new MessageEntity();
        message.setEnvoyeur(1);
        message.setRecepteur(2);
        message.setMessage("Hello, this is a test message!");
        message.setTimestamp(LocalDateTime.now());

        //FOR TEST messageService.insertMessage(message);




//        ProgressionService progressi = new ProgressionService();
//         // ID que vous souhaitez rechercher
//        List<ProgressionEntity> idsTrouves = null;
//        try {
//            idsTrouves = progressi.chercherParEmail("aziz.hajri@esprit.tn");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//// Affichage des IDs trouvés
//        System.out.println("IDs trouvés pour idClient "  + idsTrouves);





        String jdbcURL = "jdbc:mysql://localhost:3306/profit_db";
        String username = "root";
        String password = "";

        try  {
             Connection conn = DriverManager.getConnection(jdbcURL, username, password);
            // Assurez-vous que votre ProgressionService utilise cette connexion
            ProgressionService service = new ProgressionService(conn);

            // Remplacez par un email existant dans votre base de données
            String email = "aziz.hajri@esprit.tn";

            // Appel de la méthode
            List<ProgressionEntity> progression = service.chercherParEmail(email);

            // Afficher les résultats
            for (ProgressionEntity prog : progression) {
                System.out.println("Poids: " + prog.getPoids());
                System.out.println("Longueur: " + prog.getLongueur());
                System.out.println("IMC: " + prog.getIMC());
                System.out.println("Date d'inscription: " + prog.getDate_inscri());
                System.out.println("Description: " + prog.getDescription());
                System.out.println("Email: " + prog.getEmail());
                System.out.println("-----");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }




}




























