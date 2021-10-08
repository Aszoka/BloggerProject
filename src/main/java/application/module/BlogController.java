package application.module;

import application.database.DBEngine;
import application.model.blogs.Blog;
import application.model.blogs.Comment;
import application.model.blogs.Post;
import application.model.users.User;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class BlogController {
    /** create blog, create post, edit, delete etc
     * comments?*/
    @Getter
    @Setter
    private List<User> allUsers;
    private DBEngine dbEngine;

    public BlogController() {
        dbEngine = new DBEngine();
        allUsers = dbEngine.getAllUsers();
    }

    public String readPost(Post post){
        return post.toString();
    }

    public Post editPost(User user, Post post, String newTitle, String newText){

        if(canUpdate(user, post)){
            post.setPostTitle(newTitle);
            post.setPostBody(newText);
            return post;
        }
        return null;
    }

    public List<Post> deletePost(User user, Post post, Blog blog){
        List<Post> newPostList = new LinkedList<>();
        if(canUpdate(user, post)){
            for(Post p : blog.getPostList()){
                if( post == p) {
                    blog.getPostList().remove(post);
                    newPostList = blog.getPostList();
                    return  newPostList;
                }
            }
        } return null;
    }

    public Comment writeComment(Post post, User user, String commentText, long commentID){
        if(allUsers.contains(user)){
            Comment comment = new Comment(commentID,user.getUsername(), post.getPostID(), commentText);
            post.getCommentList().add(comment);
            return comment;
        }
        return null;
    }

    public boolean canUpdate(User user, Post post){
        return (post.getPostAuthorID().equals(user.getUsername()) || user.getRole().getLabel().equals("admin"))
                && user.isEnabled();
    }
}
