import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.hadoop.io.BSONWritable;

public class DataCleanReducer extends Reducer<PageDataKey, PageDataValue, BSONWritable, BSONWritable> {

	public void reduce(PageDataKey key, Iterable<PageDataValue> values, Context context)
			throws IOException, InterruptedException {
		
		int totalPageViews = 0;
		while (values.iterator().hasNext()) {
			totalPageViews += values.iterator().next().countViews;
		}
		
		new BasicDBObjectBuilder();
		BSONObject bo = BasicDBObjectBuilder.start()
						.add("pageTitle", key.pageTitle)
						.get();
		

		BSONObject boVal = BasicDBObjectBuilder.start()
						.add("date", key.date)
						.add("viewCount", totalPageViews)
						.get();
		
		context.write(new BSONWritable(bo), new BSONWritable(boVal));
	}
}
