package Services;

import Entites.MessageEntity;
import Utils.DataSource;

import java.sql.*;

public class MessageService {

    public void insertMessage(MessageEntity message) {
        String sql = "INSERT INTO message (envoyeur, recepteur, messages, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection con = DataSource.getInstance().getCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

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


    public String getConversation(int userId, int recipientId) {
        Connection con = DataSource.getInstance().getCon();
        String sql = "SELECT m.*, u.prenom FROM message m " +
                "JOIN user u ON m.envoyeur = u.id " +
                "WHERE (m.envoyeur = ? AND m.recepteur = ?) OR (m.envoyeur = ? AND m.recepteur = ?)";

        StringBuilder conversation = new StringBuilder();

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, recipientId);
            stmt.setInt(3, recipientId);
            stmt.setInt(4, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int senderId = rs.getInt("envoyeur");
                String senderName = rs.getString("prenom");
                String timestamp = rs.getString("timestamp");
                String message = rs.getString("messages");

                // Append sender's name, timestamp, and message to the conversation
                conversation.append(" [").append(timestamp).append("] ").append(senderName).append(": ").append(message).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching messages: " + e.getMessage());
        }

        return conversation.toString();
    }

    public String getNameById(int userId) {
        Connection con = DataSource.getInstance().getCon();
        String sql = "SELECT name FROM user WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user name: " + e.getMessage());
        }

        return "";
    }

    public MessageEntity getLastMessage(int envoyeurId, int recepteurId) {
        String query = "SELECT * FROM message WHERE envoyeur = ? AND recepteur = ? ORDER BY timestamp DESC LIMIT 1";
        try (Connection conn = DataSource.getInstance().getCon();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn.isClosed()) {
                System.out.println("Connection is closed. Re-establishing connection.");
                // Logic to re-establish connection
            }

            stmt.setInt(1, envoyeurId);
            stmt.setInt(2, recepteurId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MessageEntity message = new MessageEntity();
                    message.setId(rs.getInt("id_message"));
                    message.setEnvoyeur(rs.getInt("envoyeur"));
                    message.setRecepteur(rs.getInt("recepteur"));
                    message.setMessage(rs.getString("messages"));
                    message.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    return message;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

