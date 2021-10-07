import application.database.DBEngine;
import application.module.BlogController;
import application.module.UserController;

public class Main {
    public static void main(String[] args) {

        UserController userController = new UserController();
        BlogController blogController = new BlogController();

       /* blogController.readPost(blogController.getAllUsers().get(3).getBlogList().get(0).getPostList().get(1));
        System.out.println(blogController.readPost(blogController.getAllUsers().get(3).getBlogList().get(0).getPostList().get(1)));
        System.out.println(blogController.writeComment(blogController.getAllUsers().get(3).getBlogList().get(0).getPostList().get(0),
                blogController.getAllUsers().get(4),"Waiting for the rest!"));

        System.out.println(blogController.getAllUsers());*/

    }
}
