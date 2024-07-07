package services;

import entites.CoursEntity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CoursService {
    private List<CoursEntity> cours;

    // Constructeur par défaut
    public CoursService() {
        this.cours = new ArrayList<>();
        // Ajout d'exemples de données
        this.cours.add(new CoursEntity(1, "Yoga", Duration.ofHours(3),"Morning Yoga", "Débutant", 20));
        this.cours.add(new CoursEntity(2, "Pilates", Duration.ofHours(1), "Evening Pilates", "Intermédiaire", 15));
    }

    //  réserver un cours
    public void reserver(int id_cour) {
        for (CoursEntity cour : cours) {
            if (cour.getId_cour() == id_cour) {
                System.out.println("Cours réservé : " + cour);
                // Ajouter la logique pour marquer le cours comme réservé si nécessaire
                return;
            }
        }
        System.out.println("Cours avec l'ID " + id_cour + " non trouvé.");
    }

    // afficher tous les cours
    public void afficher() {
        for (CoursEntity cour : cours) {
            System.out.println(cour);
        }
    }

    // ajouter un nouveau cours
    public void ajout_c(CoursEntity newCour) {
        this.cours.add(newCour);
        System.out.println("Cours ajouté : " + newCour);
    }

    // modifier un cours existant
    public void modifier_c(int id_cour, CoursEntity updatedCour) {
        for (int i = 0; i < cours.size(); i++) {
            if (cours.get(i).getId_cour() == id_cour) {
                cours.set(i, updatedCour);
                System.out.println("Cours modifié : " + updatedCour);
                return;
            }
        }
        System.out.println("Cours avec l'ID " + id_cour + " non trouvé.");
    }

    //  supprimer un cours existant
    public void supprimer_c(int id_cour) {
        cours.removeIf(cour -> cour.getId_cour() == id_cour);
        System.out.println("Cours avec l'ID " + id_cour + " supprimé.");
    }

    // Méthode pour animer un cours
    public void Animer_c(int id_cour) {
        for (CoursEntity cour : cours) {
            if (cour.getId_cour() == id_cour) {
                System.out.println("Cours animé : " + cour);
                // Ajouter la logique pour animer le cours si nécessaire
                return;
            }
        }
        System.out.println("Cours avec l'ID " + id_cour + " non trouvé.");
    }
}
