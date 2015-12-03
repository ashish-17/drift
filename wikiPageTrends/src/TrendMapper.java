import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TrendMapper extends Mapper<LongWritable, Text, TrendKey, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer lineTokenizer = new StringTokenizer(value.toString(), "\n\r\f");
		while (lineTokenizer.hasMoreTokens()) {
			String token = lineTokenizer.nextToken();
			
			String data[] = token.split("\t");
			if (data.length == 2) {
				String[] trendKeyStr = data[0].split(",");
				String[] trendValStr = data[1].split(",");
				TrendKey trendKey = new TrendKey(trendKeyStr[0], Double.parseDouble(trendKeyStr[1]));
				context.write(trendKey, new Text(trendValStr[0] + "," + trendValStr[1] + "," + trendValStr[2]));
			}
		}
	}

}
