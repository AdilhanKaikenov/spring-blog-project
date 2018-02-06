package com.epam.adok.core.access;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.service.BlogService;
import com.epam.adok.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("blogMethodSecurity")
public class BlogMethodSecurity {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    public boolean hasRemovePermission(long blogId) {
        Blog blogByID = this.blogService.findBlogByID(blogId);
        User currentUser = this.userService.getCurrentUser();

        User blogAuthor = blogByID.getAuthor();

        return blogAuthor.equals(currentUser);
    }
}
