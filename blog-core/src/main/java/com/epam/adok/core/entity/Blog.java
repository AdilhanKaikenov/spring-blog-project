package com.epam.adok.core.entity;

import com.epam.adok.core.entity.comment.BlogComment;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Blog.readById", query = "SELECT blog FROM Blog blog WHERE blog.id = :id"),
        @NamedQuery(name = "Blog.readAll", query = "SELECT blog FROM Blog blog")
})
@Table(name = "blog")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Blog extends UniqueIdEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_category_assignment",
            joinColumns = {@JoinColumn(name = "blog_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories = new HashSet<>();

    @Column(name = "publication_date")
    private Date publicationDate;

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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", categories=" + categories +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
