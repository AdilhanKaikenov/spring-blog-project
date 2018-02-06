package com.epam.adok.web.controller;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.service.CommentService;
import com.epam.adok.core.service.UserService;
import com.epam.adok.web.model.BlogCommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService<BlogComment> blogCommentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/blog/{blogId}/comment", method = RequestMethod.POST)

    public RedirectView commentSubmit(@PathVariable("blogId") long blogId,
                                      @Valid @ModelAttribute("blogCommentModel") BlogCommentModel blogCommentModel,
                                      BindingResult result,
                                      RedirectAttributes redirectAttrs) {

        String url = "/blog/" + blogId;
        RedirectView redirectView = new RedirectView(url, true, false);

        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();
            List<String> messages = new ArrayList<>();

            for (ObjectError error : errors) {
                messages.add(error.getDefaultMessage());
            }

            redirectAttrs.addFlashAttribute("messages", messages);
            redirectAttrs.addFlashAttribute("text", blogCommentModel.getText());

            return redirectView;
        }

        BlogComment blogComment = getBlogCommentFromModel(blogCommentModel, blogId);
        blogCommentService.submitComment(blogComment);

        return redirectView;
    }

    private BlogComment getBlogCommentFromModel(BlogCommentModel blogCommentModel, long blogId) {
        BlogComment blogComment = new BlogComment();
        Blog blog = new Blog();
        blog.setId(blogId);
        blogComment.setBlog(blog);

        User user = new User();
        user.setId(userService.getCurrentUser().getId());
        blogComment.setUser(user);

        blogComment.setText(blogCommentModel.getText());
        blogComment.setCommentDate(new Date());
        return blogComment;
    }
}
