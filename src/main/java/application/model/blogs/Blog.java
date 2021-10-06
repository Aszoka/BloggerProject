package application.model.blogs;

import application.model.template.Template;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Blog {

        @Getter
        private long blogID;
        @Getter
        private String blogTitle;
        @Getter
        private String author;
        @Getter
        private String templateID;
        @Getter
        @Setter
        private Template template;

        @Getter
        @Setter
        private List<Post> postList;

        public Blog(long blogID, String blogTitle, String author, String templateID) {
            this.blogID = blogID;
            this.blogTitle = blogTitle;
            this.author = author;
            this.templateID = templateID;

            template = new Template();
            postList = new LinkedList<>();
        }

        public String toString(){
            return blogTitle + ", " +
                    " Author: " + author + ". " +
                    "Blog theme used: " + templateID;
        }
}
