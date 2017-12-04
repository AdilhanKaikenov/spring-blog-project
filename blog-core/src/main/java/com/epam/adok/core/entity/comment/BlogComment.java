package com.epam.adok.core.entity.comment;

import com.epam.adok.core.entity.Blog;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("BT")
public class BlogComment extends AbstractComment {

    @OneToOne
    @JoinColumn(name="blog_id")
    private Blog blog;

    @OneToOne
    @JoinColumn(name="parent_comment_id")
    private BlogComment parentComment;

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
