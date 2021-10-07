package application.module;

import application.database.DBEngine;
import application.model.blogs.Comment;
import application.model.blogs.Post;
import application.model.users.User;
import lombok.Getter;

import java.util.List;

public class BlogController {
    /** create blog, create post, edit, delete etc
     * comments?*/
    @Getter
    private List<User> allUsers;
    private DBEngine dbEngine;

    public BlogController() {
        dbEngine = new DBEngine();
        allUsers = dbEngine.loadAllUsers();
    }

    public String readPost(Post post){
        return post.toString();
    }

    public Post editPost(User user, Post post, String newTitle, String newText){

        if(canUpdate(user, post)){
            post.setPostTitle(newTitle);
            post.setPostBody(newText);
        }
        return post;
    }

    public Comment writeComment(Post post){
        Comment comment = new Comment();
        post.getCommentList().add(comment);

        return comment;
    }

    private boolean canUpdate(User user, Post post){
        if(post.getPostAuthorID().equals(user.getUsername())){
            return true;
        }

        return false;
    }
}
