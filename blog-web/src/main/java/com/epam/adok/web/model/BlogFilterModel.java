package com.epam.adok.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class BlogFilterModel {

    private List<Long> categoriesIds;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date to;

    public List<Long> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(List<Long> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "BlogFilterModel{" +
                "categoriesIds=" + categoriesIds +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
