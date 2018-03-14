package com.epam.adok.core.util;

public class PaginationUtil {

    public static int getPagesNumber(int totalLineNumber, int linePerPageNumber) {
        int pageCount = totalLineNumber / linePerPageNumber;
        int residue = totalLineNumber % linePerPageNumber;
        if (residue > 0) {
            pageCount++;
        }
        return pageCount;
    }

}
