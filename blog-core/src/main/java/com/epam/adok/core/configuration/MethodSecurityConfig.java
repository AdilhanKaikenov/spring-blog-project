package com.epam.adok.core.configuration;

import com.epam.adok.core.access.CustomPermissionEvaluator;
import com.epam.adok.core.service.BlogService;
import com.epam.adok.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private BlogService blogService;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {

        CustomPermissionEvaluator customPermissionEvaluator = new CustomPermissionEvaluator(userService, blogService);

        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);

        return expressionHandler;
    }
}
