/**
 * 
 */


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.mongodb.hadoop.MongoConfig;
import com.mongodb.hadoop.MongoOutputFormat;
import com.mongodb.hadoop.io.MongoUpdateWritable;

/**
 * @author ashish
 *
 */
public class RankCalcJob {

    private static NumberFormat nf = new DecimalFormat("00");
    private static final int NUM_RANK_ITERATIONS = 3;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		RankCalcJob.runDataParsingJob(args[0], args[1]);
		
		for (int i = 0; i < NUM_RANK_ITERATIONS; ++i) {
			if (i == 0) {
				RankCalcJob.runRankCalcJob(args[1], args[1]+nf.format(i));
			} else {
				RankCalcJob.runRankCalcJob(args[1]+nf.format(i - 1), args[1]+nf.format(i));
			}
		}
		
		RankCalcJob.runDumpToDatabaseJob(args[1]+nf.format(NUM_RANK_ITERATIONS-1));
	}

	public static void runDataParsingJob(String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job;
		try {

	        conf.set(com.xebia.sandbox.hadoop.job1.xmlhakker.XmlInputFormat.START_TAG_KEY, "<page>");
	        conf.set(com.xebia.sandbox.hadoop.job1.xmlhakker.XmlInputFormat.END_TAG_KEY, "</page>");

			job = Job.getInstance(conf, "Rank Sort");
			job.setInputFormatClass(com.xebia.sandbox.hadoop.job1.xmlhakker.XmlInputFormat.class);
	        
			job.setJarByClass(RankCalcJob.class);
			job.setMapperClass(com.xebia.sandbox.hadoop.job1.xmlhakker.WikiPageLinksMapper.class);
			job.setReducerClass(com.xebia.sandbox.hadoop.job1.xmlhakker.WikiLinksReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			
			job.waitForCompletion(true);
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
	
	private static void runRankCalcJob(String inputPath, String outputPath) {
		Configuration conf = new Configuration();
		Job job;
		try {
			job = Job.getInstance(conf, "Rank Calc");

			job.setJarByClass(RankCalcJob.class);
			job.setMapperClass(RankCalcMap.class);
			job.setReducerClass(RankCalcReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			
			job.waitForCompletion(true);
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

	private static void runRankSortJob(String inputPath, String outputPath) {
		Configuration conf = new Configuration();
		Job job;
		try {
			job = Job.getInstance(conf, "Rank Sort");

			job.setJarByClass(RankCalcJob.class);
			job.setMapperClass(RankSortMap.class);
			job.setOutputKeyClass(DoubleWritable.class);
			job.setOutputValueClass(Text.class);

			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			
			job.waitForCompletion(true);
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
	
	private static void runDumpToDatabaseJob(String inputPath) throws Exception {
		
		Configuration conf = new Configuration();
		Job job;
		try {
			conf.set("mongo.output.uri", "mongodb://52.34.106.174:27017/mongo_hadoop.page_rank");
			job = Job.getInstance(conf, "dumpToDB");

			job.setJarByClass(RankCalcJob.class);
			job.setMapperClass(DumpToDatabaseMap.class);
			job.setReducerClass(DumpToDatabaseReducer.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(DoubleWritable.class);
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(MongoUpdateWritable.class);

			FileInputFormat.addInputPath(job, new Path(inputPath));
			
			MongoConfig mongoConfig = new MongoConfig(conf);
			mongoConfig.setOutputFormat(MongoOutputFormat.class);

			job.setOutputFormatClass(MongoOutputFormat.class);
			
			job.waitForCompletion(true);

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
