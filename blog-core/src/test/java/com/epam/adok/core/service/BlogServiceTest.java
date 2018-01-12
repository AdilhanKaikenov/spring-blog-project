package com.epam.adok.core.service;


import com.epam.adok.core.configuration.TestApplicationContextConfiguration;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Category;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.exception.BlogNotFoundException;
import com.epam.adok.core.repository.BlogCommentRepository;
import com.epam.adok.core.repository.BlogRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationContextConfiguration.class)
public class BlogServiceTest {

    private static final Logger log = LoggerFactory.getLogger(BlogServiceTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @Test
    public void removeBlogByID_withExistingBlog_shouldDeleteBlog() throws BlogNotFoundException {

        // Given
        Blog blog = this.getBlog();

        Blog savedBlog = this.blogRepository.saveAndFlush(blog);

        BlogComment blogComment = new BlogComment(
                blog.getAuthor(), "Blog Comment text", new Date(), savedBlog, null);

        BlogComment submittedBlogComment = blogCommentRepository.saveAndFlush(blogComment);

        // When
        long savedBlogId = savedBlog.getId();
        this.blogService.removeBlogByID(savedBlogId);

        // Then
        long submittedBlogCommentId = submittedBlogComment.getId();
        Optional<Blog> targetBlog = blogRepository.findById(savedBlogId);
        Optional<BlogComment> targetComment = blogCommentRepository.findById(submittedBlogCommentId);

        assertThat(targetBlog.isPresent(), is(false));
        assertThat(targetComment.isPresent(), is(false));

    }

    @Test
    public void removeBlogByID_withExistingBlogAndWithoutComments_shouldDeleteBlog() throws BlogNotFoundException {

        // Given
        Blog blog = this.getBlog();

        Blog savedBlog = this.blogRepository.saveAndFlush(blog);

        // When
        long savedBlogId = savedBlog.getId();
        this.blogService.removeBlogByID(savedBlogId);

        // Then
        Optional<Blog> targetBlog = blogRepository.findById(savedBlogId);

        assertThat(targetBlog.isPresent(), is(false));

    }

    @Test
    public void removeBlogByID_WithNonExistingBlog_ShouldThrowException() throws BlogNotFoundException {
        thrown.expect(BlogNotFoundException.class);
        this.blogService.removeBlogByID(70L);
    }

    private Blog getBlog() {
        User user = new User();
        user.setId(1);

        Category category = new Category();
        category.setId(2);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        return new Blog(
                "Title", "Content Text", user, categories, new Date());
    }
}
