package com.epam.adok.web.controller;

import com.epam.adok.core.dao.impl.blog.BlogFilter;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Category;
import com.epam.adok.core.exception.DateParsingException;
import com.epam.adok.core.service.BlogService;
import com.epam.adok.core.service.CategoryService;
import com.epam.adok.core.util.DateRange;
import com.epam.adok.core.util.DateUtil;
import com.epam.adok.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class SpringMvcController {

    private static final Logger log = LoggerFactory.getLogger(SpringMvcController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/blog/", method = RequestMethod.GET)
    public ModelAndView blogs() {

        List<Blog> blogs = blogService.findAllBlogs();

        List<Category> categories = categoryService.findAllCategories();

        ModelAndView model = new ModelAndView();
        model.addObject("blogs", blogs);
        model.addObject("categories", categories);
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

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ModelAndView filter(HttpServletRequest request) throws DateParsingException {
        ModelAndView model = new ModelAndView();
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String[] categoryLists = request.getParameterValues("categoryList");

        Date dateFrom = null;
        if (!from.equals("")) {
            dateFrom = DateUtil.parseStringToDate(from);
        }

        Date dateTo = null;
        if (!to.equals("")) {
            dateTo = DateUtil.parseStringToDate(to);
        }

        Set<Category> categories = new HashSet<>();

        if (categoryLists != null) {
            for (String categoryID : categoryLists) {
                categories.add(categoryService.findCategoryByID(Integer.parseInt(categoryID)));
            }
        }

        BlogFilter blogFilter = new BlogFilter();
        blogFilter.setDateRange(new DateRange(dateFrom, dateTo));
        blogFilter.setCategories(categories);

        List<Blog> blogs = blogService.findAllBlogsByParameters(blogFilter);
        List<Category> allCategories = categoryService.findAllCategories();

        model.addObject("blogs", blogs);
        model.addObject("categories", allCategories);

        model.setViewName("blogs");

        return model;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The result with this ID was not found.")
    @ExceptionHandler({NoResultException.class, NotFoundException.class})
    public void handleNotFound() {
    }
}
