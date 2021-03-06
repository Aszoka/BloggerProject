package application.module;

import application.model.users.Role;
import application.model.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {


     UserController ucTest = new UserController();
     List<User> testUserList = ucTest.getAllUsers();
     User admin = testUserList.get(0);
     User sanyi = testUserList.get(4);
     User magda = testUserList.get(5);
     User poe = testUserList.get(3);

/*
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
    }*/

    @Test
    void getUsersByRole() {

        List<User> expected = new LinkedList<>();
        expected.add(testUserList.get(0));
        expected.add(testUserList.get(5));

        List<User> actual = ucTest.getUsersByRole(Role.ADMIN);

        Assertions.assertTrue(compareLists(expected,actual));
    }

    @Test
    void getUserDataOwn() {
        User expected = testUserList.get(4);
        User actual = ucTest.getUserData(testUserList.get(4), "sanyi823");

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getUserDataAdmin() {
        User expected = testUserList.get(4);
        User actual = ucTest.getUserData(testUserList.get(0), "sanyi823");

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getUserDataNoPermission() {
        User actual = ucTest.getUserData(testUserList.get(3), "sanyi823");

        assertNull(actual);
    }

    @Test
    void changeOldUserDataOwn() {
        User changed = testUserList.get(2);
        changed.setFullName("Murakami Haruki");
        User expected = testUserList.get(2);
        User actual = ucTest.changeOldUserData(testUserList.get(2));
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void editPassword() {
        String oldPassInput = "ejjmiak??";
        String newPassInput = "ty??kany??";

        Assertions.assertTrue(ucTest.editPassword(sanyi,oldPassInput,newPassInput));
    }

    @Test
    void editPasswordWrongPass() {
        String oldPassInput = "kend";
        String newPassInput = "ty??kany??";

        Assertions.assertFalse(ucTest.editPassword(sanyi,oldPassInput,newPassInput));
    }
    @Test
    void changeUserPassword() {
        String input = "ty??kany??";
        long inputId = sanyi.getUser_id();

      Assertions.assertEquals("ty??kany??", ucTest.changeUserPassword(inputId, input));
    }

    @Test
    void banUser() {
        Assertions.assertTrue(ucTest.banUser(magda, sanyi));
    }

    @Test
    void banUserNotAdmin() {
        Assertions.assertFalse(ucTest.banUser(sanyi, poe));
    }

    @Test
    void banUserWhoIsAdmin() {
        Assertions.assertFalse(ucTest.banUser(admin,magda));
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