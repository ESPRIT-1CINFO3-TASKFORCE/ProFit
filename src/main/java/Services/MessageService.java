package Services;

import Entites.MessageEntity;
import Utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageService {

    public void insertMessage(MessageEntity message) {
        Connection con = DataSource.getInstance().getCon();
        String sql = "INSERT INTO message (envoyeur, recepteur, messages, timestamp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, message.getEnvoyeur());
            stmt.setInt(2, message.getRecepteur());
            stmt.setString(3, message.getMessage());
            stmt.setObject(4, message.getTimestamp());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Message sent successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}

