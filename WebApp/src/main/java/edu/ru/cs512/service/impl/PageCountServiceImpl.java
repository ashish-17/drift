package edu.ru.cs512.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import edu.ru.cs512.config.SpringMongoConfig;
import edu.ru.cs512.model.PageCount;
import edu.ru.cs512.service.PageCountService;

@Service
public class PageCountServiceImpl extends BaseServiceImpl implements PageCountService {
    
    public String getCollection() {
        return SpringMongoConfig.PAGE_VIEW;
    }
    
	private static final int MIN_PREFIX_LENGTH = 3;

    @Override
    public PageCount findByTitle(String title) {
        Query query = new Query(Criteria.where("_id").is(title));
        List<PageCount> result = getOpts().find(query, PageCount.class, getCollection());
        
        if (!result.isEmpty()) {
        	return result.get(0);
        } else {
        	return null;
        }
    }

    @Override
    public Map<String, PageCount> findByTitles(String[] titles) {
        Map<String, PageCount> result = new HashMap<String, PageCount>();
        for(String title: titles) {
            result.put(title, findByTitle(title));
        }
        return result;
    }

    @Override
    public List<String> listTitles(String prefix) {
    	List<String> titles = new ArrayList<>();
    	
    	if (prefix.length() >= MIN_PREFIX_LENGTH) {
    		Pattern p = Pattern.compile("^"+prefix+"."/*, java.util.regex.Pattern.CASE_INSENSITIVE*/);
        	BasicDBObject query = new BasicDBObject("_id", p);

        	List<DBObject> result = getOpts().getCollection(getCollection()).find(query).limit(10).toArray();
        	for(DBObject obj : result) {
        		titles.add((String)(obj.get("_id")));
        	}
        	
    	}
    	
        return titles;
    }
}