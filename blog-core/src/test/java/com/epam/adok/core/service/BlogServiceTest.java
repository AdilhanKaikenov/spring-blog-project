package com.epam.adok.core.service;


import com.epam.adok.core.configuration.RootApplicationContextConfiguration;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.repository.BlogRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfiguration.class)
public class BlogServiceTest {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;


    public void removeBlogByID_WithExistingBlog_ShouldDeleteBlog() {
        // Given
        Blog blog = new Blog();
        this.blogRepository.saveAndFlush(blog);

        // When
//        boolean result = this.blogService.removeBlogByID(0L);

        // Then
    }

    public void removeBlogByID_WithNonExistingBlog_ShouldThrowException() {
        // Given

        // When
//        boolean result = this.blogService.removeBlogByID(-125L);

        // Then

    }
}
