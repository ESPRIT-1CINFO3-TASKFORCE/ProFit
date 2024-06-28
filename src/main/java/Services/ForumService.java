package Services;

import Entites.ForumEntity;
import GUI.Forum;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForumService {


    public void addForum(ForumEntity forum) throws SQLException {
        String sql = "INSERT INTO forum (createur, titre, topique, contenu) VALUES (?, ?, ?, ?)";
        try (Connection con = DataSource.getInstance().getCon();
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
        try (Connection con = DataSource.getInstance().getCon();
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
                    //forum.setDateCreation(rs.getDate("dateCreation"));
                    forums.add(forum);
                }
            }
            return forums;
        }
    }
}
