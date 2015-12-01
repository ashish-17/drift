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

import edu.ru.cs512.model.PageRank;
import edu.ru.cs512.model.PageTitle;
import edu.ru.cs512.service.PageRankService;

@Controller
@RequestMapping("/pagerank")
public class PageRankController {

    @Autowired
    private PageRankService prService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "pagerank";
    }

    @RequestMapping(value = "/{titles}", method = RequestMethod.GET)
    @ResponseBody
    public List<PageRank> findByTitles(@PathVariable String titles) {
        return prService.findByTitles(titles.split(","));
    }

    @RequestMapping(value = "/titles/search", method = RequestMethod.GET)
    @ResponseBody
    public List<PageTitle> searchTitles(@RequestParam String prefix) {
        List<String> temp = prService.findTitles(prefix);
        List<PageTitle> result = new ArrayList<PageTitle>();
        if (!result.isEmpty()) {
            for (String t : temp) {
                result.add(new PageTitle(t));
            }
            return result;
        }
        prefix = prefix.toUpperCase().charAt(0) + prefix.substring(1);
        temp = prService.findTitles(prefix);
        for (String t : temp) {
            result.add(new PageTitle(t));
        }
        return result;
    }
}
