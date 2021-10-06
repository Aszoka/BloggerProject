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
    private DBEngine dbEngine;

    public UserController() {
        dbEngine = new DBEngine();
        allUsers = dbEngine.loadAllUsers();
        System.out.println("search userinfo");
        getUserData(allUsers.get(0),"Poetrist");
        User user = allUsers.get(3);
        System.out.println(user.getFullName());
        user.setFullName("Edgar A. Poe");
        changeOldUserData(user);
        System.out.println(user.getFullName());

    }

    public List<User> getUsersByRole(Role role){
        List<User> usersByRole = new LinkedList<>();

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
            System.out.println("You don't have permission to access this info");

        }
        return null;
    }

    public void changeOldUserData(User oldUserWithNewData) {
           for(User u : allUsers){
               if(u.getUser_id() == oldUserWithNewData.getUser_id()){
                   u = oldUserWithNewData;
               } else{
                   System.out.println("no changes");
               }
           }
    }

    public void changeUserPassword(long userId, String newPassword) {

    }

}
