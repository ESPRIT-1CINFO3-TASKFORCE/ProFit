package entites;
import java.time.LocalDate;

import java.sql.Date;

public class ProgressionEntity {
    private double masse_musc;
    private String gender;


    private int id_prg;
    private int id;
    private int poids;
    private double longueur;
    private double IMC;
    // Ensure this matches the column name in your database

    private Date date_inscri;
    private String description;

    private String nom;
    private String prenom;
    private String email;
    private String nomRegime;
    private Date date_finR;
      // New attribute for gender

    private LocalDate dateInscri;
    private String genre;
    private double masseMusc;

    public ProgressionEntity(int id_prg, int id, double poids, double longueur, double IMC, Date date_inscri, String description, double masseMusc, String nom, String prenom, String email, String nomRegime, Date date_finR, String gender) {
        this.id_prg = id_prg;
        this.id = id;
        this.poids = (int) poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = date_inscri;
        this.description = description;
        this.masseMusc = masseMusc;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nomRegime = nomRegime;
        this.date_finR = date_finR;
        this.gender = gender;
    }


    public ProgressionEntity() {
    }

    // Constructor for ProgressionEntity with necessary attributes
    public ProgressionEntity(int idPrg, int id, int poids, double longueur, int imc, LocalDate dateInscri, String description, String gender) {
        this.id_prg = idPrg;
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = calculateIMC(poids, longueur); // Calculating IMC
        this.date_inscri = Date.valueOf(dateInscri);
        this.description = description;
        this.gender = gender;  // Setting gender
        this.masse_musc = calculateMasseMusc(poids, longueur, gender); // Calculating muscle mass
    }

    public ProgressionEntity(int idPrg, int id, int poids, double longueur, int imc, LocalDate dateInscri, String description) {
        this.id_prg = idPrg;
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = calculateIMC(poids, longueur); // Calculating IMC
        this.date_inscri = Date.valueOf(dateInscri);
        this.description = description;
        // Default values for other attributes
    }

    public ProgressionEntity(int idPrg, int id, int poids, double longueur, int imc, LocalDate dateInscri, String description, int masseMusc, String email) {
        this.id_prg = idPrg;
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = calculateIMC(poids, longueur); // Calculating IMC
        this.date_inscri = Date.valueOf(dateInscri);
        this.description = description;
        this.masse_musc = masseMusc;
        this.email = email;
        // Default values for other attributes
    }

    public ProgressionEntity(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        // Default values for other attributes
    }

    public ProgressionEntity(int poids, double longueur, double IMC, LocalDate date_inscri, String description, String email) {
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = IMC;
        this.date_inscri = Date.valueOf(date_inscri);
        this.description = description;
        this.email = email;
        // Default values for other attributes
    }

    public ProgressionEntity(int id, int poids, double longueur, LocalDate dateInscri, String gender) {
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.date_inscri = Date.valueOf(dateInscri);
        this.gender = gender;

        // Calculer IMC et masse musculaire lors de la création de l'instance si nécessaire
        this.IMC = calculateIMC(poids, longueur);
        this.masse_musc = calculateMasseMusc(poids, longueur, gender);
    }

    public ProgressionEntity(int idPrg, int id, int poids, double longueur, int imc, LocalDate dateInscri, String description, int masseMusc, String mail, String gender) {
        this.id_prg = idPrg;
        this.id = id;
        this.poids = poids;
        this.longueur = longueur;
        this.IMC = imc;
        this.date_inscri = Date.valueOf(dateInscri);
        this.description = description;
        this.masse_musc = masseMusc;
        this.email = mail;
        this.gender = gender;
    }


    public ProgressionEntity(int poids, double longueur, LocalDate dateInscri, String genre, double masseMusc) {
        this.poids = poids;
        this.longueur = longueur;
        this.dateInscri = dateInscri;
        this.genre = genre;
        this.masseMusc = masseMusc;
    }
    // Getters and setters for all attributes

    public String getNomRegime() {
        return nomRegime;
    }

    public void setNomRegime(String nomRegime) {
        this.nomRegime = nomRegime;
    }

    public LocalDate getDate_finR() {
        return date_finR.toLocalDate();
    }

    public void setDate_finR(LocalDate date_finR) {
        this.date_finR = Date.valueOf(date_finR);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getIMC() {
        return IMC;
    }

    public void setIMC(double IMC) {
        this.IMC = IMC;
    }

    public LocalDate getDate_inscri() {
        return date_inscri.toLocalDate();
    }

    public void setDate_inscri(LocalDate date_inscri) {
        this.date_inscri = Date.valueOf(date_inscri);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMasse_musc() {
        return masse_musc;
    }

    public void setMasse_musc(double masse_musc) {
        this.masse_musc = masse_musc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Method to calculate IMC
    private double calculateIMC(int poids, double longueur) {
        if (longueur == 0) return 0;
        return poids / (longueur * longueur);
    }

    // Method to calculate Masse Musculaire
    public double calculateMasseMusc(int poids, double longueur, String gender) {
        double tailleCm = longueur * 100;
        return (gender.equalsIgnoreCase("homme"))
                ? 0.407 * poids + 0.267 * tailleCm - 19.2
                : 0.252 * poids + 0.473 * tailleCm - 48.3;
    }

    public ProgressionEntity(int poids, double longueur, LocalDate dateInscri, String genre) {
        this.poids = poids;
        this.longueur = longueur;
        this.dateInscri = dateInscri;
        this.genre = genre;

        // Calcul de la masse musculaire en fonction du genre
        if ("homme".equals(genre.toLowerCase())) {
            this.masseMusc = 0.407 * poids + 0.267 * longueur - 19.2;
        } else if ("femme".equals(genre.toLowerCase())) {
            this.masseMusc = 0.252 * poids + 0.473 * longueur - 48.3;
        } else {
            // Par défaut, on peut initialiser à 0 ou laisser null selon votre logique
            this.masseMusc = 0.0;
        }
    }


    public LocalDate getDateInscri() {
        return dateInscri;
    }

    public void setDateInscri(LocalDate dateInscri) {
        this.dateInscri = dateInscri;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getMasseMusc() {
        return masseMusc;
    }

    public void setMasseMusc(double masseMusc) {
        this.masseMusc = masseMusc;
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

    public String getNomregime() {
        return nomRegime;
    }



    public void setDate_inscri(Date date_inscri) {
        this.date_inscri = date_inscri;
    }

    public void setDate_finR(Date date_finR) {
        this.date_finR = date_finR;
    }



    public LocalDate getDateFinR() {
        return date_finR.toLocalDate();
    }

    public void setDateFinR(LocalDate date_finR) {
        this.date_finR = Date.valueOf(date_finR);
    }
}
