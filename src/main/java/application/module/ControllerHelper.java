package application.module;

import application.model.blogs.Post;
import application.model.users.User;

public class ControllerHelper {

    static boolean canUpdate(User user, Post post){
        return post.getPostAuthorID().equals(user.getUsername()) || user.getRole().getLabel().equals("admin");
    }
}
