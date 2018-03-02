package com.epam.adok.core.service;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.configuration.TestApplicationContextConfiguration;
import com.epam.adok.core.entity.comment.BlogComment;
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
    public void findAllCommentsByBlogID_checkThatTheNumberOfCommentsMatches_shouldReturnTrue() {
        List<BlogComment> allBlogCommentByBlogId = this.commentService.findAllBlogCommentByBlogId(1L);
        int numberOfComments = allBlogCommentByBlogId.size();

        assertThat(numberOfComments, is(7));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_countNumberOfSubBranchesForEachBranch_shouldCorrespondToReality() {

        // when
        List<CommentBranch> commentBranches = this.commentService.buildAllCommentBranchesByBlogId(1L);

        CommentBranch commentBranch = commentBranches.get(0);

        List<CommentBranch> subComments = commentBranch.getSubComments();

        CommentBranch commentBranch1 = subComments.get(0);
        CommentBranch commentBranch2 = subComments.get(1);
        CommentBranch commentBranch3 = subComments.get(2);

        // then
        assertThat(subComments.size(), is(3));
        assertThat(commentBranch1.getSubComments().size(), is(2));
        assertThat(commentBranch2.getSubComments().size(), is(1));
        assertThat(commentBranch3.getSubComments().size(), is(0));

    }
}
