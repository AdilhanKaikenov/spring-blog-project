package com.epam.adok.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BlogCategoryID implements Serializable {

    @Column(name = "blog_id")
    private long blogID;

    @Column(name = "category_id")
    private long categoryID;

    public long getBlogID() {
        return blogID;
    }

    public void setBlogID(long blogID) {
        this.blogID = blogID;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public BlogCategoryID(long blogID, long categoryID) {
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
        int result = (int) (blogID ^ (blogID >>> 32));
        result = 31 * result + (int) (categoryID ^ (categoryID >>> 32));
        return result;
    }
}
