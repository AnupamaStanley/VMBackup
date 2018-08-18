package class5Todo;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;





public class HighestDriver {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
	
    	    if (args.length != 2) {
    	        System.err.println("Usage: HighestDriver <input path> <output path>");
    	        System.exit(-1);
    	      }
  
            
    		//Job Related Configurations
    		Configuration conf = new Configuration();
    		Job job = new Job(conf, "Highest Driver");
    		job.setJarByClass(HighestDriver.class);

         
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            
            job.setMapperClass(HighestMapper.class);
            job.setReducerClass(HighestReducer.class);
            
            Path inp = new Path(args[0]);
            Path out = new Path(args[1]);
            
          //Provide paths to pick the input file for the job
            FileInputFormat.setInputPaths(job, new Path(args[0]));
            
            
            //Provide paths to pick the output file for the job, and delete it if already present
        	Path outputPath = new Path(args[1]);
        	FileOutputFormat.setOutputPath(job, outputPath);
        	outputPath.getFileSystem(conf).delete(outputPath, true);
            //FileOutputFormat.setOutputPath(job, new Path(args[1]));
           
        	//execute the job
            System.exit(job.waitForCompletion(true) ? 0 : 1);
            
          //Some std outs for the counters to come on the console
            Counters counters = job.getCounters();
            
            Counter goodCounter = counters.findCounter(HighestCounter.TEMPRECORDQUALITY.GOOD);
            System.out.println(goodCounter.getDisplayName()+ " : " + goodCounter.getValue());
            
      }
     
    
}

