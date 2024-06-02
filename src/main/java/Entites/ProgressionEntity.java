package Entites;
import java.util.Date;
public class ProgressionEntity {

    private int id_progression;
    private int poids;
    private double longeur;
    private int IMC;
    private int Masse_musc;
    private Date date_inscri;
    private String Desciption;

    //constructeur paramétres


    public ProgressionEntity(int id_progression, int poids, double longeur, int IMC, int masse_musc, Date date_inscri, String desciption) {
        this.id_progression = id_progression;
        this.poids = poids;
        this.longeur = longeur;
        this.IMC = IMC;
        Masse_musc = masse_musc;
        this.date_inscri = date_inscri;
        Desciption = desciption;
    }

    public ProgressionEntity (){}


    //Getters et Setters

    public int getId_progression() {
        return id_progression;
    }

    public void setId_progression(int id_progression) {
        this.id_progression = id_progression;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public double getLongeur() {
        return longeur;
    }

    public void setLongeur(double longeur) {
        this.longeur = longeur;
    }

    public int getIMC() {
        return IMC;
    }

    public void setIMC(int IMC) {
        this.IMC = IMC;
    }

    public int getMasse_musc() {
        return Masse_musc;
    }

    public void setMasse_musc(int masse_musc) {
        Masse_musc = masse_musc;
    }

    public Date getDate_inscri() {
        return date_inscri;
    }

    public void setDate_inscri(Date date_inscri) {
        this.date_inscri = date_inscri;
    }

    public String getDesciption() {
        return Desciption;
    }

    public void setDesciption(String desciption) {
        Desciption = desciption;
    }

    //methode toString


    @Override
    public String toString() {
        return "ProgressionEntity{" +
                "id_progression=" + id_progression +
                ", poids=" + poids +
                ", longeur=" + longeur +
                ", IMC=" + IMC +
                ", Masse_musc=" + Masse_musc +
                ", date_inscri=" + date_inscri +
                ", Desciption='" + Desciption + '\'' +
                '}';
    }
}
