package com.epam.adok.core.access;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.service.BlogService;
import com.epam.adok.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    private UserService userService;
    private BlogService blogService;

    public CustomPermissionEvaluator(UserService userService, BlogService blogService) {
        this.userService = userService;
        this.blogService = blogService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("Entering hasPermission() method. ");
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        log.info("Input arguments : hasPermission() ---> targetDomainObject = {}, permission = {}.", targetDomainObject, permission);
        User currentUser = this.userService.getCurrentUser();
        Blog blogByID = this.blogService.findBlogByID((Long)targetDomainObject);

        User blogAuthor = blogByID.getAuthor();

        switch (permission.toString()) {
            case "permissionToRemove":
                return blogAuthor.equals(currentUser);
            default:
                return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
