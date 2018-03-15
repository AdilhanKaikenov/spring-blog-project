package com.epam.adok.core.comparator;

import com.epam.adok.core.entity.comment.BlogComment;

import java.util.Comparator;
import java.util.Date;

public class SorterByBlogCommentDate implements Comparator<BlogComment> {

    @Override
    public int compare(BlogComment blogCommentOne, BlogComment blogCommentTwo) {

        Date commentDateOne = blogCommentOne.getCommentDate();
        Date commentDateTwo = blogCommentTwo.getCommentDate();

        return commentDateTwo.compareTo(commentDateOne);
    }
}
