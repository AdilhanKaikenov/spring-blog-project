package com.epam.adok.core.entity.comment;

import com.epam.adok.core.entity.AbstractBaseEntity;
import com.epam.adok.core.entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "AbstractComment.readById", query = "SELECT comment FROM AbstractComment comment WHERE comment.id = :id"),
        @NamedQuery(name = "AbstractComment.readAll", query = "SELECT comment FROM AbstractComment comment")
})
@Table(name = "comment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "comment_type")
public abstract class AbstractComment extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "comment_date")
    private Date commentDate;

    public AbstractComment() {
    }

    public AbstractComment(User user, String text, Date commentDate) {
        this.user = user;
        this.text = text;
        this.commentDate = commentDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return "AbstractComment{" +
                "user=" + user +
                ", text='" + text + '\'' +
                ", commentDate=" + commentDate +
                '}';
    }
}
