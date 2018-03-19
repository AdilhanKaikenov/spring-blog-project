package com.epam.adok.core.service;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.configuration.TestApplicationContextConfiguration;
import com.epam.adok.core.dao.CommentDao;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.BlogComment;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.*;

import static com.epam.adok.core.service.BlogCommentsStructureBuilder.buildCommentBranchTree;
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

    @Test
    public void findAllCommentsByBlogID_ExistingBlogWithSeveralComments_ShouldReturnAllCommentsOfSpecificBlog() {
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);

        // When
        List<BlogComment> allBlogCommentByBlogId = this.commentService.findAllBlogCommentByBlogId(1L);
        int numberOfComments = allBlogCommentByBlogId.size();

        // Then
        assertThat(numberOfComments, is(44));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_ExistingBlogWithSeveralLevelsOfNestingCommentBranches_ShouldReturnCommentsTree() {
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);

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
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);

        // When
        List<CommentBranch> commentBranches = this.commentService.buildAllCommentBranchesByBlogId(4L);

        // Then
        assertThat(commentBranches.isEmpty(), is(true));
    }

    @Test
    public void buildAllCommentBranchesByBlogId_BlogCommentsBranchesWithSeveralSubBranches_ShouldSubmitNewComment() {

        // Given - before
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);

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
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);

        // When
        List<CommentBranch> tripleNestingCommentsBranches = this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 1, 20);

        int totalAmount;

        Map<Integer, Integer> subCommentsNumberForEachLevelOfNesting = this.countBranchSubCommentsNumberForEachLevelOfNesting(tripleNestingCommentsBranches);

        int rootSize = subCommentsNumberForEachLevelOfNesting.get(0);
        int firstSize = subCommentsNumberForEachLevelOfNesting.get(1);
        int secondSize = subCommentsNumberForEachLevelOfNesting.get(2);
        int thirdSize = subCommentsNumberForEachLevelOfNesting.get(3);

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
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);
        //When
        this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildLimitedCommentsBranchesUpToTripleNesting_TwoRootBranchesWithFourLevelsOfNestingAndPageEqualZero_ShouldThrowException() {
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);
        // When
        this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildLimitedCommentsBranchesUpToTripleNesting_BlogIdentifierEqualZero_ShouldThrowException() {
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);
        // When
        this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(0L, 1, 10);
    }

    @Test
    public void buildLimitedCommentsBranchesUpToTripleNesting_NonExistentBlog_ShouldReturnEmptyCommentBranchesList() {
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);

        // When
        List<CommentBranch> commentBranches = this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1000L, 1, 10);

        // Then
        assertThat(commentBranches.size(), is(0));
    }

    @Test
    public void buildLimitedCommentsBranchesUpToTripleNesting_BlogWithRootCommentBranchUpToOneLevelOfNesting_ShouldReturnSingleNestingCommentsTreeEqualOrLessTheSpecifiedNumberOfComments() {
        // Given
        GivenTestDataPreparer.prepareBlogWithRootCommentBranchUpToOneLevelOfNesting(this.dataSource);

        // When
        List<CommentBranch> commentBranches = this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 1, 10);

        Map<Integer, Integer> subCommentsNumberForEachLevelOfNesting = this.countBranchSubCommentsNumberForEachLevelOfNesting(commentBranches);

        int firstSize = subCommentsNumberForEachLevelOfNesting.get(1);
        int secondSize = subCommentsNumberForEachLevelOfNesting.get(2);

        CommentBranch commentBranch = commentBranches.get(0);

        List<CommentBranch> firstLevelSubComments = commentBranch.getSubComments();

       // Then
        assertThat(commentBranches.size(), is(1));
        assertThat(firstSize, is(4));
        assertThat(secondSize, is(0));
        assertThat(firstLevelSubComments.get(0).getRootBlogComment().getId(), is(5L));
        assertThat(firstLevelSubComments.get(1).getRootBlogComment().getId(), is(4L));
        assertThat(firstLevelSubComments.get(2).getRootBlogComment().getId(), is(3L));
        assertThat(firstLevelSubComments.get(3).getRootBlogComment().getId(), is(2L));
    }

    @Test
    public void buildLimitedCommentsBranchesUpToTripleNesting_BlogWithRootCommentBranchUpToOneDoubleNesting_ShouldReturnDoubleNestingCommentsTreeEqualOrLessTheSpecifiedNumberOfComments() {
        // Given
        GivenTestDataPreparer.prepareBlogWithRootCommentBranchUpToOneDoubleNesting(this.dataSource);

        // When
        List<CommentBranch> commentBranches = this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(1L, 1, 10);

        Map<Integer, Integer> subCommentsNumberForEachLevelOfNesting = this.countBranchSubCommentsNumberForEachLevelOfNesting(commentBranches);

        int firstSize = subCommentsNumberForEachLevelOfNesting.get(1);
        int secondSize = subCommentsNumberForEachLevelOfNesting.get(2);
        int thirdSize = subCommentsNumberForEachLevelOfNesting.get(3);

        CommentBranch commentBranch = commentBranches.get(0);

        List<CommentBranch> firstLevelSubComments = commentBranch.getSubComments();

        // Then
        assertThat(commentBranches.size(), is(1));
        assertThat(firstSize, is(2));
        assertThat(secondSize, is(2));
        assertThat(thirdSize, is(0));

        CommentBranch firstLevelBranch1 = firstLevelSubComments.get(0);
        CommentBranch firstLevelBranch2 = firstLevelSubComments.get(1);

        assertThat(firstLevelBranch1.getRootBlogComment().getId(), is(3L));
        assertThat(firstLevelBranch2.getRootBlogComment().getId(), is(2L));

        List<CommentBranch> secondLevelSubComments1 = firstLevelBranch1.getSubComments();
        List<CommentBranch> secondLevelSubComments2 = firstLevelBranch2.getSubComments();

        assertThat(secondLevelSubComments1.get(0).getRootBlogComment().getId(), is(5L));
        assertThat(secondLevelSubComments2.get(0).getRootBlogComment().getId(), is(4L));
    }

    @Test
    public void buildLimitedCommentsBranchesUpToTripleNesting_BlogWithoutComments_ShouldReturnEmptyCommentBranchesList() {
        // Given
        GivenTestDataPreparer.prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(this.dataSource);

        // When
        List<CommentBranch> commentBranches = this.commentService.buildLimitedCommentsBranchesUpToTripleNesting(4L, 1, 10);

        // Then
        assertThat(commentBranches.size(), is(0));
    }

    private Map<Integer, Integer> countBranchSubCommentsNumberForEachLevelOfNesting(List<CommentBranch> commentBranches) {

        Map<Integer, Integer> result = new HashMap<>();

        int rootSize = 0;
        int firstSize = 0;
        int secondSize = 0;
        int thirdSize = 0;

        if (commentBranches != null) {
            rootSize = commentBranches.size();
            for (CommentBranch rootLevelSubComment : commentBranches) {
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

        result.put(0, rootSize);
        result.put(1, firstSize);
        result.put(2, secondSize);
        result.put(3, thirdSize);

        return result;
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
