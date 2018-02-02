package com.epam.adok.web.configuration;

import com.epam.adok.core.configuration.RootApplicationContextConfiguration;
import com.epam.adok.web.configuration.security.LoginSecurityConfig;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Nullable
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                RootApplicationContextConfiguration.class,
                LoginSecurityConfig.class
        };
    }

    @Nullable
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
               WebApplicationContextConfiguration.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
