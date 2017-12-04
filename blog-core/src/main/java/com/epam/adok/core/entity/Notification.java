package com.epam.adok.core.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
@NamedQueries({
        @NamedQuery(name = "Notification.removeByCreatedOnBefore", query = "DELETE FROM Notification n WHERE n.date < :expiryDate"),
        @NamedQuery(name = "Notification.readAll", query = "SELECT notification FROM Notification notification")
})
public class Notification extends AbstractBaseEntity {

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Column(name = "date")
    private Date date;

    public Notification(User user, Blog blog, Date date) {
        this.user = user;
        this.blog = blog;
        this.date = date;
    }

    public Notification() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

