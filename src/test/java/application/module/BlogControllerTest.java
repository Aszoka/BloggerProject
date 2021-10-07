package application.module;

import application.model.blogs.Blog;
import application.model.blogs.Comment;
import application.model.blogs.Post;
import application.model.users.Role;
import application.model.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlogControllerTest {
    @Mock
    BlogController bcTester = new BlogController();
    List<User> usersTest = new LinkedList<>();



    @Test
    void readPost() {
        Mockito.when(bcTester.getAllUsers()).thenReturn(loadUserTest());
    //    Mockito.when(bcTester.readPost(usersTest.get(1).getBlogList().get(0).getPostList().get(0))).thenCallRealMethod();

        String expected = usersTest.get(1).getBlogList().get(0).getPostList().get(0).toString();
        String actual = bcTester.readPost(usersTest.get(1).getBlogList().get(0).getPostList().get(0));

        Assertions.assertEquals(expected,actual);



    }

    @Test
    void editPost() {
    }

    @Test
    void deletePost() {
    }

    @Test
    void writeComment() {
    }

    List<User> loadUserTest(){

        User user = new User(
                1,
                "adminTest",
                "Admin Test",
                "admin@test.com",
                "admin",
                Role.ADMIN);

        User second = new User(
                2,
                "user",
                "User Test",
                "user@test.com",
                "user",
                Role.ADMIN);

        second.getBlogList().add(loadBlogTest(1,"Testing Blog Title","user","Sparrow"));
        second.getBlogList().get(0).getPostList().add(loadPost(1,"user", 1,"post title",
                "this is a test post"));
        second.getBlogList().get(0).getPostList().get(0).getCommentList().add(loadComment(
                1,
                "adminTest",
                1,
                "Test comment"
        ));

        usersTest.add(user);

        return  usersTest;
    }

    Blog loadBlogTest(long id, String title, String username, String template){
        Blog blog = new Blog(
                id,
                title,
                username,
                template
        );

        return blog;
    }

    Post loadPost(long id, String username, long blogId, String title, String text){
        Post post = new Post(id, username,blogId,title,text);
        return post;
    }

    Comment loadComment(long commentId, String username, long postId, String text){
        Comment comment = new Comment(commentId,username,postId,text);

        return  comment;
    }
}