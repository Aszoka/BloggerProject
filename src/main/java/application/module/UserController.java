package application.module;

import application.database.DBEngine;
import application.model.users.Role;
import application.model.users.User;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class UserController {
    /**
     * get and edit user info depending on permission
     */

    @Getter
    private List<User> allUsers;


    public UserController() {
        DBEngine dbEngine = new DBEngine();
        allUsers = dbEngine.getAllUsers();
      //  System.out.println("search userinfo");
      //  getUserData(allUsers.get(0),"Poetrist");
        User user = dbEngine.loadUser("Poetrist");
        System.out.println(user.getFullName());
        user.setFullName("Edgar A. Poe");
        changeOldUserData(user);
        System.out.println(user.getFullName());
        System.out.println("get user by role " + getUsersByRole(Role.ADMIN));
        System.out.println(getUserData(dbEngine.loadUser("sanyi823"), "sanyi823" ));

    }

    public List<User> getUsersByRole(Role role){
        List<User> usersByRole = new LinkedList<>();

        for(User u : allUsers){
            if(u.getRole().equals(role)){
                usersByRole.add(u);
            }
        }

        return  usersByRole;
    }

    public User getUserData(User searchingUser, String username){
        User searchedUser = new User();

        if(searchingUser.getUsername().equals(username) ||
                searchingUser.getRole().getLabel().equals("admin") ||
                searchingUser.getRole().getLabel().equals("moderator")){
            for(User user : allUsers){
                if(user.getUsername().equals(username)){
                    System.out.println("User data: " + user);
                    return user;
                }
            }
        }
        System.out.println("You don't have permission to access this info");
        return null;
    }

    public User changeOldUserData(User oldUserWithNewData) {
           for(User u : allUsers){
               if(u.getUser_id() == oldUserWithNewData.getUser_id() || u.getRole().getLabel().equals("admin")){
                   u = oldUserWithNewData;
               }
           }
           return oldUserWithNewData;
    }

    public boolean editPassword(User user, String oldPassword, String newPass ){
        if(user.getPassword().equals(oldPassword)){
           user.setPassword(changeUserPassword(user.getUser_id(), newPass));
            return true;
        } return false;
    }
    // todo: asking for old password, separate method before calling this
    public String changeUserPassword(long userId, String newPassword) {

        for(User u : allUsers){
            if(u.getUser_id() == userId){
                u.setPassword(newPassword);
                return newPassword;
            }
        }
        return null;
    }

    public boolean banUser(User user, User toBeBanned) {
        if (user.getRole().getLabel().equals("admin") && !(toBeBanned.getRole().getLabel().equals("admin"))) {
            toBeBanned.setEnabled(false);
            return true;
        }
        return false;
    }

}
