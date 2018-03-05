package com.epam.adok.core.dao.impl.blog;

import com.epam.adok.core.entity.Category;
import com.epam.adok.core.util.DateRange;

import java.util.List;

public class BlogFilter {

    private List<Category> categories;

    private DateRange dateRange;

    public BlogFilter(List<Category> categories, DateRange dateRange) {
        this.categories = categories;
        this.dateRange = dateRange;
    }

    public BlogFilter() {
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public DateRange getDateRange() {
        return this.dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}
