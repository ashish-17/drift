import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

public class DataCleanCombiner extends Reducer<PageDataKey, PageDataValue, PageDataKey, PageDataValue> {

	public void reduce(PageDataKey key, Iterable<PageDataValue> values, Context context)
			throws IOException, InterruptedException {
		int totalPageViews = 0;
		while (values.iterator().hasNext()) {
			totalPageViews += values.iterator().next().countViews;
		}
		
		PageDataValue pdv = new PageDataValue();
		pdv.countViews = totalPageViews;
		pdv.nthHour = 0;
		context.write(key, pdv);
	}

}
