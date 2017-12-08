package com.epam.adok.core.dao.impl.blog;

import com.epam.adok.core.entity.Category;
import com.epam.adok.core.util.DateRange;

import java.util.Set;

public class BlogFilter {

    private Set<Category> categories;

    private DateRange dateRange;

    public BlogFilter(Set<Category> categories, DateRange dateRange) {
        this.categories = categories;
        this.dateRange = dateRange;
    }

    public BlogFilter() {
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}
