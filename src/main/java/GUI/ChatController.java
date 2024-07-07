package GUI;

import Entites.MessageEntity;
import Services.MessageService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatController {

    private MessageService messageService = new MessageService();
    private BufferedReader serverReader;
    private PrintWriter clientWriter;
    private Socket socket;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField inputRecepteurIDField;

    @FXML
    private TextArea messageDisplayArea;

    @FXML
    private TextField messageInputField;

    @FXML
    private Button proceedButton;

    @FXML
    private VBox loginVBox;

    private int userId;
    private String userName;
    private int recepteurID;

    @FXML
    private void proceedToChat() {
        try {
            // Load the Chat.fxml file and initialize the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
            loader.setController(this); // Set the controller instance for the loaded FXML
            Parent chatRoot = loader.load();

            // Establish socket connection to the server
            socket = new Socket("localhost", 9000);
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientWriter = new PrintWriter(socket.getOutputStream(), true);

            // Start a new thread to listen for messages from the server
            Thread serverListener = new Thread(() -> {
                try {
                    String message;
                    while ((message = serverReader.readLine()) != null) {
                        String finalMessage = message;
                        Platform.runLater(() -> messageDisplayArea.appendText(finalMessage + "\n"));
                        fetchAndDisplayMessages(); // Fetch and display messages in the chat interface
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverListener.setDaemon(true); // Ensures the thread stops when the application exits
            serverListener.start();

            // Hide the login VBox and add the chat VBox to the scene
            loginVBox.getScene().setRoot(chatRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the user ID and name from the input fields
        userId = Integer.parseInt(userIdField.getText());
        userName = userNameField.getText();
        recepteurID = Integer.parseInt(inputRecepteurIDField.getText());
        // Fetch and display messages for the logged-in user and recipient
        try {
            fetchAndDisplayMessages();

        } catch(NullPointerException e) {
            System.out.println(e);
        }
    }



    @FXML
    private void sendMessage() {
        String messageText = messageInputField.getText();
        if (messageText.isEmpty()) {
            return;
        }

        // Create a new message entity
        MessageEntity message = new MessageEntity();
        message.setEnvoyeur(userId); // Use the entered user ID
        message.setRecepteur(recepteurID);
        message.setMessage(messageText);
        message.setTimestamp(LocalDateTime.now());

        // Insert the message into the database
        messageService.insertMessage(message);

        // Fetch the inserted message from the database
        MessageEntity lastMessage = messageService.getLastMessage(userId, recepteurID);

        if (lastMessage != null) {
            // Format date
            LocalDateTime dateTime = LocalDateTime.parse(lastMessage.getTimestamp().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            // Send the fetched message to the server
            clientWriter.println(" [" + formattedDateTime + "] " + userName + ": " + lastMessage.getMessage());

            // Clear the input field
            messageInputField.clear();
        }
    }

    private void fetchAndDisplayMessages() {
        // Fetch messages for the logged-in user and recipient
        String conversation = messageService.getConversation(userId, recepteurID);
        // Display fetched messages in the text area
        messageDisplayArea.setText(conversation);
    }
}
