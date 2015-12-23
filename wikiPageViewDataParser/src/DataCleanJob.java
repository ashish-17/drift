
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

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
		DataCleanJob.runDailyTrendEstimationJob(args[0]);
	}
	
	public static void runDataAggregationJob(String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job;
		try {
			job = Job.getInstance(conf, "Data Aggreagation");
			job.setInputFormatClass(TextInputFormat.class);

			job.setMapperClass(DataCleanMapper.class);
			job.setReducerClass(TrendFindReducer.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(PageDataValue.class);
			job.setOutputKeyClass(TrendKey.class);
			job.setOutputValueClass(Text.class);
			
			job.setJarByClass(DataCleanJob.class);

			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			
			job.waitForCompletion(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

	public static void runTrendCalcJob(String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job;
		try {
			job = Job.getInstance(conf, "Trend Calc");
			job.setInputFormatClass(TextInputFormat.class);

			job.setMapperClass(TrendMapper.class);
			job.setReducerClass(TrendTopCalcReducer.class);
			
			job.setMapOutputKeyClass(TrendKey.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			job.setJarByClass(DataCleanJob.class);

			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			
			job.waitForCompletion(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
	public static void runDailyTrendEstimationJob(String inputPath) throws Exception {
		
		Configuration conf = new Configuration();
		Job job;
		try {
			conf.set("mongo.output.uri", "mongodb://52.33.93.221:27017/mongo_hadoop.page_trends");
			job = Job.getInstance(conf, "dataclean");

			job.setJarByClass(DataCleanJob.class);
			job.setMapperClass(DataCleanMapper.class);
			job.setReducerClass(DataCleanReducer.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(PageDataValue.class);
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(MongoUpdateWritable.class);

			FileInputFormat.setInputDirRecursive(job, true);
			FileInputFormat.addInputPath(job, new Path(inputPath));
			
			MongoConfig mongoConfig = new MongoConfig(conf);
			mongoConfig.setOutputFormat(MongoOutputFormat.class);

			job.setOutputFormatClass(MongoOutputFormat.class);
			
			System.exit(job.waitForCompletion(true) ? 0 : 1);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
