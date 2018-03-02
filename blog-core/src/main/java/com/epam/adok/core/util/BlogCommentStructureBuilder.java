package com.epam.adok.core.util;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.entity.comment.BlogComment;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlogCommentStructureBuilder {

    public static List<CommentBranch> buildTree(Multimap<Long, BlogComment> map, Long parentId) {

        List<CommentBranch> commentsBranches = new ArrayList<>();

        Collection<BlogComment> blogComments = map.get(parentId);

        if (blogComments.size() > 0) {
            for (BlogComment blogComment : blogComments) {
                CommentBranch commentBranch = new CommentBranch();
                commentBranch.setRootBlogComment(blogComment);
                List<CommentBranch> commentBranches = buildTree(map, blogComment.getId());
                commentBranch.setSubComments(commentBranches);

                commentsBranches.add(commentBranch);
            }
        }
        return commentsBranches;
    }


    public static Multimap<Long, BlogComment> getCommentsAsHierarchyMap(List<BlogComment> blogComments) {

        Multimap<Long, BlogComment> resultMap = HashMultimap.create();

        for (BlogComment blogComment : blogComments) {
            if (blogComment.getParentComment() == null) {
                blogComment.setParentComment(new BlogComment());
            }
            resultMap.put(blogComment.getParentComment().getId(), blogComment);
        }
        return resultMap;
    }

}
