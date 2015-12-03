package edu.ru.cs512.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.ru.cs512.model.PageCount;
import edu.ru.cs512.model.PageTrend;
import edu.ru.cs512.service.PageCountService;

@Service
public class PageCountServiceImpl extends BaseServiceImpl implements PageCountService {

    @Override
    public Map<String, PageCount> findByTitles(String[] titles) {
        Map<String, PageCount> result = new HashMap<String, PageCount>();
        Query query = new Query(Criteria.where("_id").in(titles));
        List<PageCount> temp = getOpts().find(query, PageCount.class);
        for (PageCount pc : temp) {
            pc.sort();
            result.put(pc.getPageTitle(), pc);
        }
        return result;
    }

    @Override
    public Map<String, PageTrend> findTrendsByTitles(String[] titles) {
        Map<String, PageTrend> result = new HashMap<String, PageTrend>();
        Query query = new Query(Criteria.where("_id").in(titles));
        List<PageTrend> temp = getOpts().find(query, PageTrend.class);
        for (PageTrend pt : temp) {
            pt.sort();
            result.put(pt.getPageTitle(), pt);
        }
        return result;
    }

    @Override
    public List<String> findTitles(String prefix) {
        String match = ".";
        if (prefix.endsWith(";")) {
            prefix = prefix.substring(0, prefix.length() - 1);
            match = "$";
        }
        Pattern pattern = Pattern.compile("^" + prefix + match);
        Query query = new Query(Criteria.where("_id").regex(pattern));
        query.limit(20);
        List<PageCount> temp = getOpts().find(query, PageCount.class);
        List<String> result = new ArrayList<String>();
        for (PageCount pc : temp) {
            result.add(pc.getPageTitle());
        }
        return result;
    }
}