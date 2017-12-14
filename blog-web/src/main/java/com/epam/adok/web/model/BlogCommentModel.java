package com.epam.adok.web.model;

public class BlogCommentModel {

    private long blogId;
    private long userId;
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
