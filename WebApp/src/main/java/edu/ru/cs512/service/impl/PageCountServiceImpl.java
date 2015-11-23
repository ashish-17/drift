package edu.ru.cs512.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.ru.cs512.config.SpringMongoConfig;
import edu.ru.cs512.model.PageCount;
import edu.ru.cs512.service.PageCountService;

@Service
public class PageCountServiceImpl implements PageCountService {
    
    private MongoOperations getOpts() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations opts = (MongoOperations) ctx.getBean("mongoTemplate");
        return opts;
    }
    
    @Override
    public List<PageCount> findByTitle(String title) {
        MongoOperations ops = getOpts();
        Query query = new Query(Criteria.where("pageTitle").is(title));
        List<PageCount> pcList = ops.find(query, PageCount.class, "page_count");
        return pcList;
    }
}
