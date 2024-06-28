package Entites;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ForumEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id_forum;
    private int createur;
    private String titre;
    private String topique;
    private String contenu;
    private LocalDateTime dateCreation;


    public int getId_forum() {
        return id_forum;
    }

    public void setId_forum(int id_forum) {
        this.id_forum = id_forum;
    }

    public int getCreateur() {
        return createur;
    }

    public void setCreateur(int createur) {
        this.createur = createur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTopique() {
        return topique;
    }

    public void setTopique(String topique) {
        this.topique = topique;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "ForumEntity{" +
                "id_forum=" + id_forum +
                ", createur=" + createur +
                ", titre='" + titre + '\'' +
                ", topique='" + topique + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
