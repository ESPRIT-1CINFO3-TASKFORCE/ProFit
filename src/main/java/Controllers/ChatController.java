package Controllers;

import Entites.MessageEntity;
import Entites.UserEntity;
import Services.MessageService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private VBox messagesContainer;
    @FXML
    private TextField messageInputField;

    @FXML
    private Button proceedButton;

    @FXML
    private ListView<UserEntity> searchUserList;

    @FXML
    private VBox loginVBox;
    @FXML
    private Pane container;
    private int userId;
    private String userName;
    private int recepteurID = 9999;
    @FXML
    private Label receptName;
    @FXML
    private Label receptRole;

    @FXML
    private TextField searchUserField;
    @FXML
    private Button searchUserButton;

    @FXML
    private void proceedToChat() {
        try {
            // Load the Chat.fxml file and initialize the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chat.fxml"));
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
                        Platform.runLater(() -> {
                            // Append the new message to the message display area
                            TextArea messageArea = new TextArea(finalMessage);
                            messageArea.setEditable(false);
                            messageArea.setWrapText(true);
                            messageArea.setPrefHeight(47.0); // Adjust the height as needed
                            messageArea.setFont(new Font("Malgun Gothic", 14));

                            messagesContainer.getChildren().add(messageArea);

                            // Fetch and display messages in the chat interface
                            fetchAndDisplayMessages(recepteurID);
                        });
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
        //recepteurID = Integer.parseInt(inputRecepteurIDField.getText());
        // Fetch and display messages for the logged-in user and recipient
        try {
            fetchAndDisplayMessages(recepteurID);
            chatUserList();
            //setupSearch();


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



    public void fetchAndDisplayMessages(int recepteur) {
        // Fetch messages for the logged-in user and recipient
        String conversation = messageService.getConversation(userId, recepteur);
        if (conversation.length() > 0) {
            // Clear any existing messages in the FlowPane
            messagesContainer.getChildren().clear();

            // Split the conversation into individual messages
            String[] messages = conversation.split("\n");
            // Create and add a Label for each message
            for (String message : messages) {
                Label messageLabel = new Label(message);
                messageLabel.setFont(Font.font("Malgun Gothic", 14));
                messageLabel.setWrapText(true);
                messageLabel.setTextFill(Color.WHITE); // Text color
                messageLabel.setStyle("-fx-background-color: #0594D0; -fx-background-radius: 15; -fx-padding: 10px;");
                messageLabel.setMaxWidth(430); // Max width for the bubble
                messagesContainer.getChildren().add(messageLabel);
            }

            // Add padding to the bottom to push the messages up
            messagesContainer.setPadding(new Insets(10, 20, 20, 20));
        }

        // Scroll to the bottom of the messages container
        ScrollPane scrollPane = new ScrollPane(messagesContainer);
        scrollPane.setVvalue(1.0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(450); // Set your desired height

        // Replace the existing container with the ScrollPane
       //container.getChildren().setAll(scrollPane);
    }



    public void chatUserList() {
        List<UserEntity> userList = messageService.getSearchUserList();
        ObservableList<UserEntity> observableUserList = FXCollections.observableArrayList(userList);

        FilteredList<UserEntity> filteredList = new FilteredList<>(observableUserList, p -> true);

        searchUserList.setItems(filteredList);

        // Add listener to handle row selection
        searchUserList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int userId = newValue.getId();
                this.recepteurID = userId;
                messagesContainer.getChildren().clear();
                fetchAndDisplayMessages(this.recepteurID);
                receptName.setText(newValue.getNom() + " " + newValue.getPrenom());
                receptRole.setText(newValue.getRole());

                System.out.println("ENVOYEUR:" + userId);
                System.out.println("RECEPTEUR:" + recepteurID);
            } else {
                System.out.println("invalid selected user.");
            }
        });

        // Set up the search field listener
        setupSearch(filteredList);
    }



    private void setupSearch(FilteredList<UserEntity> filteredList) {
        searchUserField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (user.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        searchUserButton.setOnAction(event -> {
            String filter = searchUserField.getText();
            filteredList.setPredicate(user -> {
                if (filter == null || filter.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = filter.toLowerCase();

                if (user.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (user.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
    }


}
