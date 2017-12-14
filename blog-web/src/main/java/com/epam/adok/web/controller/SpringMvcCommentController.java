package com.epam.adok.web.controller;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.service.CommentService;
import com.epam.adok.web.model.BlogCommentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class SpringMvcCommentController {

    private static final Logger log = LoggerFactory.getLogger(SpringMvcCommentController.class);

    @Autowired
    private CommentService<BlogComment> blogCommentService;

    @RequestMapping(value = "/blog/comment/submit", method = RequestMethod.POST)
    public ModelAndView commentSubmit(@ModelAttribute("blogCommentModel") BlogCommentModel blogCommentModel,
                                      ModelAndView modelAndView) {

        BlogComment blogComment = getBlogCommentFromModel(blogCommentModel);
        blogCommentService.submitComment(blogComment);

        modelAndView.setViewName("redirect:/blog/" + blogCommentModel.getBlogId());

        return modelAndView;
    }

    private BlogComment getBlogCommentFromModel(BlogCommentModel blogCommentModel) {
        BlogComment blogComment = new BlogComment();
        Blog blog = new Blog();
        blog.setId(blogCommentModel.getBlogId());
        blogComment.setBlog(blog);

        User user = new User();
        user.setId(1); // TODO : User
        blogComment.setUser(user);

        blogComment.setText(blogCommentModel.getText());
        blogComment.setCommentDate(new Date());
        return blogComment;
    }
}
