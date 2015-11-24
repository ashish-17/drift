package edu.ru.cs512.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ru.cs512.model.PageCount;
import edu.ru.cs512.service.PageCountService;

@Controller
@RequestMapping("/pagecount")
public class PageCountController {

    @Autowired
    PageCountService pcService;
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public String index() {
        return "pagecount";
    }
    
    @RequestMapping(value="/{title}", method=RequestMethod.GET)
    @ResponseBody
    public Object findByTitle(@PathVariable String title) {
        String[] titles = title.split(",");
        if(titles.length == 1) {
            return pcService.findByTitle(title);
        }
        else if(title.length() > 1){
            return pcService.findByTitles(titles);
        }
        return "";
    }
}
