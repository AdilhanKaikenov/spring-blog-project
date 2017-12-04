package com.epam.adok.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BlogCategoryID implements Serializable {

    @Column(name = "blog_id")
    private int blogID;

    @Column(name = "category_id")
    private int categoryID;

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public BlogCategoryID(int blogID, int categoryID) {
        this.blogID = blogID;
        this.categoryID = categoryID;
    }

    public BlogCategoryID() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogCategoryID that = (BlogCategoryID) o;

        if (blogID != that.blogID) return false;
        return categoryID == that.categoryID;
    }

    @Override
    public int hashCode() {
        int result = blogID;
        result = 31 * result + categoryID;
        return result;
    }
}
