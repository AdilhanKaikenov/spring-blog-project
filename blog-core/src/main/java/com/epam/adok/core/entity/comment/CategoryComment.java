package com.epam.adok.core.entity.comment;

import com.epam.adok.core.entity.Category;
import com.epam.adok.core.entity.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@DiscriminatorValue("CT")
public class CategoryComment extends AbstractComment {

    @OneToOne
    @JoinColumn(name="category_id")
    private Category category;

    public CategoryComment() {
    }

    public CategoryComment(User user, String text, Date commentDate, Category category) {
        super(user, text, commentDate);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryComment{" +
                "category=" + category +
                '}';
    }
}
