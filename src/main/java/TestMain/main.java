package TestMain;

import Entites.MessageEntity;
import Services.MessageService;

import java.time.LocalDateTime;

public class main {
    public static void main(String[] args) {
        MessageService messageService = new MessageService();

        MessageEntity message = new MessageEntity();
        message.setEnvoyeur(1);
        message.setRecepteur(2);
        message.setMessage("Hello, this is a test message!");
        message.setTimestamp(LocalDateTime.now());

        //FOR TEST messageService.insertMessage(message);
    }
}
