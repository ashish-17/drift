package edu.ru.cs512.service;

import org.springframework.data.mongodb.core.MongoOperations;

public interface BaseService {

    public MongoOperations getOpts();
    
}
