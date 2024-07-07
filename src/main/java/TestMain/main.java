package TestMain;




import static java.sql.DriverManager.getConnection;


        // Ajout dans la base de données a travers le main


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

    }}