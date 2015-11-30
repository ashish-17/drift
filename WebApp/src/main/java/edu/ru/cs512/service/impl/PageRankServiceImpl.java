package edu.ru.cs512.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.ru.cs512.model.PageRank;
import edu.ru.cs512.service.PageRankService;

@Service
public class PageRankServiceImpl extends BaseServiceImpl implements PageRankService {
    
    @Override
    public String getCollection() {
        return "page_rank";
    }

    @Override
    public List<PageRank> findTopK(int k) {
        Query query = new Query();
        query.limit(k);
        query.with(new Sort(Sort.Direction.DESC, "pagerank"));
        List<PageRank> result = getOpts().find(query, PageRank.class, getCollection());
        return result;
    }

}
