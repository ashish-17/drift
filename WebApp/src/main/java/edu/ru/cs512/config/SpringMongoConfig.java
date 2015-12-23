package edu.ru.cs512.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class SpringMongoConfig {

    public final static String DB = "mongo_hadoop";
    public final static String URI = "mongodb://127.0.0.1:27017";

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClient(new MongoClientURI(URI)), DB);
        return mongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}