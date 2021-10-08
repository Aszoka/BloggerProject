package application.module;

import application.database.DBEngine;
import application.model.blogs.Blog;
import application.model.blogs.Comment;
import application.model.blogs.Post;
import application.model.users.Role;
import application.model.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogControllerTest {

    BlogController bcTester = new BlogController();
    List<User> usersTest = new LinkedList<>();

    @BeforeEach
    void init(){

        usersTest = loadUserTest();
        bcTester = new BlogController();
        bcTester.setAllUsers(usersTest);

        System.out.println(usersTest);
    }



    @Test
    void readPost() {

        Post tester = usersTest.get(1).getBlogList().get(0).getPostList().get(0);


        String expected = tester.toString();
        String actual = bcTester.readPost(tester);

        Assertions.assertEquals(expected,actual);

    }

    @Test
    void editPost() {
        User editor = usersTest.get(1);
        Post toBeEdited = usersTest.get(1).getBlogList().get(0).getPostList().get(0);
        String newTitle = "Will this work?";
        String newText = "We will see....";

        Post expected = toBeEdited;
        expected.setPostTitle(newTitle);
        expected.setPostBody(newText);

        Assertions.assertEquals(expected, bcTester.editPost(editor, toBeEdited, newTitle, newText));


    }

    @Test
    void editPostNoPermission() {
        User editor = usersTest.get(2);
        Post toBeEdited = usersTest.get(1).getBlogList().get(0).getPostList().get(0);
        String newTitle = "I wanna get some trolling done";
        String newText = "We will see....";

        Post expected = toBeEdited;
        expected.setPostTitle(newTitle);
        expected.setPostBody(newText);

        Assertions.assertNull( bcTester.editPost(editor, toBeEdited, newTitle, newText));


    }

    @Test
    void deletePost() {

        User deleter = usersTest.get(1);
        Post toBeDeleted = deleter.getBlogList().get(0).getPostList().get(1);

        List<Post> expected = new LinkedList<>();
        expected.add(deleter.getBlogList().get(0).getPostList().get(0));
        List<Post> actual = bcTester.deletePost(deleter, toBeDeleted,deleter.getBlogList().get(0));

        Assertions.assertTrue(compareLists(expected, actual));


    }

    @Test
    void deletePostNoPermission() {

        User deleter = usersTest.get(2);
        Post toBeDeleted = usersTest.get(1).getBlogList().get(0).getPostList().get(1);
        Blog blog = usersTest.get(1).getBlogList().get(0);

        Assertions.assertNull(bcTester.deletePost(deleter, toBeDeleted, blog));
    }

    @Test
    void writeComment() {
        User commenter = usersTest.get(0);
        Post commented = usersTest.get(1).getBlogList().get(0).getPostList().get(0);
        String comment = "comment";
        long commentId = 4;

        Comment expected = new Comment(commentId,commenter.getUsername(),commented.getPostID(),comment);
        Comment actual = bcTester.writeComment(commented,commenter,comment,commentId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void canUpdate(){
        User tester = usersTest.get(1);
        Post test = tester.getBlogList().get(0).getPostList().get(0);

        Assertions.assertTrue(bcTester.canUpdate(tester, test));
    }

    @Test
    void canUpdateNoPermission(){
        User tester = usersTest.get(1);
        Post test = new Post(10, "magda", 12,"title", "text");

        Assertions.assertFalse(bcTester.canUpdate(tester, test));
    }

    @Test
    void canUpdateAdmin(){
        User tester = usersTest.get(0);
        Post test = usersTest.get(1).getBlogList().get(0).getPostList().get(0);

        Assertions.assertTrue(bcTester.canUpdate(tester, test));
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
                Role.USER);

        second.getBlogList().add(loadBlogTest(1,"Testing Blog Title","user","Sparrow"));
        second.getBlogList().get(0).getPostList().add(loadPost(1,"user", 1,"post title",
                "this is a test post"));
        second.getBlogList().get(0).getPostList().add(loadPost(2,"user", 1,"to be removed",
                "this is a test post for removing the post xD"));
        second.getBlogList().get(0).getPostList().get(0).getCommentList().add(loadComment(
                1,
                "adminTest",
                1,
                "Test comment"
        ));

        User third = new User(
                3,
                "theBadOne",
                "I can't change",
                "noppee@test.com",
                "nope",
                Role.USER);
        usersTest.add(user);
        usersTest.add(second);
        usersTest.add(third);

        return  usersTest;
    }

    Blog loadBlogTest(long id, String title, String username, String template){

        return new Blog(
                id,
                title,
                username,
                template
        );
    }

    Post loadPost(long id, String username, long blogId, String title, String text){
        return new Post(id, username,blogId,title,text);
    }

    Comment loadComment(long commentId, String username, long postId, String text){

        return new Comment(commentId,username,postId,text);
    }

    private <T> boolean compareLists(List<T> expected, List<T> actual) {
        if (!(expected.size() == actual.size())) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!(expected.get(i).equals(actual.get(i))))
                return false;
        }

        return true;
    }
}