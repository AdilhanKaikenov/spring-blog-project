package com.epam.adok.core.service;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.configuration.TestApplicationContextConfiguration;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.BlogComment;
import com.google.common.collect.Multimap;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationContextConfiguration.class)
public class CommentServiceTest {

    @Autowired
    private CommentService<BlogComment> commentService;

    @Autowired
    private DataSource dataSource;

    @Before
    public void prepareTestData() {
        Operation operation = sequenceOf(
                deleteAllFrom("blog_category_assignment", "comment", "blog"),
                insertInto("blog")
                        .columns("id", "title", "content", "user_id", "publication_date")
                        .values(1L, "Blog#1", "Content Text", 1L, "2017-10-31 16:40:31")
                        .values(4L, "Blog#2", "Content Text", 1L, "2017-10-31 16:40:31")
                        .build(),
                insertInto("comment")
                        .columns("id", "blog_id", "parent_comment_id", "user_id", "text", "comment_date", "comment_type")
                        .values(1L, 1L, null, 1L, "Comment text-Comment text-Comment text-Comment text", new Date(), "BT")
                        .values(2L, 1L, 1L, 2L, "Comment text-Comment text-Comment text-Comment text", new Date(), "BT")
                        .values(3L, 1L, 1L, 2L, "Comment text-Comment text-Comment text-Comment text", new Date(), "BT")
                        .values(4L, 1L, 3L, 1L, "Comment text-Comment text-Comment text-Comment text", new Date(), "BT")
                        .values(5L, 1L, 1L, 2L, "Comment text-Comment text-Comment text-Comment text", new Date(), "BT")
                        .values(6L, 1L, 5L, 1L, "Comment text-Comment text-Comment text-Comment text", new Date(), "BT")
                        .values(7L, 1L, 5L, 1L, "Comment text-Comment text-Comment text-Comment text", new Date(), "BT")
                        .build()
        );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(this.dataSource), operation);
        dbSetup.launch();
    }

    @Test
    public void findAllCommentsByBlogID_checkThatTheNumberOfCommentsMatches_shouldCorrespondToReality() {
        // When
        List<BlogComment> allBlogCommentByBlogId = this.commentService.findAllBlogCommentByBlogId(1L);
        int numberOfComments = allBlogCommentByBlogId.size();

        // Then
        assertThat(numberOfComments, is(7));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_countNumberOfSubBranchesForEachBranch_shouldCorrespondToReality() {
        // When
        List<CommentBranch> commentBranches = this.commentService.buildAllCommentBranchesByBlogId(1L);

        CommentBranch targetCommentBranch = commentBranches.get(0);

        List<CommentBranch> subComments = targetCommentBranch.getSubComments();

        int numberSubBranches1 = subComments.get(0).getSubComments().size();
        int numberSubBranches2 = subComments.get(1).getSubComments().size();
        int numberSubBranches3 = subComments.get(2).getSubComments().size();

        List<Integer> numbersSubBranches = Arrays.asList(numberSubBranches1, numberSubBranches2, numberSubBranches3);

        // Then
        assertThat(subComments.size(), is(3));
        assertThat(numbersSubBranches.contains(0), is(true)); // comments id = 2
        assertThat(numbersSubBranches.contains(1), is(true)); // comments id = 3
        assertThat(numbersSubBranches.contains(2), is(true)); // comments id = 5

    }

    @Test
    public void buildCommentBranchTree_forSubCommentBranchWithoutSubComments_shouldReturnEmptySubCommentsList() {
        // Given
        List<BlogComment> blogComments = this.commentService.findAllBlogCommentByBlogId(1L);
        Multimap<Long, BlogComment> map = CommentService.BlogCommentsStructureBuilder.getCommentsAsHierarchyMap(blogComments);

        // When
        List<CommentBranch> commentBranches = CommentService.BlogCommentsStructureBuilder.buildCommentBranchTree(map, 2L);

        // Then
        assertThat(commentBranches.isEmpty(), is(true));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_forBlogWithoutComments_shouldReturnEmptyCommentBranchesList() {
        // When
        List<CommentBranch> commentBranches = this.commentService.buildAllCommentBranchesByBlogId(4L);

        // Then
        assertThat(commentBranches.isEmpty(), is(true));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_beforeAndAfterAddingAnAnswerForRootComment_numberOfSubBranchesShouldBeIncreased() {

        // Given - Before
        List<CommentBranch> commentBranchesBefore = this.commentService.buildAllCommentBranchesByBlogId(1L);
        CommentBranch targetCommentBranchBefore = commentBranchesBefore.get(0);

        BlogComment newComment = this.generateBlogCommentInstance(1L, 1L, 2L);

        // Before
        assertThat(targetCommentBranchBefore.getSubComments().size(), is(3));

        // When
        this.commentService.submitComment(newComment);

        // Given - After sent new comment
        List<CommentBranch> commentBranchesAfter = this.commentService.buildAllCommentBranchesByBlogId(1L);
        CommentBranch targetCommentBranchAfter = commentBranchesAfter.get(0);

        // Then
        assertThat(targetCommentBranchAfter.getSubComments().size(), is(4));
    }

    private BlogComment generateBlogCommentInstance(long blogId, long parentCommentId, long commentUserId) {
        BlogComment blogComment = new BlogComment();

        User user = new User();
        user.setId(commentUserId);
        blogComment.setUser(user);

        blogComment.setText("Comment text-Comment text-Comment text-Comment text");

        blogComment.setCommentDate(new Date());

        Blog blog = new Blog();
        blog.setId(blogId);
        blogComment.setBlog(blog);

        BlogComment parentRootComment = new BlogComment();
        parentRootComment.setId(parentCommentId);
        blogComment.setParentComment(parentRootComment);

        return blogComment;
    }
}
