package edu.ru.cs512.service;

import java.util.List;
import java.util.Map;

import edu.ru.cs512.model.PageCount;

public interface PageCountService extends BaseService {
    
    public PageCount findByTitle(String title);
    
    public Map<String, PageCount> findByTitles(String[] titles);
    
    public List<String> listTitles(String prefix);

}
