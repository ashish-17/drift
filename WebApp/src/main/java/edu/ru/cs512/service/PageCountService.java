package edu.ru.cs512.service;

import java.util.List;

import edu.ru.cs512.model.PageCount;

public interface PageCountService {
    
    public List<PageCount> findByTitle(String title);

}
