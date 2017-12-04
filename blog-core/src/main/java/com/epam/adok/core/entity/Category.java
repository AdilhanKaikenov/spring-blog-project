package com.epam.adok.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@Table(name = "category")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@XmlRootElement
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
