package edu.ru.cs512.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.ru.cs512.service.PageViewService;

@Controller
@RequestMapping("/pageview")
public class PageViewController {

    @Autowired
    PageViewService dataService;
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public String index() {
        return "pageview";
    }
}
