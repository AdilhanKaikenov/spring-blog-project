package com.epam.adok.core.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Blog.readById", query = "SELECT blog FROM Blog blog WHERE blog.id = :id"),
        @NamedQuery(name = "Blog.readByTitle", query = "SELECT blog FROM Blog blog WHERE blog.title = :title"),
        @NamedQuery(name = "Blog.readVOByTitle", query = "SELECT NEW com.epam.adok.core.vo.BlogTitleVO(blog.id, blog.title) FROM Blog blog WHERE blog.title = :title"),
        @NamedQuery(name = "Blog.readAll", query = "SELECT blog FROM Blog blog")
})
@Table(name = "blog")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Blog extends UniqueIdEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_category_assignment",
            joinColumns = {@JoinColumn(name = "blog_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories;

    @Column(name = "publication_date")
    private Date publicationDate;

    public Blog() {
    }

    public Blog(String title, String content, User author, Set<Category> categories, Date publicationDate) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.categories = categories;
        this.publicationDate = publicationDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Blog blog = (Blog) o;

        if (title != null ? !title.equals(blog.title) : blog.title != null) return false;
        if (content != null ? !content.equals(blog.content) : blog.content != null) return false;
        if (author != null ? !author.equals(blog.author) : blog.author != null) return false;
        if (categories != null ? !categories.equals(blog.categories) : blog.categories != null) return false;
        return publicationDate != null ? publicationDate.equals(blog.publicationDate) : blog.publicationDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        return result;
    }
}
