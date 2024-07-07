package Controllers;

import Entites.CommentEntity;
import Entites.ForumEntity;
import Services.ForumService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TopicController {

    // TOPIC

    @FXML
    private Label title;

    @FXML
    private Label topic;

    @FXML
    private Label topicDate;

    @FXML
    private Label username;

    @FXML
    private TextFlow messsage;

    @FXML
    private ListView<CommentEntity> commentId;

    private int selectedForumId;

    private ForumService forumService = new ForumService();

    public void setForumDetails(ForumEntity forum) {
        title.setText(forum.getTitre());
        messsage.getChildren().add(new Text(forum.getContenu()));
        topic.setText(forum.getTopique());
        topicDate.setText(forum.getDateCreation().toString());
        username.setText(forumService.getUserNameById(forum.getCreateur()));
        this.selectedForumId = forum.getId_forum();


        // Load and display comments associated with the forum
        List<CommentEntity> comments = forumService.getCommentsByForumId(forum.getId_forum());
        ObservableList<CommentEntity> observableComments = FXCollections.observableArrayList(comments);
        commentId.setItems(observableComments);
        commentId.setCellFactory(param -> new ListCell<CommentEntity>() {
            @Override
            protected void updateItem(CommentEntity item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(forumService.getUserNameById(item.getUser_id())+ " : " +item.getComment());
                }
            }
        });
    }

    // Method to handle "Back" button click
    @FXML
    private void goBackToForum() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forums.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) title.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Handle your exception properly
        }
    }

    // Method to handle "Back" button click
    @FXML
    private void goToNewTopic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/newTopic.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("NOUVEAU SUJET");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to open the comment popup
    @FXML
    private void openCommentPopup() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Commentaire");
        dialog.setHeaderText("Tapez votre commentaire :");

        // Set the button types
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        // Create the comment text area
        TextArea commentTextArea = new TextArea();
        commentTextArea.setPromptText("Commentaire");

        VBox vbox = new VBox(commentTextArea);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to a comment when the confirm button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return commentTextArea.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(commentText -> {
            // Process the comment (e.g., save to database or add to comments section)
            CommentEntity newComment = new CommentEntity();
            ForumEntity forum = new ForumEntity();
            newComment.setForum_id(selectedForumId);
            newComment.setUser_id(38);  // NO AUTH
            newComment.setComment(commentText);
            //newComment.setDate_comment(LocalDateTime.now());
            if (!commentText.isEmpty()) {
                // Save the new comment
                try {
                    forumService.addComment(newComment);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                    // Add the comment to the UI
                    commentId.getItems().add(newComment);
                }


        });
    }

}