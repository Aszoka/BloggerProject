package application.model.users;

import application.model.blogs.Blog;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class User {
    @Getter
    private long user_id;
    @Getter
    private String username;
    @Getter
    private String fullName;
    @Getter
    private String email_address;
    @Getter
    private Timestamp regTime;
    @Getter
    private byte[] profilePic;
    @Getter
    private String password;
    @Getter
    private Role role;

    @Getter
    @Setter
    private List<Blog> blogList;
    @Getter
    @Setter
    private  boolean enabled;

    /** constructor for reading from database*/
    public User(long user_id,
                String username,
                String fullName,
                String email_address,
                Timestamp regTime,
                String password,
                Role role) {
        this.user_id = user_id;
        this.username = username;
        this.fullName = fullName;
        this.email_address = email_address;
        this.regTime = regTime;
        this.password = password;
        this.role = role;

        blogList = new LinkedList<>();
        enabled = true;
    }

    public String toString(){
        return  "Username: " + username + ", " +
                "Name: " + fullName + ", " +
                "Email address: " + email_address + ", " +
                role + "\n\t" +
                "Time of registration: " + regTime + ", " +
                blogList;
    }
}
