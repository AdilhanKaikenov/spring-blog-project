package com.epam.adok.core.service;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.comparator.SorterByBlogCommentDate;
import com.epam.adok.core.entity.comment.BlogComment;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

class BlogCommentsStructureBuilder {

    static List<CommentBranch> buildCommentBranchTree(Multimap<Long, BlogComment> map) {
        return buildCommentBranchTree(map, 0L); // 0L - root level id
    }

    static List<CommentBranch> buildCommentBranchTree(Multimap<Long, BlogComment> map, long parentId) {
        List<CommentBranch> commentsBranches = new ArrayList<>();

        Collection<BlogComment> blogComments = map.get(parentId);

        if (!blogComments.isEmpty()) {
            for (BlogComment blogComment : blogComments) {
                CommentBranch commentBranch = new CommentBranch();
                commentBranch.setRootBlogComment(blogComment);
                List<CommentBranch> commentBranches = buildCommentBranchTree(map, blogComment.getId());
                commentBranch.setSubComments(commentBranches);

                commentsBranches.add(commentBranch);
            }
        }
        return commentsBranches;
    }

    static Multimap<Long, BlogComment> getCommentsAsHierarchyMap(List<BlogComment> blogComments) {
        Multimap<Long, BlogComment> resultMap = LinkedHashMultimap.create();

        for (BlogComment blogComment : blogComments) {
            if (blogComment.getParentComment() == null) {
                blogComment.setParentComment(new BlogComment());
            }
            resultMap.put(blogComment.getParentComment().getId(), blogComment);
        }
        return resultMap;
    }

    static List<CommentBranch> buildLimitedTripleNestingCommentsBranchesTree(
            int limit,
            List<BlogComment> zeroLevelComments,
            List<BlogComment> firstLevelComments,
            List<BlogComment> secondLevelComments) {

        Queue<BlogComment> blogCommentQueue = new LinkedList<>();

        if (zeroLevelComments != null && firstLevelComments != null && secondLevelComments != null) {

            for (int i = zeroLevelComments.size(); --i >= 0; ) {

                for (BlogComment secondLevelComment : secondLevelComments) {
                    if (secondLevelComment.getParentComment().getParentComment().getId() == zeroLevelComments.get(i).getId()) {
                        handleAddingCommentToTheQueueWithLimitCheck(blogCommentQueue, limit, secondLevelComment);
                    }
                }

                for (BlogComment firstLevelComment : firstLevelComments) {
                    if (firstLevelComment.getParentComment().getId() == zeroLevelComments.get(i).getId()) {
                        handleAddingCommentToTheQueueWithLimitCheck(blogCommentQueue, limit, firstLevelComment);
                    }
                }
                handleAddingCommentToTheQueueWithLimitCheck(blogCommentQueue, limit, zeroLevelComments.get(i));
            }
        }
        List<BlogComment> blogComments = new ArrayList<>(blogCommentQueue);

        blogComments.sort(new SorterByBlogCommentDate());

        Multimap<Long, BlogComment> commentsAsHierarchyMap = getCommentsAsHierarchyMap(blogComments);
        return buildCommentBranchTree(commentsAsHierarchyMap);
    }

    private static void handleAddingCommentToTheQueueWithLimitCheck(Queue<BlogComment> blogCommentQueue, int limit, BlogComment secondLevelComment) {
        if (blogCommentQueue.size() >= limit) {
            blogCommentQueue.remove();
            blogCommentQueue.add(secondLevelComment);
        } else {
            blogCommentQueue.add(secondLevelComment);
        }
    }
}

