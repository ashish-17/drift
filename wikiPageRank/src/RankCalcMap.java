import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RankCalcMap extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] linkInfo = value.toString().split(":");
		String from = linkInfo[0].trim();
		double pageRank = 1.0;
		String to = "";
		if (linkInfo.length == 3){
			pageRank = Double.parseDouble(linkInfo[1].trim());
			to = linkInfo[2].trim();
		} else if (linkInfo.length == 2) {
			to = linkInfo[1].trim();
		} else {
			return; // shouldn't happen.
		}

		context.write(new Text(from), new Text("!"));
		
		StringTokenizer linkTokenizer = new StringTokenizer(to, " \t");;
		int numOutLinks = linkTokenizer.countTokens();
		while (linkTokenizer.hasMoreTokens()) {
			String newKey = linkTokenizer.nextToken().trim();
			StringBuffer newVal = new StringBuffer(from);
			newVal.append(" " + pageRank + " " + numOutLinks);
			
			context.write(new Text(newKey), new Text(newVal.toString()));
		}

		//Write the original line with some identifier.
		context.write(new Text(from), new Text(":" + to));
	}

}
