import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.hadoop.io.MongoUpdateWritable;

public class DataCleanReducer extends Reducer<PageDataKey, PageDataValue, NullWritable, MongoUpdateWritable> {

    private MongoUpdateWritable reduceResult = new MongoUpdateWritable();
    
	public void reduce(PageDataKey key, Iterable<PageDataValue> values, Context context)
			throws IOException, InterruptedException {
		
		int totalPageViews = 0;
		while (values.iterator().hasNext()) {
			totalPageViews += values.iterator().next().countViews;
		}
		
		BasicBSONObject query=new BasicBSONObject();
		query.append("_id", key.pageTitle);
		
		BSONObject boVal = BasicDBObjectBuilder.start()
							.add("date", key.date)
							.add("viewCount", totalPageViews)
							.get();
		
		BasicBSONObject pageTitleData = new BasicBSONObject(key.date.substring(0,  6), boVal);
		BasicBSONObject pageTitleDataUpdate = new BasicBSONObject("$push", pageTitleData);
		
		reduceResult.setQuery(query);
		reduceResult.setModifiers(pageTitleDataUpdate);
		reduceResult.setUpsert(true);
		context.write(null, reduceResult);
	}
}