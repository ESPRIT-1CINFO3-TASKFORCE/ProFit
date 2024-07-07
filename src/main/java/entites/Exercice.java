package entites;

import javafx.beans.property.*;

public class Exercice {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nomEx = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty difficulte = new SimpleStringProperty();
    private IntegerProperty dureeMinutes = new SimpleIntegerProperty();
    private IntegerProperty caloriesBrulees = new SimpleIntegerProperty();
    private StringProperty musculesCibles = new SimpleStringProperty();

    // Constructeurs
    public Exercice() {
    }

    public Exercice(String nomEx, String description, String difficulte, int dureeMinutes, int caloriesBrulees, String musculesCibles) {
        this.nomEx.set(nomEx);
        this.description.set(description);
        this.difficulte.set(difficulte);
        this.dureeMinutes.set(dureeMinutes);
        this.caloriesBrulees.set(caloriesBrulees);
        this.musculesCibles.set(musculesCibles);
    }

    public Exercice(int id, String nomEx, String description, String difficulte, int dureeMinutes, int caloriesBrulees, String musculesCibles) {
        this.id.set(id);
        this.nomEx.set(nomEx);
        this.description.set(description);
        this.difficulte.set(difficulte);
        this.dureeMinutes.set(dureeMinutes);
        this.caloriesBrulees.set(caloriesBrulees);
        this.musculesCibles.set(musculesCibles);
    }

    // Id property
    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    // NomEx property
    public StringProperty nomExProperty() {
        return nomEx;
    }

    public String getNomEx() {
        return nomEx.get();
    }

    public void setNomEx(String nomEx) {
        this.nomEx.set(nomEx);
    }

    // Description property
    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    // Difficulte property
    public StringProperty difficulteProperty() {
        return difficulte;
    }

    public String getDifficulte() {
        return difficulte.get();
    }

    public void setDifficulte(String difficulte) {
        this.difficulte.set(difficulte);
    }

    // DureeMinutes property
    public IntegerProperty dureeMinutesProperty() {
        return dureeMinutes;
    }

    public int getDureeMinutes() {
        return dureeMinutes.get();
    }

    public void setDureeMinutes(int dureeMinutes) {
        this.dureeMinutes.set(dureeMinutes);
    }

    // CaloriesBrulees property
    public IntegerProperty caloriesBruleesProperty() {
        return caloriesBrulees;
    }

    public int getCaloriesBrulees() {
        return caloriesBrulees.get();
    }

    public void setCaloriesBrulees(int caloriesBrulees) {
        this.caloriesBrulees.set(caloriesBrulees);
    }

    // MusculesCibles property
    public StringProperty musculesCiblesProperty() {
        return musculesCibles;
    }

    public String getMusculesCibles() {
        return musculesCibles.get();
    }

    public void setMusculesCibles(String musculesCibles) {
        this.musculesCibles.set(musculesCibles);
    }

    @Override
    public String toString() {
        return "Exercice{" +
                "id=" + id +
                ", nomEx='" + nomEx.get() + '\'' +
                ", description='" + description.get() + '\'' +
                ", difficulte='" + difficulte.get() + '\'' +
                ", dureeMinutes=" + dureeMinutes.get() +
                ", caloriesBrulees=" + caloriesBrulees.get() +
                ", musculesCibles='" + musculesCibles.get() + '\'' +
                '}';
    }
}
