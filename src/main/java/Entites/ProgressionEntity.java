package Entites;
import java.time.LocalDate;

public class ProgressionEntity {

    private int id_prg;
    private  int id;
    private int poids;
    private double longueur;
    private int IMC;
    private LocalDate date_inscri;
    private String description;
    private int masse_musc;

    private String nom;
    private String prenom;
    private String email;
    private String Nomregime;
    private LocalDate date_finR;

    public String getNomregime() {
        return Nomregime;
    }

    public void setNomregime(String nomregime) {
        Nomregime = nomregime;
    }

    public LocalDate getDate_finR() {
        return date_finR;
    }

    public void setDate_finR(LocalDate date_finR) {
        this.date_finR = date_finR;
    }

    public ProgressionEntity(int poids, double longueur, int IMC, LocalDate date_inscri, String description, String email) {
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = date_inscri;
        this.description = description;
        this.email = email;
    }

    public ProgressionEntity(int idPrg, int id, int poids, double longueur, double imc, LocalDate dateInscri, String description, int masseMusc, String email) {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //constructeur paramétres

    public ProgressionEntity(int id, String prenom, String nom) {
        this.id = id;

        this.prenom=prenom;
        this.nom = nom;

    }


    public ProgressionEntity(int id_prg, int id, int poids, double longueur, int IMC, LocalDate date_inscri, String description, int masse_musc) {
        this.id_prg = id_prg;
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = date_inscri;
        this.description = description;
        this.masse_musc = masse_musc;
    }

    public ProgressionEntity(int id, int poids, double longueur, int IMC, LocalDate date_inscri, String description) {
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = date_inscri;
        this.description = description;
        this.masse_musc = masse_musc;
    }




    public ProgressionEntity(){}

    public ProgressionEntity(int idClient, int poids, double longueur, LocalDate dateInscri, int masseMusc) {
    }

    public ProgressionEntity(int id, int poids, double longueur, int imc,  LocalDate dateInscri,String description, String nom, String prenom) {
    }

    public ProgressionEntity(int id, int poids, String description, String nom, String prenom) {
    }

    public ProgressionEntity(int id, int poids, String description) {
    }

    public ProgressionEntity(int id, int poids, String description, LocalDate dateInscription) {
    }

    public ProgressionEntity(String email, int poids, double longueur, LocalDate dateInscri) {
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

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

    public  void setPoids(int poids) {
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

    public  void setIMC(int IMC) {
        this.IMC = IMC;
    }

    public LocalDate getDate_inscri() {
        return date_inscri;
    }

    public void setDate_inscri(LocalDate date_inscri) {
        this.date_inscri = date_inscri;
    }

    public String getDescription() {
        return description;
    }

    public  void setDescription(String description) {
        this.description = description;
    }

    public int getMasse_musc() {
        return masse_musc;
    }

    public void setMasse_musc(int masse_musc) {
        this.masse_musc = masse_musc;
    }

    public ProgressionEntity(int poids, double longueur) {
        this.poids = poids;
        this.longueur = longueur;
    }

    public ProgressionEntity(int id, int poids, double longueur,LocalDate date_inscri) {
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.date_inscri=date_inscri;
    }

    public ProgressionEntity(int id_prg, int id, int poids, double longueur, int IMC, LocalDate date_inscri, String description, int masse_musc, String email) {
        this.id_prg = id_prg;
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = date_inscri;
        this.description = description;
        this.masse_musc = masse_musc;
        this.email = email;
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
                ", description='" + description + '\'' +
                ", masse_musc=" + masse_musc +
                '}';
    }

}
