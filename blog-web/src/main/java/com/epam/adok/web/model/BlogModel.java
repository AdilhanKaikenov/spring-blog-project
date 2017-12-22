package com.epam.adok.web.model;

import com.epam.adok.core.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class BlogModel {

    private long id;

    @NotNull(message = "Should not be null")
    @Size(min = 4, max = 30, message = "Should be between 4 to 30 characters")
    private String title;

    @NotNull(message = "Should not be null")
    @Size(min = 10, max = 300, message = "Should be between 10 to 300 characters")
    private String content;

    private User author;

    @NotNull(message = "Should not be null")
    @Size(min = 1, message = "You must select at least 1")
    private List<Long> categoriesIds;

    private Date publicationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Long> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(List<Long> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "BlogModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", categoriesIds=" + categoriesIds +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
