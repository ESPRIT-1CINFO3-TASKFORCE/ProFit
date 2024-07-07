package entites;

import java.time.LocalDateTime;

public class PlanningEntity {
    private int id_planning;
    private int id_cour;
    private LocalDateTime date;  // Utilisation de LocalDateTime pour représenter la date et l'heure
    private EplanningEntity.Eplanning planning;  // Utilisation de l'énumération Eplanning

    // Constructeur par défaut
    public PlanningEntity() {}

    // Constructeur avec paramètres
    public PlanningEntity(int id_planning, int id_cour, LocalDateTime date, EplanningEntity.Eplanning planning) {
        this.id_planning = id_planning;
        this.id_cour = id_cour;
        this.date = date;
        this.planning = planning;
    }

    // Getters et Setters
    public int getId_planning() {
        return id_planning;
    }

    public void setId_planning(int id_planning) {
        this.id_planning = id_planning;
    }

    public int getId_cour() {
        return id_cour;
    }

    public void setId_cour(int id_cour) {
        this.id_cour = id_cour;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EplanningEntity.Eplanning getPlanning() {
        return planning;
    }

      public void setPlanning(EplanningEntity.Eplanning planning) {
        this.planning = planning;
      }


    public String toString() {
        return "PlanningEntity{" +
                "id_planning=" + id_planning +
                ", id_cour=" + id_cour +
                ", date=" + date +
                ", planning=" + planning +
                '}';
    }
}
