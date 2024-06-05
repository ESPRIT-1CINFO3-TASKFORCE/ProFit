package Entites;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int envoyeur;
    private int recepteur;
    private String message;
    private LocalDateTime timestamp;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(int envoyeur) {
        this.envoyeur = envoyeur;
    }

    public int getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(int recepteur) {
        this.recepteur = recepteur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", envoyeur=" + envoyeur +
                ", recepteur=" + recepteur +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
