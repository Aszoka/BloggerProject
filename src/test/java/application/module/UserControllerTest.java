package application.module;

import application.model.users.Role;
import application.model.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {


    private UserController ucTest = new UserController();
    private List<User> testUserList = ucTest.getAllUsers();


    List<User> loadUserTest(){
        long millis = System.currentTimeMillis();

        Timestamp copyTime = ucTest.getAllUsers().get(0).getRegTime();
        User user = new User(
        1,
        "adminTest",
        "Admin Test",
        "admin@bloggles.com",
                copyTime,
        "admin",
        Role.ADMIN);

        testUserList.add(user);

        return  testUserList;
    }

    @Test
    void getUsersByRole() {

        List<User> expected = new LinkedList<>();
        expected.add(testUserList.get(0));

        List<User> actual = ucTest.getUsersByRole(Role.ADMIN);

        Assertions.assertTrue(compareLists(expected,actual));
    }

    @Test
    void getUserData() {
    }

    @Test
    void changeOldUserData() {
    }

    @Test
    void changeUserPassword() {
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