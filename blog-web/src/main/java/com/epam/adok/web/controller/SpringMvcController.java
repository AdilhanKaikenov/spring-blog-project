package com.epam.adok.web.controller;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.service.BlogService;
import com.epam.adok.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import java.util.List;

@Controller
public class SpringMvcController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blog/", method = RequestMethod.GET)
    public ModelAndView blogs() {

        List<Blog> blogs = blogService.findAllBlogs();

        ModelAndView model = new ModelAndView();
        model.addObject("blogs", blogs);
        model.setViewName("blogs");

        return model;
    }

    @RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
    public ModelAndView blog(@PathVariable("id") int id) {

        Blog blog = blogService.findBlogByID(id);

        if (blog == null) {
            throw new NotFoundException();
        }

        ModelAndView model = new ModelAndView();
        model.addObject("blog", blog);
        model.setViewName("blog");

        return model;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The result with this ID was not found.")
    @ExceptionHandler({NoResultException.class, NotFoundException.class})
    public void handleNotFound() {
    }
}
