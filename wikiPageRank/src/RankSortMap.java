import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RankSortMap extends Mapper<LongWritable, Text, DoubleWritable, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] linkInfo = value.toString().split(":");
		String page = linkInfo[0].trim();
		Double pageRank = Double.parseDouble(linkInfo[1].trim());
		
		context.write(new DoubleWritable(pageRank), new Text(page));
	}
}
