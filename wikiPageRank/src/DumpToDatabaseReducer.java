import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.hadoop.io.MongoUpdateWritable;

public class DumpToDatabaseReducer extends Reducer<Text, DoubleWritable, NullWritable, MongoUpdateWritable> {

    private MongoUpdateWritable reduceResult = new MongoUpdateWritable();
    
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
			throws IOException, InterruptedException {
		
		BasicBSONObject query=new BasicBSONObject();
		query.append("_id", key.toString());
		
		BSONObject boVal = BasicDBObjectBuilder.start()
							.add("pageRank", values.iterator().next().toString())
							.get();
		
		BasicBSONObject pageRankDataUpdate = new BasicBSONObject("$set", boVal);
		
		reduceResult.setQuery(query);
		reduceResult.setModifiers(pageRankDataUpdate);
		reduceResult.setUpsert(true);
		context.write(null, reduceResult);
	}

}
