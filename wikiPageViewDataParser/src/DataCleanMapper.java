import java.io.IOException;
import java.net.URLDecoder;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class DataCleanMapper extends Mapper<LongWritable, Text, Text, PageDataValue> {

	private static PageDataValue pageDataValue = new PageDataValue();
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer lineTokenizer = new StringTokenizer(value.toString(), "\n\r\f");
		String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
		String[] fileNameSplits = fileName.split("-");
		if (fileNameSplits[0].compareToIgnoreCase("pagecounts") == 0) {
			
			while (lineTokenizer.hasMoreTokens()) {

				StringTokenizer colmTokenizer = new StringTokenizer(lineTokenizer.nextToken(), " \t");
				if (colmTokenizer.countTokens() == 4) {
					String domainCode = colmTokenizer.nextToken();
					if (domainCode.startsWith("en")) {
						try {
							String pageTitle = URLDecoder.decode(colmTokenizer.nextToken(), "UTF-8");
							if (pageTitle.matches("^[a-zA-Z0-9 _]*$")) {
								pageTitle = pageTitle.replace("_", " ").trim();
								if (pageTitle .length() >0) {
									pageDataValue.date = fileNameSplits[1];
									pageDataValue.countViews = Integer.parseInt(colmTokenizer.nextToken());
									String totalResponseSize = colmTokenizer.nextToken();

									context.write(new Text(pageTitle), pageDataValue);
								}
							}
						} catch (IllegalArgumentException e) {
							// Nothing
						}
					}
				}
			}
		}
	}

}
