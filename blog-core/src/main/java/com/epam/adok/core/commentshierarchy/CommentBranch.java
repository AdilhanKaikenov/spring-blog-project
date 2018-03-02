package com.epam.adok.core.commentshierarchy;

import com.epam.adok.core.entity.comment.BlogComment;

import java.util.List;

public class CommentBranch {

    private BlogComment rootBlogComment;
    private List<CommentBranch> subComments;


    public BlogComment getRootBlogComment() {
        return rootBlogComment;
    }

    public void setRootBlogComment(BlogComment rootBlogComment) {
        this.rootBlogComment = rootBlogComment;
    }

    public List<CommentBranch> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<CommentBranch> subComments) {
        this.subComments = subComments;
    }
}
