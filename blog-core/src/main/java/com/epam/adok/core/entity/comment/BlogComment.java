package com.epam.adok.core.entity.comment;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "BlogComment.readAllByBlogId", query = "SELECT comment FROM BlogComment comment WHERE comment.blog.id = :id"),
        @NamedQuery(name = "BlogComment.deleteAllBlogId", query = "DELETE FROM BlogComment comment WHERE comment.blog.id = :id")
})
@DiscriminatorValue("BT")
public class BlogComment extends AbstractComment {

    @OneToOne
    @JoinColumn(name="blog_id")
    private Blog blog;

    @OneToOne
    @JoinColumn(name="parent_comment_id")
    private BlogComment parentComment;

    public BlogComment() {
    }

    public BlogComment(User user, String text, Date commentDate, Blog blog, BlogComment parentComment) {
        super(user, text, commentDate);
        this.blog = blog;
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public BlogComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(BlogComment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "BlogComment{" +
                "blog=" + blog +
                ", parentComment=" + parentComment +
                '}';
    }
}
