DROP DATABASE IF EXISTS blogglesDB;
CREATE DATABASE IF NOT EXISTS blogglesDB;

USE blogglesDB;

DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user(
	user_id INT AUTO_INCREMENT,
	username VARCHAR(30) UNIQUE NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    email_address VARCHAR(100) UNIQUE,
    time_of_reg TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    profile_pic LONGBLOB,
    user_password VARCHAR(256) NOT NULL, 
    role ENUM('user', 'admin', 'moderator'),
    PRIMARY KEY(user_id)
);

DROP TABLE IF EXISTS template;
CREATE TABLE IF NOT EXISTS template(
	template_id VARCHAR(30),
    category VARCHAR(20) NOT NULL,
    color_theme VARCHAR(50) NOT NULL,
    background_picture LONGBLOB,
    PRIMARY KEY (template_id)
);

DROP TABLE IF EXISTS blog;
CREATE TABLE IF NOT EXISTS blog(
	blog_id INT AUTO_INCREMENT,
    blog_title VARCHAR(100) NOT NULL,
    blog_author_id VARCHAR(30),
    blog_template_id VARCHAR(30),
    FOREIGN KEY(blog_author_id) REFERENCES user(username),
    FOREIGN KEY (blog_template_id) REFERENCES template(template_id),
    PRIMARY KEY (blog_id)
);

DROP TABLE IF EXISTS post;
CREATE TABLE IF NOT EXISTS post(
	post_id INT AUTO_INCREMENT,
    post_author_id VARCHAR(30),
    post_blog_id INT,
    post_title VARCHAR(80) default 'no title',
    post_body LONGTEXT,
    post_type ENUM('normal', 'shakespeare', 'dickens') default 'normal', -- plain, shakespearify etc
    published BOOL default true,
    posting_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_author_id) REFERENCES user(username),
    FOREIGN KEY (post_blog_id) REFERENCES blog(blog_id),
    PRIMARY KEY (post_id)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE IF NOT EXISTS comment(
	comment_id INT AUTO_INCREMENT,
    comment_author_id VARCHAR(30),
    post_id INT,
    comment_text TEXT NOT NULL,
    reply_id INT,
    sending_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(comment_author_id) REFERENCES user(username),
    FOREIGN KEY(post_id) REFERENCES post(post_id),
    FOREIGN KEY(reply_id) REFERENCES comment(comment_id), -- az adott comment egy válasz
    PRIMARY KEY (comment_id)
);


        
SELECT * FROM comment;   

INSERT INTO user(username, real_name, email_address, user_password, role)
	VALUES
		('adminTest', 'Admin Test', 'admin@bloggles.com', 'admin', 'admin'),
		('moderatorTest', 'Moderator Test', 'moderator@bloggles.com', 'moderator', 'moderator'),
		('userTest', 'User Test', 'user@bloggles.com', 'user', 'user'),
        ('Poetrist', 'Edgar Allan Poe', 'eldorado@bloggles.com', 'raven', 'user');   
        
INSERT INTO template(template_id, category, color_theme)
	VALUES
		('Raven','novelist', 'melancholic'),
        ('Kafka','novelist', 'surrealist');  
        
INSERT INTO blog(blog_title, blog_author_id, blog_template_id)
	VALUES
		('The Pit and the Pendulum', 'Poetrist','Raven'),
        ('Test Blog', 'userTest', 'Raven');  
        
INSERT INTO post (post_author_id, post_blog_id, post_title, post_body, published)
	VALUES
		('Poetrist', 1, 'The Raven - First verse', 
			'Once upon a midnight dreary, while I pondered, weak and weary,
			Over many a quaint and curious volume of forgotten lore—
			While I nodded, nearly napping, suddenly there came a tapping,
			As of some one gently rapping, rapping at my chamber door.
			“’Tis some visitor,” I muttered, “tapping at my chamber door—
            Only this and nothing more.”', true);   
            
 INSERT INTO comment (comment_author_id, post_id, comment_text)
	VALUES
		('userTest', 1, 'Please continue!');

INSERT INTO comment(comment_author_id, post_id, comment_text, reply_id)
	VALUES
		('Poetrist', 1, 'Thank you!',1);   
        
 INSERT INTO user(username, real_name, email_address, user_password, role)
	VALUES
		('sanyi823', 'Sándor Petőfi', 'forradalmat48@freemail.hu' , 'ejjmiakő', 'moderator');
INSERT INTO blog(blog_title, blog_author_id, blog_template_id)
	VALUES
		('Pilvax snippets', 'sanyi823','Kafka');
 INSERT INTO post (post_author_id, post_blog_id, post_title, post_body, published)
	VALUES
		('Poetrist', 1, 'What a windy but sunny day!', 
		'I was about to continue my great verse, The Raven, but I need some coffee. How are you, my dear readers today?', true);  
INSERT INTO comment (comment_author_id, post_id, comment_text)
	VALUES
		('sanyi823', 2, 'Come to Pilvax! I treat you for a great coffee and maybe we can discuss our poetry.');  
        

        