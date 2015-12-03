package edu.ru.cs512.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import edu.ru.cs512.config.SpringMongoConfig;
import edu.ru.cs512.service.BaseService;

public class BaseServiceImpl implements BaseService {
    
    @Override
    public MongoOperations getOpts() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations opts = (MongoOperations) ctx.getBean("mongoTemplate");
        return opts;
    }
    
}
