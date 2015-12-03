import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

public class DataCleanCombiner extends Reducer<PageDataKey, PageDataValue, PageDataKey, PageDataValue> {

	public void reduce(PageDataKey key, Iterable<PageDataValue> values, Context context)
			throws IOException, InterruptedException {
		PageDataValue pdv = new PageDataValue();
		while (values.iterator().hasNext()) {
			PageDataValue tmp = values.iterator().next();
			if (pdv.date == null) {
				pdv.date = tmp.date;
			}
			pdv.countViews += tmp.countViews;
		}
		
		context.write(key, pdv);
	}
}
