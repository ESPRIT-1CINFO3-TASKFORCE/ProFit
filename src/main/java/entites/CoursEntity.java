package entites;

import java.time.Duration;

public class CoursEntity {
    private int id_cour;
    private String nom;
    private Duration duree;  // Utilisation de Date pour représenter la durée
    private String event;
    private String niveau; // par exemple, débutant, intermédiaire, avancé
    private int nb_Max;

    // Constructeur par défaut
    public CoursEntity() {}

    // Constructeur avec paramètres
    public CoursEntity(int id_cour, String nom, Duration duree, String event, String niveau, int nb_Max) {
        this.id_cour = id_cour;
        this.nom = nom;
        this.duree = duree;
        this.event = event;
        this.niveau = niveau;
        this.nb_Max = nb_Max;
    }

    // Getters et Setters
    public int getId_cour() {
        return id_cour;
    }

    public void setId_cour(int id_cour) {
        this.id_cour = id_cour;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Duration getDuree() {
        return duree;
    }

    public void setDuree(Duration duree) {
        this.duree = duree;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getNb_Max() {
        return nb_Max;
    }

    public void setNb_Max(int nb_Max) {
        this.nb_Max = nb_Max;
    }

    @Override
    public String toString() {
        return "CoursEntity{" +
                "id_cour=" + id_cour +
                ", nom='" + nom + '\'' +
                ", duree=" + duree +
                ", event='" + event + '\'' +
                ", niveau='" + niveau + '\'' +
                ", nb_Max=" + nb_Max +
                '}';
    }
}
