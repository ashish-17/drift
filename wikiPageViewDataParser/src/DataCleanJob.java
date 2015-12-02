import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import com.mongodb.hadoop.MongoConfig;
import com.mongodb.hadoop.MongoOutputFormat;
import com.mongodb.hadoop.io.MongoUpdateWritable;
import com.mongodb.hadoop.util.MongoTool;

/**
 * @author ashish
 *
 */
public class DataCleanJob  extends MongoTool {
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job;
		try {
			conf.set("mongo.output.uri", "mongodb://52.34.106.174:27017/mongo_hadoop.page_views");
			job = Job.getInstance(conf, "dataclean");

			job.setJarByClass(DataCleanJob.class);
			job.setMapperClass(DataCleanMapper.class);
			//job.setCombinerClass(DataCleanCombiner.class);
			job.setReducerClass(DataCleanReducer.class);
			
			job.setMapOutputKeyClass(PageDataKey.class);
			job.setMapOutputValueClass(PageDataValue.class);
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(MongoUpdateWritable.class);

			FileInputFormat.setInputDirRecursive(job, true);
			FileInputFormat.addInputPath(job, new Path(args[0]));
			
			MongoConfig mongoConfig = new MongoConfig(conf);
			mongoConfig.setOutputFormat(MongoOutputFormat.class);

			job.setOutputFormatClass(MongoOutputFormat.class);
			
			System.exit(job.waitForCompletion(true) ? 0 : 1);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
