package edu.ru.cs512.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.ru.cs512.config.SpringMongoConfig;
import edu.ru.cs512.model.PageRank;
import edu.ru.cs512.service.PageRankService;

@Service
public class PageRankServiceImpl extends BaseServiceImpl implements PageRankService {

    @Override
    public String getCollection() {
        return SpringMongoConfig.PAGE_RANK;
    }

    @Override
    public List<PageRank> findByTitles(String[] titles) {
        Query query = new Query(Criteria.where("_id").in(titles));
        List<PageRank> result = getOpts().find(query, PageRank.class, getCollection());
        return result;
    }

    @Override
    public List<String> findTitles(String prefix) {
        String match = ".";
        if(prefix.endsWith(";")) {
            prefix = prefix.substring(0, prefix.length()-1);
            match = "$";
        }
        Pattern pattern = Pattern.compile("^" + prefix + match);
        Query query = new Query(Criteria.where("_id").regex(pattern));
        query.limit(20);
        List<PageRank> temp = getOpts().find(query, PageRank.class, getCollection());
        List<String> result = new ArrayList<String>();
        for (PageRank pr : temp) {
            result.add(pr.getPageTitle());
        }
        return result;
    }

}
