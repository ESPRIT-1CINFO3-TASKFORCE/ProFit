package Entites;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int comment_id;
    private int forum_id;
    private int user_id;
    private String comment;
    private LocalDateTime date_comment;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getForum_id() {
        return forum_id;
    }

    public void setForum_id(int forum_id) {
        this.forum_id = forum_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate_comment() {
        return date_comment;
    }

    public void setDate_comment(LocalDateTime date_comment) {
        this.date_comment = date_comment;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "comment_id=" + comment_id +
                ", forum_id=" + forum_id +
                ", user_id=" + user_id +
                ", comment='" + comment + '\'' +
                ", date_comment=" + date_comment +
                '}';
    }
}
