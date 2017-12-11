package com.epam.adok.core.util;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DateRange {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date to;

    public DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public DateRange() {
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
}
