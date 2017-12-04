package com.epam.adok.core.entity.comment;

import com.epam.adok.core.entity.Category;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("CT")
public class CategoryComment extends AbstractComment {

    @OneToOne
    @JoinColumn(name="category_id")
    private Category category;

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
