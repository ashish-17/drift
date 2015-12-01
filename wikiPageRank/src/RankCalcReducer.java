import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RankCalcReducer extends Reducer<Text, Text, Text, Text> {

	public static final double DAMPING_FACTOR = 0.85;
	
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		
		String to = "";
		boolean exits = false;
		double pageRankContribution = 0.0;
		while (values.iterator().hasNext()) {
			String val = values.iterator().next().toString();
			
			if(val.equals("!")) {
				exits = true;
                continue;
            }
			
			if (val.startsWith(":")) {
				to = val.substring(1);
				continue;
			}
			
			String[] contributorData = val.split(" ");
			
			double pageRankContributor = Double.parseDouble(contributorData[1].trim());
			int numOutLinksFromContributor = Integer.parseInt(contributorData[2].trim());
			
			pageRankContribution += pageRankContributor / numOutLinksFromContributor;
		}
		
		if (exits) {
			double finalPageRank = (1 - DAMPING_FACTOR) + (DAMPING_FACTOR * pageRankContribution); 

			context.write(key, new Text(":" + finalPageRank + ":" + to));
		}
	}
}
