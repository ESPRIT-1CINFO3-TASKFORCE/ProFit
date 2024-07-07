package Utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Application {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9000;
    private PrintWriter writer;
    private TextArea messageDisplayArea;
    private TextField messageInputField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Client");

        messageDisplayArea = new TextArea();
        messageDisplayArea.setEditable(false);

        messageInputField = new TextField();
        messageInputField.setPromptText("Enter your message here...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendMessage());

        VBox layout = new VBox(10, messageDisplayArea, messageInputField, sendButton);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Received message: " + message);
                        messageDisplayArea.appendText(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageInputField.getText();
        if (!message.isEmpty()) {
            writer.println(message);
            messageInputField.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
