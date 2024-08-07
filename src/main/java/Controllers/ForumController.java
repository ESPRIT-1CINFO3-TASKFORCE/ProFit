package Controllers;

import Entites.ForumEntity;
import Entites.UserEntity;
import Services.ForumService;
import Utils.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ForumController {

    @FXML
    private ListView<String> forumCardPane;
    @FXML
    private TextField titreField;
    //provisoire
    @FXML
    private TextField userId;
    @FXML
    private ComboBox<String> topicField;
    @FXML
    private TextArea messageField;
    @FXML
    private Label newTopicAlert;

    @FXML
    private TableView<ForumEntity> forumTablePane;

    @FXML
    private TableColumn<ForumEntity, String> topiqueColumn;

    @FXML
    private TableColumn<ForumEntity, String> titreColumn;

    @FXML
    private TableColumn<ForumEntity, String> createurColumn;

    @FXML
    private TableColumn<ForumEntity, String> dateColumn;

    @FXML
    private Label currentUserId;

    private ForumService forumService = new ForumService();
    UserEntity currentUser = SessionManager.getSession();

    private ObservableList<String> forumList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        try {
            loadForums();
            if (currentUserId != null) {
                currentUserId.setText(forumService.getUserNameById(currentUser.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void NavigateToNewTopic() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newTopic.fxml"));
            Parent root = loader.load();
            // Get the current stage and set the new scene
            Stage stage = (Stage) forumTablePane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Handle your exception properly
        }
    }

    @FXML
    private void cancelTopic() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forums.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) topicField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Handle your exception properly
        }    }

    private void closeWindow() {
        Stage stage = (Stage) titreField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addTopic() {
        if (titreField.getText().isEmpty() || topicField.getValue().isEmpty() || messageField.getText().isEmpty()) {

            newTopicAlert.setText("Tous les champs sont obligatoires !");
            newTopicAlert.setStyle("-fx-text-fill: red;");
        } else {
            ForumEntity forum = new ForumEntity();
            forum.setCreateur(currentUser.getId());
            forum.setTitre(titreField.getText());
            forum.setTopique((String) topicField.getValue());
            forum.setContenu(messageField.getText());
            //forum.setDateCreation(new java.util.Date());

            try {
                forumService.addForum(forum);
                newTopicAlert.setText("Vous avez ajouté un nouveau sujet !");
                newTopicAlert.setStyle("-fx-text-fill: green;"); // Set the success message to green
                System.out.println("Topic added successfully.");
                loadForums();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/forums.fxml"));
                    Parent root = loader.load();

                    // Get the current stage and set the new scene
                    Stage stage = (Stage) topicField.getScene().getWindow();
                    stage.setScene(new Scene(root));


            } catch (SQLException e) {
                newTopicAlert.setText("Erreur !");
                newTopicAlert.setStyle("-fx-text-fill: red;");
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void loadForums() throws SQLException {
        if (topiqueColumn != null && titreColumn != null && createurColumn != null && dateColumn != null) {
            try {
                topiqueColumn.setCellValueFactory(new PropertyValueFactory<>("topique"));
                titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));

                // replacing user id by username
                createurColumn.setCellValueFactory(cellData -> {
                    int userId = cellData.getValue().getCreateur();
                    String username = forumService.getUserNameById(userId);
                    return new SimpleStringProperty(username);
                });                dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
                List<ForumEntity> forums = forumService.getForums();
                forumTablePane.getItems().addAll(forums);

                // Add listener to handle row selection
                forumTablePane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        navigateToForumDetails(newValue);
                    }
                });

            } catch (SQLException e) {
                e.printStackTrace(); // Handle your exception properly
            }
        }

    }


    // navigate to selected topic
    private void navigateToForumDetails(ForumEntity selectedForum) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/topic.fxml"));
            Parent root = loader.load();

            // Get the controller of topic.fxml and pass the selected forum
            TopicController topicController = loader.getController();
            topicController.setForumDetails(selectedForum);

            // Get the current stage and set the new scene
            Stage stage = (Stage) forumTablePane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Handle your exception properly
        }
    }

    @FXML
    private void backToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SideBar.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) forumTablePane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Handle your exception properly
        }

    }

}