package GUI;

import Entites.ForumEntity;
import GUI.Forum;
import Services.ForumService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ForumController {

    @FXML
    private ListView<String> forumListView;

    @FXML
    private TextField titreField;
    //provisoire
    @FXML
    private TextField userId;
    @FXML
    private TextField topicField;
    @FXML
    private TextArea messageField;

    private ForumService forumService = new ForumService();
    private ObservableList<String> forumList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        try {
            loadForums();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void NavigateToNewTopic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newTopic.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("NOUVEAU SUJET");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelTopic() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) titreField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addTopic() {
        ForumEntity forum = new ForumEntity();
        forum.setCreateur(Integer.parseInt(userId.getText()));
        forum.setTitre(titreField.getText());
        forum.setTopique(topicField.getText());
        forum.setContenu(messageField.getText());
        //forum.setDateCreation(new java.util.Date());

        try {
            forumService.addForum(forum);
            System.out.println("Topic added successfully.");
            loadForums();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadForums() throws SQLException {
        forumList.clear();
        for (ForumEntity forum : forumService.getForums()) {
            forumList.add(forum.getTitre() + " - " + forum.getCreateur());
        }
        if (forumList.isEmpty()) {
            forumList.add("No topics found");
        }
        //forumListView.setItems(forumList);
    }

}