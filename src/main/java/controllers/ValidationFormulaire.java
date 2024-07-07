package controllers;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ValidationFormulaire {

    public static boolean validateNomEx(TextField nomExField) {
        String nomEx = nomExField.getText().trim();
        if (nomEx.isEmpty() || nomEx.length() < 5) {
            setFieldError(nomExField, "Le nom doit contenir au moins 5 caractères.");
            return false;
        }
        clearFieldError(nomExField);
        return true;
    }

    public static boolean validateDescription(TextField descriptionField) {
        String description = descriptionField.getText().trim();
        if (description.length() > 100) {
            setFieldError(descriptionField, "La description ne doit pas dépasser 100 caractères.");
            return false;
        }
        clearFieldError(descriptionField);
        return true;
    }

    public static boolean validateDifficulte(TextField difficulteField) {
        try {
            int difficulte = Integer.parseInt(difficulteField.getText().trim());
            if (difficulte < 1 || difficulte > 100) {
                setFieldError(difficulteField, "La difficulté doit être comprise entre 1 et 100.");
                return false;
            }
            clearFieldError(difficulteField);
            return true;
        } catch (NumberFormatException e) {
            setFieldError(difficulteField, "Veuillez entrer un nombre valide pour la difficulté.");
            return false;
        }
    }

    public static boolean validateCaloriesBrulees(TextField caloriesBruleesField) {
        try {
            double caloriesBrulees = Double.parseDouble(caloriesBruleesField.getText().trim());
            if (caloriesBrulees <= 0) {
                setFieldError(caloriesBruleesField, "Les calories brûlées doivent être positives.");
                return false;
            }
            clearFieldError(caloriesBruleesField);
            return true;
        } catch (NumberFormatException e) {
            setFieldError(caloriesBruleesField, "Veuillez entrer un nombre valide pour les calories brûlées.");
            return false;
        }
    }

    public static boolean validateMusculesCibles(ChoiceBox<String> musculesCiblesBox) {
        String selectedMuscle = musculesCiblesBox.getValue();
        if (selectedMuscle == null || selectedMuscle.isEmpty()) {
            Label errorLabel = new Label("Veuillez sélectionner un muscle cible.");
            errorLabel.setTextFill(Color.RED);
            musculesCiblesBox.getParent().getChildrenUnmodifiable().add(errorLabel);
            return false;
        }
        musculesCiblesBox.getParent().getChildrenUnmodifiable().removeIf(node -> node instanceof Label);
        return true;
    }

    private static void setFieldError(TextField field, String errorMessage) {
        field.setStyle("-fx-border-color: red;");
        field.setPromptText(errorMessage);
    }

    private static void clearFieldError(TextField field) {
        field.setStyle(null);
        field.setPromptText(null);
    }
}
