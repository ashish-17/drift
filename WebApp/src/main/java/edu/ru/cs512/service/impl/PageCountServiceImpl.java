package edu.ru.cs512.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.ru.cs512.model.PageCount;
import edu.ru.cs512.service.PageCountService;

@Service
public class PageCountServiceImpl extends BaseServiceImpl implements PageCountService {
    
    @Override
    public List<PageCount> findByTitle(String title) {
        Query query = new Query(Criteria.where("pageTitle").is(title));
        List<PageCount> result = getOpts().find(query, PageCount.class, getCollection());
        return result;
    }

    @Override
    public Map<String, List<PageCount>> findByTitles(String[] titles) {
        Map<String, List<PageCount>> result = new HashMap<String, List<PageCount>>();
        for(String title: titles) {
            result.put(title, findByTitle(title));
        }
        return result;
    }

    @Override
    public List<String> listTitles() {
        return getOpts().getCollection(getCollection()).distinct("pageTitle");
    }
    
    
}