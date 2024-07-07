package controllers;

public class ValidationFormulaireController {

    public static boolean validerNomEx(String nomEx) {
        return nomEx.length() >= 3;
    }

    public static boolean validerMusclesCibles(String musclesCibles) {
        return musclesCibles.length() >= 3;
    }

    public static boolean validerDescription(String description) {
        return description.length() >= 5;
    }

    public static boolean validerDifficulte(String difficulte) {
        return difficulte.length() >= 5;
    }

    public static boolean validerCaloriesBrulees(Double caloriesBrulees) {
        // Assuming the validation is that the calories burned should be a positive number
        return caloriesBrulees >= 5;
    }

    public static boolean validerDureeMinutes(Integer dureeMinutes) {
        // Duration should be non-negative
        return dureeMinutes >= 0;
    }
}
