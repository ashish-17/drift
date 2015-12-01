package edu.ru.cs512.service;

import java.util.List;

import edu.ru.cs512.model.PageRank;

public interface PageRankService extends BaseService {
    
    public List<PageRank> findByTitles(String[] titles);

    public List<String> findTitles(String prefix);
}
