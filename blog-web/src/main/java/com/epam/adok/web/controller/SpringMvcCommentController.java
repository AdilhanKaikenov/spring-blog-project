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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SpringMvcCommentController {

    private static final Logger log = LoggerFactory.getLogger(SpringMvcCommentController.class);

    @Autowired
    private CommentService<BlogComment> blogCommentService;

    @RequestMapping(value = "/blog/comment/submit", method = RequestMethod.POST)
    public RedirectView commentSubmit(@Valid @ModelAttribute("blogCommentModel") BlogCommentModel blogCommentModel,
                                      BindingResult result,
                                      RedirectAttributes redirectAttrs) {

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/blog/" + blogCommentModel.getBlogId());
        redirectView.setContextRelative(true);

        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();
            List<String> messages = new ArrayList<>();

            for (ObjectError error : errors) {
                messages.add(error.getDefaultMessage());
            }

            redirectAttrs.addFlashAttribute("messages", messages);

            return redirectView;
        }

        BlogComment blogComment = getBlogCommentFromModel(blogCommentModel);
        blogCommentService.submitComment(blogComment);

        return redirectView;
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
