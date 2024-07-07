package Entites;
import java.time.LocalDate;

public class RegimeEntity {


  private int id_regime;
  private int id_client;
  private String nom_regime;
  private String description;
  private LocalDate date_debut;
  private  LocalDate date_fin;
  private Semaine semaine;
  private Repas repas;
  //Constructeur parametré


  public RegimeEntity(int id_regime, int id_client, String nom_regime, String description, LocalDate date_debut, LocalDate date_fin) {
    this.id_regime = id_regime;
    this.id_client = id_client;
    this.nom_regime = nom_regime;
    this.description = description;
    this.date_debut = date_debut;
    this.date_fin = date_fin;
    this.semaine = semaine;
    this.repas = repas;
  }

  public RegimeEntity(int id_client, String nom_regime, String description, LocalDate date_debut, LocalDate date_fin, Semaine semaine, Repas repas) {
    this.id_client = id_client;
    this.nom_regime = nom_regime;
    this.description = description;
    this.date_debut = date_debut;
    this.date_fin = date_fin;
    this.semaine = semaine;
    this.repas = repas;
  }

  public RegimeEntity(){}
  public RegimeEntity(int id_client, int poids,double longueur,LocalDate dateInscri) {
  }



  public RegimeEntity(String nom_Regime ,LocalDate date_debut, LocalDate date_fin) {
    this.nom_regime=nom_Regime;
    this.date_debut = date_debut;
    this.date_fin = date_fin;
  }

  public RegimeEntity(String nomRegime, String dateDebutStr, String dateFinStr) {
  }


  public int getId_regime() {
    return id_regime;
  }

  public void setId_regime(int id_regime) {
    this.id_regime = id_regime;
  }

  public int getId_client() {
    return id_client;
  }

  public void setId_client(int id_client) {
    this.id_client = id_client;
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

  public LocalDate getDate_debut() {
    return date_debut;
  }

  public void setDate_debut(LocalDate date_debut) {
    this.date_debut = date_debut;
  }

  public LocalDate getDate_fin() {
    return date_fin;
  }

  public void setDate_fin(LocalDate date_fin) {
    this.date_fin = date_fin;
  }

  public Semaine getSemaine() {
    return semaine;
  }

  public void setSemaine(Semaine semaine) {
    this.semaine = semaine;
  }

  public Repas getRepas() {
    return repas;
  }

  public void setRepas(Repas repas) {
    this.repas = repas;
  }

  @Override
  public String toString() {
    return "RegimeEntity{" +
            "id_regime=" + id_regime +
            ", id_client=" + id_client +
            ", nom_regime='" + nom_regime + '\'' +
            ", description='" + description + '\'' +
            ", date_debut=" + date_debut +
            ", date_fin=" + date_fin +
            ", semaine=" + semaine +
            ", repas=" + repas +
            '}';
  }
}
