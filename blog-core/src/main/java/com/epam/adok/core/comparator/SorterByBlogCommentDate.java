package com.epam.adok.core.comparator;

import com.epam.adok.core.commentshierarchy.CommentBranch;

import java.util.Comparator;
import java.util.Date;

public class SorterByBlogCommentDate implements Comparator<CommentBranch> {

    @Override
    public int compare(CommentBranch commentBranchOne, CommentBranch commentBranchTwo) {

        Date commentDateOne = commentBranchOne.getRootBlogComment().getCommentDate();
        Date commentDateTwo = commentBranchTwo.getRootBlogComment().getCommentDate();

        return commentDateOne.compareTo(commentDateTwo);
    }
}
