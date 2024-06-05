package GUI;

import Entites.MessageEntity;
import Services.MessageService;
import Utils.DataSource;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class Chat extends Application {

    private MessageService messageService = new MessageService();
    private TextArea messageDisplayArea;
    private TextField messageInputField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ProFit Chat");

        // Create the text area for displaying messages
        messageDisplayArea = new TextArea();
        messageDisplayArea.setEditable(false);

        // Create the text field for inputting messages
        messageInputField = new TextField();
        messageInputField.setPromptText("Entrez votre message ici...");

        // Create the send button
        Button sendButton = new Button("Envoyer");
        // calls send message method
        sendButton.setOnAction(event -> sendMessage());

        // Create a VBox layout and add the components
        VBox layout = new VBox(15, messageDisplayArea, messageInputField, sendButton);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void sendMessage() {
        String messageText = messageInputField.getText();
        if (messageText.isEmpty()) {
            return;
        }

        // Create a new message entity
        MessageEntity message = new MessageEntity();
        message.setEnvoyeur(1); // Assuming the sender ID is 1 for simplicity
        message.setRecepteur(2); // Assuming the receiver ID is 2 for simplicity
        message.setMessage(messageText);
        message.setTimestamp(LocalDateTime.now());

        // Insert the message into the database
        messageService.insertMessage(message);

        // Display the message in the text area
        messageDisplayArea.appendText("Moi : " + messageText + "\n");

        // Clear the input field
        messageInputField.clear();
    }

    public static void main(String[] args) {
        // Ensure DataSource is initialized
        DataSource.getInstance();
        launch(args);
    }
}

