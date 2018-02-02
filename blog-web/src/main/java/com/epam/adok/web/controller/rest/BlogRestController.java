package com.epam.adok.web.controller.rest;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.exception.BlogNotFoundException;
import com.epam.adok.core.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BlogRestController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blog", method = RequestMethod.POST)
    public ResponseEntity<Blog> create(@RequestBody Blog blog) {

        if (blog.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        blogService.createBlog(blog);

        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
    public ResponseEntity<Blog> read(@PathVariable("id") long id) {
        Blog blog = blogService.findBlogByID(id);
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @RequestMapping(value = "/blog", method = RequestMethod.PUT)
    public ResponseEntity<Blog> update(@RequestBody Blog blog) {
        Blog targetBlog = blogService.findBlogByID(blog.getId());

        if (targetBlog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        blogService.createBlog(blog);

        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @RequestMapping(value = "/blog/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Blog> delete(@PathVariable("id") long id) throws BlogNotFoundException {
        Blog blog = blogService.findBlogByID(id);

        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        blogService.removeBlogByID(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
