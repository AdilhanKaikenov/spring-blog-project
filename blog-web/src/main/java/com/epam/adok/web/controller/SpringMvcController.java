package com.epam.adok.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMvcController {

    @RequestMapping(value = "/message/{source}", method = RequestMethod.GET)
    public ModelAndView message(@PathVariable("source") String source) {

        ModelAndView model = new ModelAndView();
        model.addObject("message", source);
        model.setViewName("message");

        return model;
    }
}
