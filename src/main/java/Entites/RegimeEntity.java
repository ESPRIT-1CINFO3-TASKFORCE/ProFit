package Entites;
import java.util.Date;

public class RegimeEntity {


  private int id_regime;
  private String nom_regime;
  private String description;
  private Date date_debut;
  private Date date_fin;
  //Constructeur parametré
  public RegimeEntity(int id_regime, String nom_regime, String description, Date date_debut, Date date_fin) {
    this.id_regime = id_regime;
    this.nom_regime = nom_regime;
    this.description = description;
    this.date_debut = date_debut;
    this.date_fin = date_fin;
  }
  //constructeur par defaut
  public RegimeEntity(){}

  //Getters et Setters

  public int getId_regime() {
    return id_regime;
  }

  public void setId_regime(int id_regime) {
    this.id_regime = id_regime;
  }

  public String getNom_regime() {
    return nom_regime;
  }

  public void setNom_regime(String nom_regime) {
    this.nom_regime = nom_regime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDate_debut() {
    return date_debut;
  }

  public void setDate_debut(Date date_debut) {
    this.date_debut = date_debut;
  }

  public Date getDate_fin() {
    return date_fin;
  }

  public void setDate_fin(Date date_fin) {
    this.date_fin = date_fin;
  }


  //methode toString


  @Override
  public String toString() {
    return "RegimeEntity{" +
            "id_regime=" + id_regime +
            ", nom_regime='" + nom_regime + '\'' +
            ", description='" + description + '\'' +
            ", date_debut=" + date_debut +
            ", date_fin=" + date_fin +
            '}';
  }


}
