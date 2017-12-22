package com.epam.adok.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BlogCommentModel {

    private long blogId;

    private long userId;

    @NotNull(message = "Should not be null")
    @Size(min = 30, max = 300, message = "Should be between 30 to 300 characters")
    private String text;

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "BlogCommentModel{" +
                "blogId=" + blogId +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                '}';
    }
}
