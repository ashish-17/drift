import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TrendTopCalcReducer extends Reducer<TrendKey, Text, Text, Text> {

	private static int MAX_TOPPERS = 10;
	
	public void reduce(TrendKey key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		
		int count = 0;
		List<String> toppersSorted = new ArrayList<>();
		while (values.iterator().hasNext() && count < MAX_TOPPERS) {
			String[] val = values.iterator().next().toString().split(",");
			toppersSorted.add(val[0] + "," + key.trend);
			
			count++;
		}
		
		context.write(new Text(key.date), new Text(toppersSorted.toString()));
	}

}
