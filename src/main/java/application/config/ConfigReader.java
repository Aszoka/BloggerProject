package application.config;

import lombok.Getter;

public class ConfigReader {
    @Getter
    private final String dbPath = "jdbc:mysql://localhost:3306/blogglesdb";
    @Getter
    private final String charEncoding = "?useUnicode=yes&characterEncoding=UTF-8";

    @Getter
    private final String select = "SELECT * FROM ";
    @Getter
    private final String user = "user";
    @Getter
    private final String template = "template";
    @Getter
    private final String blog = "blog";
    @Getter
    private final String post = "post";
    @Getter
    private final String comment = "comment";

    @Getter
    private final String whereAuthor = " WHERE BLOG_AUTHOR_ID = ?";
    @Getter
    private final String whereTemplateId = " WHERE TEMPLATE_ID = ?";
    @Getter
    private final String whereBlogId = " WHERE POST_BLOG_ID = ?";
    @Getter
    private final String wherePostId = " WHERE POST_ID = ?";
    @Getter
    private final String whereCommentId = " WHERE REPLY_ID = ?";


}
