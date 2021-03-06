import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.hadoop.io.MongoUpdateWritable;

public class DataCleanReducer extends Reducer<Text, PageDataValue, NullWritable, MongoUpdateWritable> {

    private MongoUpdateWritable reduceResult = new MongoUpdateWritable();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat formatterOut = new SimpleDateFormat("yyyyMM");
    private Calendar calendar = Calendar.getInstance();
    
	public void reduce(Text key, Iterable<PageDataValue> values, Context context)
			throws IOException, InterruptedException {
		
		Map<Date, Integer> stats = new HashMap<>();
		while (values.iterator().hasNext()) {
			PageDataValue tmp = values.iterator().next();
			Date date;
			try {
				date = formatter.parse(tmp.date);
				if (stats.containsKey(date)) {
					stats.put(date, stats.get(date) + tmp.countViews);
					
					//System.out.println(key.toString() + " - " + stats.get(date));
				} else {
					stats.put(date, tmp.countViews);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		Date minDate = null, maxDate = null;
		for (Date date : stats.keySet()) {
			if ((minDate == null) || (date.before(minDate))) {
				minDate = date;
			}
			
			if ((maxDate == null) || (date.after(maxDate))) {
				maxDate = date;
			}
		}

		//System.out.println("Min Date - " + minDate.toString());
		//System.out.println("Max Date - " + maxDate.toString());
		
		Date prev = null;
		Map<Date, Double> trend = new HashMap<>();
		int totalPageViews = 0;
		for (Date date = minDate; date.compareTo(maxDate) <= 0;){
			
			if (prev != null) {
				if (stats.containsKey(prev) == false) {
					if (stats.containsKey(date)) {
						trend.put(date, (double)stats.get(date));
					} else {
						trend.put(date, 0.0);
					}
				} else {
					if (stats.containsKey(date)) {
						trend.put(date, (double)(stats.get(date) - stats.get(prev))); // Slope
						//System.out.println(key.toString() + " - Slope =  " + stats.get(date) + "-" + stats.get(prev) + " = "+ trend.get(date));
					} else {
						trend.put(date, (double)(0 - stats.get(prev)));
					}
				}
			} else {
				trend.put(date, 0.0);
			}

			if (stats.containsKey(date)) {
				totalPageViews += stats.get(date);
			}
			
			prev = date;
			
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			
			date = calendar.getTime();
		}
		
		for (Entry<Date, Double> entry: trend.entrySet()) {

			BasicBSONObject query=new BasicBSONObject();
			query.append("_id", key.toString());
			
			BSONObject boVal = BasicDBObjectBuilder.start()
								.add("date", formatter.format(entry.getKey()))
								.add("viewCount", stats.containsKey(entry.getKey()) ? stats.get(entry.getKey()):0)
								.add("trend", entry.getValue() * (Math.log(1 + totalPageViews)))
								.get();
			
			//System.out.println(key.toString() + " - viewCount -" + (stats.containsKey(entry.getKey()) ? stats.get(entry.getKey()):0));
			//System.out.println(key.toString() + " - trend -" + entry.getValue() * (Math.log(1 + totalPageViews)));
			
			BasicBSONObject pageTitleData = new BasicBSONObject(formatterOut.format(entry.getKey()), boVal);
			BasicBSONObject pageTitleDataUpdate = new BasicBSONObject("$push", pageTitleData);
			
			reduceResult.setQuery(query);
			reduceResult.setModifiers(pageTitleDataUpdate);
			reduceResult.setUpsert(true);
			context.write(null, reduceResult);
		}
	}
}