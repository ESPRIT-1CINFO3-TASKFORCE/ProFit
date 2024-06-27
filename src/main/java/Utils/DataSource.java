package Utils;

import Services.RegimeService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Services.RegimeService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private  String url="jdbc:mysql://localhost:3306/profit_db";


    private  String login="root";
    private  String pwd="";
    private static DataSource data;

    private Connection con1;


    private DataSource(){
        try {



            con1= DriverManager.getConnection(url,login,pwd);
            System.out.println("Connexion établie avec la base de données profit_bd. ");

        } catch (SQLException e) {

            System.out.println("Impossible de se connecter à la base de données : "+e);
        }
    }
    public Connection getCon() {
        return con1;
    }

    public static DataSource getInstance() {
        if(data==null)
            data=new DataSource();
        return data;
    }




}
