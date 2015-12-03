package edu.ru.cs512.service;

import java.util.List;
import java.util.Map;

import edu.ru.cs512.model.PageCount;
import edu.ru.cs512.model.PageTrend;

public interface PageCountService extends BaseService {
    
    public Map<String, PageCount> findByTitles(String[] titles);
    
    public Map<String, PageTrend> findTrendsByTitles(String[] titles);

    public List<String> findTitles(String prefix);

}
