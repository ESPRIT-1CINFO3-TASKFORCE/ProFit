package Entites;
import java.time.LocalDate;
import java.util.Date;
public class ProgressionEntity {

    private int id_prg;
    private  int id;
    private int poids;
    private double longueur;
    private int IMC;
    private LocalDate date_inscri;
    private String desciption;
    private int masse_musc;

    //constructeur paramétres


    public ProgressionEntity(int id_prg, int id, int poids, double longueur, int IMC, LocalDate date_inscri, String desciption, int masse_musc) {
        this.id_prg = id_prg;
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = date_inscri;
        this.desciption = desciption;
        this.masse_musc = masse_musc;
    }

    public ProgressionEntity(int id, int poids, double longueur, int IMC, LocalDate date_inscri, String desciption, int masse_musc) {
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = date_inscri;
        this.desciption = desciption;
        this.masse_musc = masse_musc;
    }
    public ProgressionEntity(){}

    public int getId_prg() {
        return id_prg;
    }

    public void setId_prg(int id_prg) {
        this.id_prg = id_prg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public int getIMC() {
        return IMC;
    }

    public void setIMC(int IMC) {
        this.IMC = IMC;
    }

    public LocalDate getDate_inscri() {
        return date_inscri;
    }

    public void setDate_inscri(LocalDate date_inscri) {
        this.date_inscri = date_inscri;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public int getMasse_musc() {
        return masse_musc;
    }

    public void setMasse_musc(int masse_musc) {
        this.masse_musc = masse_musc;
    }

    @Override
    public String toString() {
        return "ProgressionEntity{" +
                "id_prg=" + id_prg +
                ", id=" + id +
                ", poids=" + poids +
                ", longueur=" + longueur +
                ", IMC=" + IMC +
                ", date_inscri=" + date_inscri +
                ", desciption='" + desciption + '\'' +
                ", masse_musc=" + masse_musc +
                '}';
    }

}
