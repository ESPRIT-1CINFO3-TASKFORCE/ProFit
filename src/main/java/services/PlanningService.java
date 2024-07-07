package services;

import entites.EplanningEntity;
import entites.PlanningEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanningService {
    private List<PlanningEntity> plannings;

    // Constructeur par défaut
    public PlanningService() {
        this.plannings = new ArrayList<>();
        // Ajout d'exemples de données
        this.plannings.add(new PlanningEntity(1, 100, LocalDateTime.of(2024, 6, 3, 14, 30), EplanningEntity.Eplanning.PLANNING_PERSONNEL));
        this.plannings.add(new PlanningEntity(2, 101, LocalDateTime.of(2024, 6, 4, 16, 00), EplanningEntity.Eplanning.PLANNING_COACH));
    }

    //  ajouter Planning
    public void ajouter_p(PlanningEntity planning) {
        this.plannings.add(planning);
        System.out.println("Planning ajouté : " + planning);
    }

    // modifier Planning
    public void modifier_p(int id_planning, PlanningEntity updatedPlanning) {
        for (int i = 0; i < plannings.size(); i++) {
            if (plannings.get(i).getId_planning() == id_planning) {
                plannings.set(i, updatedPlanning);
                System.out.println("Planning modifié : " + updatedPlanning);
                return;
            }
        }
        System.out.println("Planning avec l'ID " + id_planning + " non trouvé.");
    }

    //  supprimer Planning
    public void supprimer_p(int id_planning) {
        plannings.removeIf(planning -> planning.getId_planning() == id_planning);
        System.out.println("Planning avec l'ID " + id_planning + " supprimé.");
    }

    // réserver Planning (marquer comme réservé)
    public void Reserver_p(int id_planning) {
        for (PlanningEntity planning : plannings) {
            if (planning.getId_planning() == id_planning) {
                System.out.println("Planning réservé : " + planning);

                return;
            }
        }
        System.out.println("Planning avec l'ID " + id_planning + " non trouvé.");
    }

    // consulter toutes les Planning
    public void consulter() {
        for (PlanningEntity planning : plannings) {
            System.out.println(planning);
        }
    }
}
