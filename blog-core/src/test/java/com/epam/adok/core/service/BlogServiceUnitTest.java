package com.epam.adok.core.service;

import com.epam.adok.core.dao.BlogDao;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.exception.BlogNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.NoResultException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlogServiceUnitTest {

    @InjectMocks // annotation is used to create and inject the mock object
    private BlogService blogService = new BlogService();

    @Mock
    private CommentService<BlogComment> blogCommentService;

    @Mock // annotation is used to create the mock object to be injected
    private BlogDao blogDao;

    @Captor // ArgumentCaptor class is used to capture argument values for further assertions.
    private ArgumentCaptor<Blog> argCaptor;

    @Test
    public void removeBlogByID_withExistingBlogWithoutComments_shouldDeleteBlog() throws BlogNotFoundException {
        // initialize -> set expectations -> exercise -> verify

        // Given
        long blogId = 1;
        Blog blog = new Blog();
        blog.setId(blogId);
        blog.setTitle("Title");

        when(blogDao.read(blogId)).thenReturn(blog);
        when(blogCommentService.countAllBlogCommentByBlogId(blogId)).thenReturn(0L);

        // When
        long commentNumber = this.blogService.removeBlogByID(blogId);

        // Then
        verify(blogDao).read(blogId);
        verify(blogCommentService).removeAllBlogCommentsByBlogId(blogId);

        verify(blogDao).delete(argCaptor.capture());
        assertEquals(blog, argCaptor.getValue());

    }

    @Test(expected = BlogNotFoundException.class)
    public void removeBlogByID_withNonexistentBlog_shouldThrowException() throws BlogNotFoundException {
        long blogId = 0; // wrong blog Id
        Blog blog = new Blog();
        blog.setId(blogId);

        when(blogDao.read(blogId)).thenThrow(new NoResultException());
        // doThrow variant can be used for methods which return void to throw an exception -> this case does not fit
        // when() method can be used to throw an exception.
            this.blogService.removeBlogByID(blogId);
    }
}
