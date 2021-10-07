package application.module;

import application.model.users.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlogControllerTest {
    BlogController bcTester = new BlogController();
    private List<User> usersTest = bcTester.getAllUsers();

    @Test
    void readPost() {
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
}