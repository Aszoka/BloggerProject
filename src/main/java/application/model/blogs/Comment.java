package application.model.blogs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class Comment {
    @Getter
    private long commentID;
    @Getter
    private String commentAuthorID;
    @Getter
    private long postID;
    @Getter
    @Setter
    private String commentText;
    @Getter
    private long replyID;
    @Getter
    private Timestamp sendingTime;
    @Getter
    @Setter
    private List<Comment> replies;

    public Comment(long commentID,
                   String commentAuthorID,
                   long postID,
                   String commentText,
                   long replyID,
                   Timestamp sendingTime) {
        this.commentID = commentID;
        this.commentAuthorID = commentAuthorID;
        this.postID = postID;
        this.commentText = commentText;
        this.replyID = replyID;
        this.sendingTime = sendingTime;

        replies = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentAuthorID='" + commentAuthorID + '\'' +
                ", commentText='" + commentText + '\'' +
                ", sendingTime=" + sendingTime +
                ", replies=" + replies +
                '}';
    }
}
