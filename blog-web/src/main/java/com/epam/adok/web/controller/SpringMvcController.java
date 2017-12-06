package com.epam.adok.web.controller;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMvcController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/message/{source}", method = RequestMethod.GET)
    public ModelAndView message(@PathVariable("source") String source) {

        Blog blog = blogService.findBlogByID(1);

        ModelAndView model = new ModelAndView();
        model.addObject("message", blog.getTitle());
        model.setViewName("message");

        return model;
    }
}
