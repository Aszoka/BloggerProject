package application.database;

import application.config.ConfigReader;
import application.model.blogs.Blog;
import application.model.blogs.Comment;
import application.model.blogs.Post;
import application.model.blogs.PostType;
import application.model.template.Template;
import application.model.users.Role;
import application.model.users.User;
import lombok.Getter;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DBEngine {
    Connection connection;
    ConfigReader configReader;
    @Getter
    private List<User> allUsers;
    private List<Template> templates;

    public DBEngine() {
        configReader = new ConfigReader();
        connection = connect();
        System.out.println(isConnected());
        allUsers = new LinkedList<>();
        templates = new LinkedList<>();
        loadAllUsers();
       // System.out.println(allUsers);

    }

    private boolean isConnected(){
        return connection !=null;
    }

    private Connection connect(){
        String url = configReader.getDbPath() +
                configReader.getCharEncoding();

        Properties properties = new Properties();
        properties.put("user", System.getenv("DB_USER"));
        properties.put("password", System.getenv("DB_PASSWORD"));

        try {
            return DriverManager.getConnection(url,properties);
        } catch (SQLException e){
            System.out.println("error");
            return null;
        }
    }

    private ResultSet getResultSet(Connection connection, String query) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(query);


        return preparedStatement.executeQuery();
    }

    ResultSet getResultSet(Connection connection, String query, long searched) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, searched);

        return preparedStatement.executeQuery();
    }

    ResultSet getResultSet(Connection connection, String query, String searched) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, searched);

        return preparedStatement.executeQuery();
    }

    public List<User> loadAllUsers(){
        String query = configReader.getSelect() + configReader.getUser();

        ResultSet resultSet;

        try {
            resultSet = getResultSet(connection, query);

            while (resultSet.next()) {
                long user_id = resultSet.getLong("user_id");
                String username = resultSet.getString("username");
                String fullName = resultSet.getString("real_name");
                String email = resultSet.getString("email_address");
                Timestamp regTime = resultSet.getTimestamp("time_of_reg");
                String password = resultSet.getString("user_password");
                Role userRole = Role.valueOf(resultSet.getString("role").toUpperCase());

                User user = new User(user_id, username,fullName,email,regTime,password,userRole);
                user.setBlogList(loadBlogsByUser(username));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public User loadUser(String username){
        String query = "SELECT * FROM USER WHERE USERNAME = ?";

        ResultSet resultSet;

        User user = null;

        try {
            resultSet = getResultSet(connection, query, username);

            while (resultSet.next()) {
                long user_id = resultSet.getLong("user_id");
                String userName = resultSet.getString("username");
                String fullName = resultSet.getString("real_name");
                String email = resultSet.getString("email_address");
                Timestamp regTime = resultSet.getTimestamp("time_of_reg");
                String password = resultSet.getString("user_password");
                Role userRole = Role.valueOf(resultSet.getString("role").toUpperCase());

                user = new User(user_id, userName,fullName,email,regTime,password,userRole);
                user.setBlogList(loadBlogsByUser(userName));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<Blog> loadBlogsByUser(String username){
        List<Blog> blogList = new LinkedList<>();

        String query = configReader.getSelect() + configReader.getBlog() + configReader.getWhereAuthor();

        ResultSet resultSet;
        try {
            resultSet = getResultSet(connection, query, username);
            while(resultSet.next()){
                long blogID = resultSet.getLong("blog_id");
                String blogTitle = resultSet.getString("blog_title");
                String author = resultSet.getString("blog_author_id");
                String templateID = (resultSet.getString("blog_template_id")) ;

                Blog blog = new Blog(blogID,blogTitle,author,templateID);
                blog.setTemplate(loadTemplateForBlog(templateID));
                blog.setPostList(loadPostsForBlog(blogID));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  blogList;
    }

    public Template loadTemplateForBlog(String templateId){


        String query = configReader.getSelect() + configReader.getTemplate() + configReader.getWhereTemplateId();
        ResultSet resultSet;

        try {
            resultSet = getResultSet(connection, query, templateId);
            while (resultSet.next()) {

                String tempID = resultSet.getString("template_id");
                String category = resultSet.getString("category");
                String colorTheme = (resultSet.getString("color_theme"));

                Template template = new Template(tempID,category,colorTheme);
                templates.add(template);

            }
        } catch (SQLException e){
            System.out.println("error");
        }

        return templates.get(0);
    }

    public List<Post> loadPostsForBlog(long blogId){
        List<Post> postList = new LinkedList<>();

        String query = configReader.getSelect() + configReader.getPost() + configReader.getWhereBlogId();

        ResultSet resultSet;
        try {
            resultSet = getResultSet(connection, query, blogId);
            while(resultSet.next()){
                long postId = resultSet.getLong("post_id");
                String author = resultSet.getString("post_author_id");
                long blogID = resultSet.getLong("post_blog_id");
                String title = resultSet.getString("post_title");
                String text = resultSet.getString("post_body");
                PostType type = PostType.valueOf(resultSet.getString("post_type").toUpperCase());
                boolean published = resultSet.getBoolean("published");
                Timestamp postingTime = resultSet.getTimestamp("posting_time");

                Post post = new Post(postId,author,blogID,title,text,type,published,postingTime);
                post.setCommentList(loadCommentsForPost(postId));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return postList;
    }

    public List<Comment> loadCommentsForPost(long postId){
        List<Comment> commentList = new LinkedList<>();

        String query = configReader.getSelect() + configReader.getComment() + configReader.getWherePostId();

        ResultSet resultSet;
        try {
            resultSet = getResultSet(connection, query, postId);
            while(resultSet.next()){

                long commentId = resultSet.getLong("comment_id");
                String commentAuthor = resultSet.getString("comment_author_id");
                long postID = resultSet.getLong("post_id");
                String text = resultSet.getString("comment_text");
                long replyID = resultSet.getLong("reply_id");
                Timestamp sendingTime = resultSet.getTimestamp("sending_time");


                Comment comment = new Comment(commentId,commentAuthor,postID,text,replyID, sendingTime);
                comment.setReplies(loadRepliesForComments(commentId));
                commentList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return commentList;
    }

    public List<Comment> loadRepliesForComments(long commentId){
        List<Comment> replyList = new LinkedList<>();

        String query = configReader.getSelect() + configReader.getComment() + configReader.getWhereCommentId();

        ResultSet resultSet;
        try {
            resultSet = getResultSet(connection, query, commentId);
            while(resultSet.next()){

                long commentID = resultSet.getLong("comment_id");
                String commentAuthor = resultSet.getString("comment_author_id");
                long postID = resultSet.getLong("post_id");
                String text = resultSet.getString("comment_text");
                long replyID = resultSet.getLong("reply_id");
                Timestamp sendingTime = resultSet.getTimestamp("sending_time");


                Comment comment = new Comment(commentID,commentAuthor,postID,text,replyID, sendingTime);
                replyList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return replyList;
    }


}
