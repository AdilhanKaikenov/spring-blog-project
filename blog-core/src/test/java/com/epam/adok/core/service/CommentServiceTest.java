package com.epam.adok.core.service;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.configuration.TestApplicationContextConfiguration;
import com.epam.adok.core.dao.CommentDao;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.repository.BlogCommentRepository;
import com.google.common.collect.HashMultimap;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.epam.adok.core.service.BlogCommentsStructureBuilder.buildCommentBranchTree;
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

    @Autowired
    private CommentDao<BlogComment> commentDao;

    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @Before
    public void prepareTestData() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Operation operation = sequenceOf(
                deleteAllFrom("blog_category_assignment", "comment", "blog"),
                insertInto("blog")
                        .columns("id", "title", "content", "user_id", "publication_date")
                        .values(1L, "Blog#1", "Content Text", 1L, "2017-10-31 16:40:31")
                        .values(4L, "Blog#2", "Content Text", 1L, "2017-10-31 16:40:31")
                        .build(),
                insertInto("comment")
                        .columns("id", "blog_id", "parent_comment_id", "user_id", "text", "comment_date", "comment_type")
                        .values(1L, 1L, null, 1L, "BlogComment Text id = 1", formatter.parse("2017-10-31 13:00:00"), "BT")
                        .values(2L, 1L, 1L, 2L, "BlogComment Text id = 2", formatter.parse("2017-10-31 13:30:00"), "BT")
                        .values(3L, 1L, 1L, 2L, "BlogComment Text id = 3", formatter.parse("2017-10-31 13:40:00"), "BT")
                        .values(4L, 1L, 3L, 1L, "BlogComment Text id = 4", formatter.parse("2017-10-31 13:45:00"), "BT")
                        .values(5L, 1L, 1L, 2L, "BlogComment Text id = 5", formatter.parse("2017-10-31 13:50:00"), "BT")
                        .values(6L, 1L, 5L, 1L, "BlogComment Text id = 6", formatter.parse("2017-10-31 14:00:00"), "BT")
                        .values(7L, 1L, 5L, 1L, "BlogComment Text id = 7", formatter.parse("2017-10-31 14:15:00"), "BT")
                        .values(8L, 1L, 5L, 1L, "BlogComment Text id = 8", formatter.parse("2017-10-31 14:20:00"), "BT")
                        .values(9L, 1L, 5L, 1L, "BlogComment Text id = 9", formatter.parse("2017-10-31 14:40:00"), "BT")
                        .values(10L, 1L, 3L, 1L, "BlogComment Text id = 10", formatter.parse("2017-10-31 14:55:00"), "BT")
                        .values(11L, 1L, 2L, 2L, "BlogComment Text id = 11", formatter.parse("2017-10-31 15:10:00"), "BT")
                        .values(12L, 1L, 1L, 1L, "BlogComment Text id = 12", formatter.parse("2017-10-31 15:40:00"), "BT")
                        .values(13L, 1L, 5L, 2L, "BlogComment Text id = 13", formatter.parse("2017-10-31 16:40:00"), "BT")
                        .values(14L, 1L, 6L, 1L, "BlogComment Text id = 14", formatter.parse("2017-10-31 16:50:00"), "BT")
                        .values(15L, 1L, 7L, 2L, "BlogComment Text id = 15", formatter.parse("2017-10-31 17:40:00"), "BT")
                        .values(16L, 1L, 3L, 1L, "BlogComment Text id = 16", formatter.parse("2017-10-31 17:50:00"), "BT")
                        .values(17L, 1L, 2L, 2L, "BlogComment Text id = 17", formatter.parse("2017-10-31 18:20:00"), "BT")
                        .values(18L, 1L, 3L, 1L, "BlogComment Text id = 18", formatter.parse("2017-10-31 18:25:00"), "BT")
                        .values(19L, 1L, 4L, 2L, "BlogComment Text id = 19", formatter.parse("2017-10-31 19:00:00"), "BT")
                        .values(20L, 1L, 6L, 1L, "BlogComment Text id = 20", formatter.parse("2017-10-31 19:20:00"), "BT")
                        .values(21L, 1L, 1L, 2L, "BlogComment Text id = 21", formatter.parse("2017-10-31 19:30:00"), "BT")
                        .values(22L, 1L, 2L, 1L, "BlogComment Text id = 22", formatter.parse("2017-10-31 19:40:00"), "BT")
                        .values(23L, 1L, null, 1L, "BlogComment Text id = 23", formatter.parse("2017-10-31 13:20:00"), "BT")
                        .values(24L, 1L, 23L, 2L, "BlogComment Text id = 24", formatter.parse("2017-10-31 13:35:00"), "BT")
                        .values(25L, 1L, 24L, 2L, "BlogComment Text id = 25", formatter.parse("2017-10-31 19:45:00"), "BT")
                        .values(26L, 1L, 24L, 1L, "BlogComment Text id = 26", formatter.parse("2017-10-31 19:45:00"), "BT")
                        .values(27L, 1L, 23L, 2L, "BlogComment Text id = 27", formatter.parse("2017-10-31 13:55:00"), "BT")
                        .values(28L, 1L, 26L, 1L, "BlogComment Text id = 28", formatter.parse("2017-10-31 14:30:00"), "BT")
                        .values(29L, 1L, 27L, 1L, "BlogComment Text id = 29", formatter.parse("2017-10-31 19:20:00"), "BT")
                        .values(30L, 1L, 29L, 1L, "BlogComment Text id = 30", formatter.parse("2017-10-31 14:20:50"), "BT")
                        .values(31L, 1L, 28L, 1L, "BlogComment Text id = 31", formatter.parse("2017-10-31 14:40:40"), "BT")
                        .values(32L, 1L, 26L, 1L, "BlogComment Text id = 32", formatter.parse("2017-10-31 14:55:20"), "BT")
                        .values(33L, 1L, 29L, 2L, "BlogComment Text id = 33", formatter.parse("2017-10-31 15:10:30"), "BT")
                        .values(34L, 1L, 30L, 1L, "BlogComment Text id = 34", formatter.parse("2017-10-31 15:40:50"), "BT")
                        .values(35L, 1L, 31L, 2L, "BlogComment Text id = 35", formatter.parse("2017-10-31 16:40:30"), "BT")
                        .values(36L, 1L, 33L, 1L, "BlogComment Text id = 36", formatter.parse("2017-10-31 16:50:20"), "BT")
                        .values(37L, 1L, 33L, 2L, "BlogComment Text id = 37", formatter.parse("2017-10-31 17:40:30"), "BT")
                        .values(38L, 1L, 25L, 1L, "BlogComment Text id = 38", formatter.parse("2017-10-31 17:50:40"), "BT")
                        .values(39L, 1L, 26L, 2L, "BlogComment Text id = 39", formatter.parse("2017-10-31 18:20:50"), "BT")
                        .values(40L, 1L, 32L, 1L, "BlogComment Text id = 40", formatter.parse("2017-10-31 18:25:30"), "BT")
                        .values(41L, 1L, 33L, 2L, "BlogComment Text id = 41", formatter.parse("2017-10-31 19:00:50"), "BT")
                        .values(42L, 1L, 31L, 1L, "BlogComment Text id = 42", formatter.parse("2017-10-31 19:20:30"), "BT")
                        .values(43L, 1L, 34L, 2L, "BlogComment Text id = 43", formatter.parse("2017-10-31 19:30:50"), "BT")
                        .values(44L, 1L, 37L, 1L, "BlogComment Text id = 44", formatter.parse("2017-10-31 19:45:20"), "BT")
                        .build()
        );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(this.dataSource), operation);
        dbSetup.launch();
    }

    @Test
    public void findAllCommentsByBlogID_ExistingBlogWithSeveralComments_ShouldReturnAllCommentsOfSpecificBlog() {
        // When
        List<BlogComment> allBlogCommentByBlogId = this.commentService.findAllBlogCommentByBlogId(1L);
        int numberOfComments = allBlogCommentByBlogId.size();

        // Then
        assertThat(numberOfComments, is(44));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_ExistingBlogWithSeveralLevelsOfNestingCommentBranches_ShouldReturnCommentsTree() {
        // When
        List<CommentBranch> commentBranches = this.commentService.buildAllCommentBranchesByBlogId(1L);

        CommentBranch targetCommentBranch = commentBranches.get(0);

        List<CommentBranch> subComments = targetCommentBranch.getSubComments();

        int numberSubBranches1 = subComments.get(0).getSubComments().size();
        int numberSubBranches2 = subComments.get(1).getSubComments().size();
        int numberSubBranches3 = subComments.get(2).getSubComments().size();
        int numberSubBranches4 = subComments.get(3).getSubComments().size();
        int numberSubBranches5 = subComments.get(4).getSubComments().size();

        List<Integer> numbersSubBranches = Arrays.asList(
                numberSubBranches1, numberSubBranches2, numberSubBranches3, numberSubBranches4, numberSubBranches5);

        // Then
        assertThat(subComments.size(), is(5));
        assertThat(numbersSubBranches.contains(3), is(true)); // comments id = 2
        assertThat(numbersSubBranches.contains(4), is(true)); // comments id = 3
        assertThat(numbersSubBranches.contains(0), is(true)); // comments id = 12
        assertThat(numbersSubBranches.contains(5), is(true)); // comments id = 5
        assertThat(numbersSubBranches.contains(0), is(true)); // comments id = 21

    }

    @Test
    public void buildCommentBranchTree_BranchOfRootCommentWithoutAnySubBranches_ShouldReturnEmptySubCommentsList() {
        // Given
        BlogComment rootBlogComment = this.generateBlogCommentInstance(1L, 0, 1L);
        BlogComment subBlogComment = this.generateBlogCommentInstance(2L, 1L, 2L);

        Multimap<Long, BlogComment> map = HashMultimap.create();

        map.put(rootBlogComment.getParentComment().getId(), rootBlogComment);
        map.put(subBlogComment.getParentComment().getId(), subBlogComment);

        // When
        List<CommentBranch> commentBranches = buildCommentBranchTree(map, 2L);

        // Then
        assertThat(commentBranches.isEmpty(), is(true));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_ExistingBlogWithoutComments_ShouldReturnEmptyCommentBranchesList() {
        // When
        List<CommentBranch> commentBranches = this.commentService.buildAllCommentBranchesByBlogId(4L);

        // Then
        assertThat(commentBranches.isEmpty(), is(true));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_BlogCommentsBranchesWithSeveralSubBranches_ShouldSubmitNewComment() {

        // Given - before
        List<BlogComment> blogCommentsBefore = this.commentDao.readAllByBlogId(1L);
        BlogComment newComment = this.generateBlogCommentInstance(1L, 1L, 2L);

        // When
        this.commentService.submitComment(newComment);

        // Given - after
        List<BlogComment> blogCommentsAfter = this.commentDao.readAllByBlogId(1L);

        // Then
        assertThat(blogCommentsBefore.size(), is(44));
        assertThat(blogCommentsAfter.size(), is(45));
    }


    @Test
    public void buildLimitedCommentsBranchesUpToTripleNesting_TwoRootBranchesWithFourLevelsOfNesting_ShouldReturnTripleNestingCommentsTreeWithTheSpecifiedNumberOfComments() {

        // When
        List<CommentBranch> tripleNestingCommentsBranches = this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 1, 20);

        int totalAmount;
        int rootSize = 0;
        int firstSize = 0;
        int secondSize = 0;
        int thirdSize = 0;

        if (tripleNestingCommentsBranches != null) {
            rootSize = tripleNestingCommentsBranches.size();
            for (CommentBranch rootLevelSubComment : tripleNestingCommentsBranches) {
                List<CommentBranch> firstLevelSubComments = rootLevelSubComment.getSubComments();
                firstSize += firstLevelSubComments.size();
                for (CommentBranch firstLevelSubComment : firstLevelSubComments) {
                    List<CommentBranch> secondLevel = firstLevelSubComment.getSubComments();
                    secondSize += secondLevel.size();
                    for (CommentBranch secondLevelSubComment : secondLevel) {
                        List<CommentBranch> thirdLevel = secondLevelSubComment.getSubComments();
                        thirdSize += thirdLevel.size();
                    }
                }
            }
        }

        totalAmount = rootSize + firstSize + secondSize;

        // Then
        assertThat(totalAmount == 20, is(true));
        assertThat(thirdSize == 0, is(true));

        CommentBranch rootLevelComment1 = tripleNestingCommentsBranches.get(0);
        CommentBranch rootLevelComment2 = tripleNestingCommentsBranches.get(1);

        List<CommentBranch> rootLevelSubComments1 = rootLevelComment1.getSubComments();
        List<CommentBranch> rootLevelSubComments2 = rootLevelComment2.getSubComments();

        assertThat(tripleNestingCommentsBranches.size(), is(2));
        assertThat(rootLevelComment1.getRootBlogComment().getId(), is(23L));
        assertThat(rootLevelComment2.getRootBlogComment().getId(), is(1L));

        CommentBranch firstLevelBranch1 = rootLevelSubComments1.get(0);
        CommentBranch firstLevelBranch2 = rootLevelSubComments1.get(1);

        assertThat(rootLevelSubComments1.size(), is(2));
        assertThat(firstLevelBranch1.getRootBlogComment().getId(), is(27L));
        assertThat(firstLevelBranch2.getRootBlogComment().getId(), is(24L));

        List<CommentBranch> secondLevelSubComments1 = firstLevelBranch1.getSubComments();
        List<CommentBranch> secondLevelSubComments2 = firstLevelBranch2.getSubComments();

        assertThat(secondLevelSubComments1.size(), is(1));
        assertThat(secondLevelSubComments2.size(), is(2));
        assertThat(secondLevelSubComments1.get(0).getRootBlogComment().getId(), is(29L));
        assertThat(secondLevelSubComments2.get(0).getRootBlogComment().getId(), is(25L));
        assertThat(secondLevelSubComments2.get(1).getRootBlogComment().getId(), is(26L));

        CommentBranch firstLevelBranch3 = rootLevelSubComments2.get(0);
        CommentBranch firstLevelBranch4 = rootLevelSubComments2.get(1);
        CommentBranch firstLevelBranch5 = rootLevelSubComments2.get(2);
        CommentBranch firstLevelBranch6 = rootLevelSubComments2.get(3);
        CommentBranch firstLevelBranch7 = rootLevelSubComments2.get(4);

        assertThat(rootLevelSubComments2.size(), is(5));
        assertThat(firstLevelBranch3.getRootBlogComment().getId(), is(21L));
        assertThat(firstLevelBranch4.getRootBlogComment().getId(), is(12L));
        assertThat(firstLevelBranch5.getRootBlogComment().getId(), is(5L));
        assertThat(firstLevelBranch6.getRootBlogComment().getId(), is(3L));
        assertThat(firstLevelBranch7.getRootBlogComment().getId(), is(2L));

        List<CommentBranch> secondLevelSubComments3 = firstLevelBranch3.getSubComments();
        List<CommentBranch> secondLevelSubComments4 = firstLevelBranch4.getSubComments();
        List<CommentBranch> secondLevelSubComments5 = firstLevelBranch5.getSubComments();
        List<CommentBranch> secondLevelSubComments6 = firstLevelBranch6.getSubComments();
        List<CommentBranch> secondLevelSubComments7 = firstLevelBranch7.getSubComments();

        assertThat(secondLevelSubComments3.size(), is(0));
        assertThat(secondLevelSubComments4.size(), is(0));
        assertThat(secondLevelSubComments5.size(), is(5));
        assertThat(secondLevelSubComments6.size(), is(2));
        assertThat(secondLevelSubComments7.size(), is(1));

        assertThat(secondLevelSubComments5.get(0).getRootBlogComment().getId(), is(13L));
        assertThat(secondLevelSubComments5.get(1).getRootBlogComment().getId(), is(9L));
        assertThat(secondLevelSubComments5.get(2).getRootBlogComment().getId(), is(8L));
        assertThat(secondLevelSubComments5.get(3).getRootBlogComment().getId(), is(7L));
        assertThat(secondLevelSubComments5.get(4).getRootBlogComment().getId(), is(6L));
        assertThat(secondLevelSubComments6.get(0).getRootBlogComment().getId(), is(10L));
        assertThat(secondLevelSubComments6.get(1).getRootBlogComment().getId(), is(4L));
        assertThat(secondLevelSubComments7.get(0).getRootBlogComment().getId(), is(11L));

    }

    @Test(expected = IllegalArgumentException.class)
    public void buildLimitedCommentsBranchesUpToTripleNesting_TwoRootBranchesWithFourLevelsOfNestingAndLinePerPageEqualZero_ShouldThrowException() {
        this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildLimitedCommentsBranchesUpToTripleNesting_TwoRootBranchesWithFourLevelsOfNestingAndPageEqualZero_ShouldThrowException() {
        this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildLimitedCommentsBranchesUpToTripleNesting_BlogIdentifierEqualZero_ShouldThrowException() {
        this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(0L, 1, 10);
    }

    @Test
    public void buildLimitedCommentsBranchesUpToTripleNesting_NonExistentBlog_ShouldReturnEmptyCommentBranchesList() {
        // When
        List<CommentBranch> commentBranches = this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1000L, 1, 10);

        // Then
        assertThat(commentBranches.size(), is(0));
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
