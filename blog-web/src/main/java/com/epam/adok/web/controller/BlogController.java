package com.epam.adok.web.controller;

import com.epam.adok.core.dao.impl.blog.BlogFilter;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Category;
import com.epam.adok.core.entity.Role;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.exception.BlogNotFoundException;
import com.epam.adok.core.service.BlogService;
import com.epam.adok.core.service.CategoryService;
import com.epam.adok.core.service.CommentService;
import com.epam.adok.core.service.UserService;
import com.epam.adok.core.util.DateRange;
import com.epam.adok.web.exception.NotFoundException;
import com.epam.adok.web.model.BlogCommentModel;
import com.epam.adok.web.model.BlogFilterModel;
import com.epam.adok.web.model.BlogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    private static final int ONE = 1;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService<BlogComment> blogCommentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public ModelAndView blogs(ModelAndView modelAndView) {

        List<Blog> blogs = this.blogService.findAllBlogs();

        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("blogFilterModel", new BlogFilterModel());
        modelAndView.setViewName("blogs");

        return modelAndView;
    }

    @RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
    public ModelAndView blog(@PathVariable("id") long id,
                             Model model) {

        Blog blog = this.blogService.findBlogByID(id);

        List<BlogComment> allBlogComments = this.blogCommentService.findAllBlogCommentByBlogId(id);

        if (blog == null) {
            throw new NotFoundException();
        }

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("blog", blog);
        modelAndView.addObject("blogComments", allBlogComments);

        BlogCommentModel blogCommentModel = new BlogCommentModel();

        Map<String, Object> objectMap = model.asMap();
        String commentText = (String) objectMap.get("text");

        if (commentText != null) {
            blogCommentModel.setText(commentText);
        }

        modelAndView.addObject("blogCommentModel", blogCommentModel);

        modelAndView.setViewName("blog");

        return modelAndView;
    }

    @RequestMapping(value = "/blog/filter", method = RequestMethod.POST)
    public ModelAndView filter(@ModelAttribute("blogFilterModel") BlogFilterModel blogFilterModel,
                               ModelAndView modelAndView) {

        BlogFilter blogFilter = getBlogFilterFromModel(blogFilterModel);
        List<Blog> blogs = this.blogService.findAllBlogsByParameters(blogFilter);
        modelAndView.addObject("blogs", blogs);

        modelAndView.setViewName("blogs");

        return modelAndView;
    }

    @RequestMapping(value = "/blog/create", method = RequestMethod.GET)
    public ModelAndView getBlogCreatePage(ModelAndView modelAndView) {
        modelAndView.addObject("blogModelCreate", new BlogModel());
        modelAndView.setViewName("create-new-blog");
        return modelAndView;
    }

    @RequestMapping(value = "/blog/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("blogModelCreate") BlogModel blogModel,
                               BindingResult result,
                               ModelAndView modelAndView) {

        if (result.hasErrors()) {
            modelAndView.addAllObjects(result.getModel());
            modelAndView.setViewName("create-new-blog");
            return modelAndView;
        }

        Blog blog = getBlogFromModel(blogModel);
        this.blogService.createBlog(blog);

        modelAndView.setViewName("redirect:/blog");

        return modelAndView;
    }

    @RequestMapping(value = "/blog/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") String sourceId,
                               ModelAndView modelAndView) throws BlogNotFoundException {

        User currentUser = this.userService.getCurrentUser();

        if (currentUser != null) {

            String currentUserLogin = currentUser.getLogin();
            List<Role> roles = currentUser.getRoles();

            log.info("Current User Login : {}", currentUserLogin);

            log.info("User ROLES : ");
            for (Role role : roles) {
                log.info(" ----> {}", role.getName());
            }
        }

        long id = Long.parseLong(sourceId);
        this.blogService.removeBlogByID(id);

        modelAndView.setViewName("redirect:/blog");

        return modelAndView;
    }

    @RequestMapping(value = "/blog/{id}/edit", method = RequestMethod.GET)
    public ModelAndView getBlogEditPage(@PathVariable("id") String sourceId,
                                        ModelAndView modelAndView) {

        long id = Long.parseLong(sourceId);

        Blog blog = this.blogService.findBlogByID(id);
        BlogModel blogModel = getBlogModelFromBlog(blog);

        modelAndView.addObject("blogModelEdit", blogModel);
        modelAndView.setViewName("edit");

        return modelAndView;
    }

    @RequestMapping(value = "/blog/edit", method = RequestMethod.POST)
    public ModelAndView edit(@Valid @ModelAttribute("blogModelEdit") BlogModel blogModel,
                             BindingResult result,
                             ModelAndView modelAndView) {

        if (result.getAllErrors().size() > ONE) { // ONE - always a title field (should be ignored)
            modelAndView.addAllObjects(result.getModel());
            modelAndView.setViewName("edit");
            return modelAndView;
        }

        Blog blog = getBlogFromModel(blogModel);
        this.blogService.updateBlog(blog);

        modelAndView.setViewName("redirect:/blog/" + blog.getId());

        return modelAndView;
    }

    @ModelAttribute("categoryList")
    public List<Category> getCategoryList() {
        return this.categoryService.findAllCategories();
    }

    @ExceptionHandler({NoResultException.class, NotFoundException.class})
    public ModelAndView handleNotFound(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", req.getRequestURI());
        modelAndView.setViewName("no-result-error-page");
        return modelAndView;
    }

    @ExceptionHandler({NumberFormatException.class})
    public ModelAndView handleNumberFormatException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/blog");
        return modelAndView;
    }

    private List<Category> getCategoriesByIds(List<Long> categoryIds) {
        List<Category> allCategoriesByIdList = null;
        if (!categoryIds.isEmpty()) {
            allCategoriesByIdList = this.categoryService.findAllCategoriesByIdList(categoryIds);
        }
        return allCategoriesByIdList;
    }

    private BlogModel getBlogModelFromBlog(Blog blog) {
        BlogModel blogModel = new BlogModel();
        blogModel.setId(blog.getId());
        blogModel.setTitle(blog.getTitle());
        blogModel.setAuthor(blog.getAuthor());
        blogModel.setContent(blog.getContent());
        return blogModel;
    }

    private BlogFilter getBlogFilterFromModel(BlogFilterModel blogFilterModel) {
        BlogFilter blogFilter = new BlogFilter();

        List<Category> allCategoriesByIdList = getCategoriesByIds(blogFilterModel.getCategoriesIds());
        blogFilter.setCategories(allCategoriesByIdList);

        DateRange dateRange = new DateRange();
        dateRange.setFrom(blogFilterModel.getFrom());
        dateRange.setTo(blogFilterModel.getTo());

        blogFilter.setDateRange(dateRange);
        return blogFilter;
    }

    private Blog getBlogFromModel(BlogModel blogModel) {
        Blog blog = new Blog();
        blog.setId(blogModel.getId());
        List<Category> categories = getCategoriesByIds(blogModel.getCategoriesIds());
        blog.setCategories(new HashSet<>(categories));

        User user = new User();
        user.setId(this.userService.getCurrentUser().getId());
        blog.setAuthor(user);

        blog.setTitle(blogModel.getTitle());
        blog.setContent(blogModel.getContent());
        return blog;
    }
}
