package edu.ru.cs512.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ru.cs512.model.PageTitle;
import edu.ru.cs512.service.PageCountService;

@Controller
@RequestMapping("/pagecount")
public class PageCountController {

    @Autowired
    private PageCountService pcService;
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "pagecount";
    }
    
    @RequestMapping(value="/{titles}", method=RequestMethod.GET)
    @ResponseBody
    public Object findByTitle(@PathVariable String titles) {
        return pcService.findByTitles(titles.split(",,"));
    }
    
    @RequestMapping(value="/titles/search", method=RequestMethod.GET)
    @ResponseBody
    public List<PageTitle> findTitles(@RequestParam(required=false) String prefix) {
        List<PageTitle> result = new ArrayList<PageTitle>();
        if(prefix == null || prefix.isEmpty()) {
            return result;
        }
        List<String> temp = pcService.findTitles(prefix);
        if (!result.isEmpty()) {
            for (String t : temp) {
                result.add(new PageTitle(t));
            }
            return result;
        }
        prefix = prefix.toUpperCase().charAt(0) + prefix.substring(1);
        temp = pcService.findTitles(prefix);
        for (String t : temp) {
            result.add(new PageTitle(t));
        }
        return result;
    }
}
