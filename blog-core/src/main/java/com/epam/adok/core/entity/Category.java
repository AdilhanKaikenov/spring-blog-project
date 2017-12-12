package com.epam.adok.core.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Category.readById", query = "SELECT category FROM Category category WHERE category.id = :id"),
        @NamedQuery(name = "Category.readAll", query = "SELECT category FROM Category category"),
        @NamedQuery(name = "Category.readByIdList", query = "SELECT category FROM Category category WHERE category.id = :idList")
})
@Table(name = "category")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Category extends UniqueIdEntity {

    @Column(name = "genre")
    private String genre;

    @Column(name = "added_date")
    private Date addedDate;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "genre='" + genre + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
