package com.epam.adok.web.validator;

import com.epam.adok.core.service.BlogService;
import com.epam.adok.web.annotation.UniqueBlogTitle;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueBlogTitleValidator implements ConstraintValidator<UniqueBlogTitle, String> {

    @Autowired
    private BlogService blogService;

    @Override
    public void initialize(UniqueBlogTitle constraintAnnotation) {

    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        return blogService.findBlogByTitle(title) == null;
    }
}
