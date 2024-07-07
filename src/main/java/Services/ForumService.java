package Services;

import Entites.CommentEntity;
import Entites.ForumEntity;
import GUI.Forum;
import Utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ForumService {


    public void addForum(ForumEntity forum) throws SQLException {
        String sql = "INSERT INTO forum (createur, titre, topique, contenu) VALUES (?, ?, ?, ?)";
        try (Connection con = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, forum.getCreateur());
            pstmt.setString(2, forum.getTitre());
            pstmt.setString(3, forum.getTopique());
            pstmt.setString(4, forum.getContenu());

            //pstmt.setString(5, forum.getComm());
            //pstmt.setDate(6, new java.sql.Date(forum.getDateCreation().toLocalTime()));
            pstmt.executeUpdate();
        }
    }

    public List<ForumEntity> getForums() throws SQLException {
        List<ForumEntity> forums = new ArrayList<>();
        String sql = "SELECT * FROM forum";
        try (Connection con = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery(sql);
            {
                while (rs.next()) {
                    ForumEntity forum = new ForumEntity();
                    forum.setId_forum(rs.getInt("id_forum"));
                    forum.setCreateur(rs.getInt("createur"));
                    forum.setTitre(rs.getString("titre"));
                    forum.setTopique(rs.getString("topique"));
                    forum.setContenu(rs.getString("contenu"));
                    //forum.setComm(rs.getString("comm"));
                    forum.setDateCreation((rs.getTimestamp("dateCreation").toLocalDateTime()));
                    forums.add(forum);
                }
            }
            return forums;
        }
    }


    public void addComment(CommentEntity comments) throws SQLException {
        String sql = "INSERT INTO comments (forum_id, user_id, comment) VALUES (?, ?, ?)";
        try (Connection con = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, comments.getForum_id());
            pstmt.setInt(2, comments.getUser_id());
            pstmt.setString(3, comments.getComment());

            pstmt.executeUpdate();
        }
    }



    // Method to fetch comments by forum_id
    public List<CommentEntity> getCommentsByForumId(int forumId) {

        List<CommentEntity> comments = new ArrayList<>();

        String query = "SELECT comment_id, forum_id, user_id, comment, date_comment FROM comments WHERE forum_id = ?";

        try (Connection con = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, forumId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                CommentEntity comment = new CommentEntity();
                comment.setComment_id(resultSet.getInt("comment_id"));
                comment.setForum_id(resultSet.getInt("forum_id"));
                comment.setUser_id(resultSet.getInt("user_id"));
                comment.setComment(resultSet.getString("comment"));
                comment.setDate_comment(resultSet.getTimestamp("date_comment").toLocalDateTime());

                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle your exception properly
        }

        return comments;
    }

    public String getUserNameById(int userId) {
        String username = "";
        String query = "SELECT nom, prenom FROM user WHERE id = ?";

        try (Connection con = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String nom = resultSet.getString(1);
                String prenom = resultSet.getString(2);
                username = nom + " " + prenom;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle your exception properly
        }

        return username;
    }



}
