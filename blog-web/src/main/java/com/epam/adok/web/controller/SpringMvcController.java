package com.epam.adok.web.controller;

import com.epam.adok.core.dao.impl.blog.BlogFilter;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Category;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.exception.DateParsingException;
import com.epam.adok.core.service.BlogService;
import com.epam.adok.core.service.CategoryService;
import com.epam.adok.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class SpringMvcController {

    private static final Logger log = LoggerFactory.getLogger(SpringMvcController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public ModelAndView blogs(ModelAndView modelAndView) {

        List<Blog> blogs = blogService.findAllBlogs();

        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("filter", new BlogFilter());
        modelAndView.setViewName("blogs");

        return modelAndView;
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
    public ModelAndView filter(@ModelAttribute("filter") BlogFilter filter,
                               ModelAndView modelAndView,
                               @Nullable @RequestParam("categoryIds") String[] categoryIds) throws DateParsingException {

        modelAndView.addObject("filter", filter);

        List<Category> allCategoriesByIdList = getCategoriesByIds(categoryIds);

        filter.setCategories(allCategoriesByIdList);
        List<Blog> blogs = blogService.findAllBlogsByParameters(filter);

        modelAndView.addObject("blogs", blogs);

        modelAndView.setViewName("blogs");

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView getBlogCreatePage(ModelAndView modelAndView) {
        modelAndView.addObject("newBlog", new Blog());
        modelAndView.setViewName("create-new-blog");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("newBlog") Blog blog,
            @RequestParam("categoryIds") String[] categoryIds,
            ModelAndView modelAndView) {

        List<Category> categories = getCategoriesByIds(categoryIds);

        for (Category category : categories) {
            blog.getCategories().add(category);
        }

        User user = new User(); // TODO : User
        user.setId(1); // User ID : 1

        blog.setAuthor(user);

        blogService.createBlog(blog);

        modelAndView.setViewName("redirect:/blog");

        return modelAndView;
    }

    @ModelAttribute("categoryList")
    public List<Category> getCategoryList() {
        return categoryService.findAllCategories();
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The result with this ID was not found.")
    @ExceptionHandler({NoResultException.class, NotFoundException.class})
    public void handleNotFound() {
    }

    private List<Category> getCategoriesByIds(String[] categoryIds) {
        List<Integer> categoryIdList = new ArrayList<>();
        List<Category> allCategoriesByIdList = null;
        if (categoryIds != null) {
            for (String categoryId : categoryIds) {
                categoryIdList.add(Integer.parseInt(categoryId));
            }
            allCategoriesByIdList = categoryService.findAllCategoriesByIdList(categoryIdList);
        }
        return allCategoriesByIdList;
    }
}
