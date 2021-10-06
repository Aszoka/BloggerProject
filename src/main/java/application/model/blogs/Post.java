package application.model.blogs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class Post {
    @Getter
    private long postID;
    @Getter
    private String postAuthorID;
    @Getter
    private long blogID;
    @Getter
    @Setter
    private String postTitle; // default 'no title',
    @Getter
    @Setter
    private String postBody;
    @Getter
    @Setter
    private PostType postType; //default 'normal'
    @Getter
    @Setter
    private boolean published;
    @Getter
    private Timestamp postingTime;

    @Getter
    @Setter
    private List<Comment> commentList;

    @Getter
    @Setter
    private Date edited;

    public Post(long postID,
                String postAuthorID,
                long blogID,
                String postTitle,
                String postBody,
                PostType postType,
                boolean published,
                Timestamp postingTime) {
        this.postID = postID;
        this.postAuthorID = postAuthorID;
        this.blogID = blogID;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postType = postType;
        this.published = published;
        this.postingTime = postingTime;

        commentList = new LinkedList<>();
    }

    public String toString(){
        return "Post written by: " + postAuthorID +
                " Post title: " + postTitle + "\n" +
                postBody;
    }
}
