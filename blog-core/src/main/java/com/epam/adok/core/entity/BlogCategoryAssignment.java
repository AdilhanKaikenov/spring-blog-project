package com.epam.adok.core.entity;

import com.epam.adok.core.util.interfaces.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blog_category_assignment")
public class BlogCategoryAssignment implements Serializable, Identifiable<BlogCategoryID> {

    @EmbeddedId
    private BlogCategoryID pk;

    @MapsId("blogID")
    @JoinColumn(name = "blog_id")
    @ManyToOne
    private Blog blog;

    @MapsId("categoryID")
    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @Column(name = "date")
    private Date date;

    public BlogCategoryID getPk() {
        return this.pk;
    }

    public void setPk(final BlogCategoryID pk) {
        this.pk = pk;
    }

    @Override
    @Transient
    public BlogCategoryID getId() {
        return this.pk;
    }

    @Override
    @Transient
    public void setId(final BlogCategoryID id) {
        this.pk = id;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogCategoryAssignment that = (BlogCategoryAssignment) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;
        if (blog != null ? !blog.equals(that.blog) : that.blog != null) return false;
        return category != null ? category.equals(that.category) : that.category == null;
    }

    @Override
    public int hashCode() {
        int result = pk != null ? pk.hashCode() : 0;
        result = 31 * result + (blog != null ? blog.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
