package edu.ru.cs512.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.ru.cs512.config.SpringMongoConfig;
import edu.ru.cs512.model.PageCount;
import edu.ru.cs512.service.PageCountService;

@Service
public class PageCountServiceImpl extends BaseServiceImpl implements PageCountService {
    
    public String getCollection() {
        return SpringMongoConfig.PAGE_VIEW;
    }

    @Override
    public Map<String, PageCount> findByTitles(String[] titles) {
        Map<String, PageCount> result = new HashMap<String, PageCount>();
        Query query = new Query(Criteria.where("_id").in(titles));
        List<PageCount> temp = getOpts().find(query, PageCount.class, getCollection());
        for(PageCount pc: temp) {
            result.put(pc.getPageTitle(), pc);
        }
        return result;
    }

    @Override
    public List<String> findTitles(String prefix) {
        Pattern pattern = Pattern.compile("^" + prefix + ".");
        Query query = new Query(Criteria.where("_id").regex(pattern));
        query.limit(20);
        List<PageCount> temp = getOpts().find(query, PageCount.class, getCollection());
        List<String> result = new ArrayList<String>();
        for (PageCount pc : temp) {
            result.add(pc.getPageTitle());
        }
        return result;
    }
}