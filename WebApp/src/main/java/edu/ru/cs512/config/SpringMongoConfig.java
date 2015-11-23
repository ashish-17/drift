package edu.ru.cs512.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig {

    public final static String db = "test";

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClient(), db);
        return mongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}