package com.epam.adok.core.util;

import com.epam.adok.core.exception.DateParsingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static SimpleDateFormat simpleDateFormat;

    public static String parseDateToString(Date date, Locale locale) {
        simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, locale);
        return simpleDateFormat.format(date);
    }

    public static Date parseStringToDate(String source) throws DateParsingException {
        return parseStringToDate(source, DATE_PATTERN);
    }

    public static Date parseStringToDate(String source, String pattern) throws DateParsingException {
        Date date;
        simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new DateParsingException(e);
        }
        return date;
    }
}
