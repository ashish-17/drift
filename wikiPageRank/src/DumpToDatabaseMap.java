import java.io.IOException;
import java.net.URLDecoder;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DumpToDatabaseMap extends Mapper<LongWritable, Text, Text, DoubleWritable> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] linkInfo = value.toString().split(":");
		String page = linkInfo[0].trim();
		Double pageRank = Double.parseDouble(linkInfo[1].trim());
		try {
			page = URLDecoder.decode(page, "UTF-8");
			page = page.replace("_", " ").trim();
			if (page.length() >0) {
				context.write(new Text(page), new DoubleWritable(pageRank));
			}
		} catch (Exception e) {
			// Nothing!!
		}
	}
}
