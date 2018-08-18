package class5Counters;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;

public class CustomCounter {
	
  //Declare a public static java enum to hold the values of the months	
  public static enum MONTH{
		DEC,
		JAN,
		FEB
	};
	
  public static void main(String[] args) 
                  throws IOException, ClassNotFoundException, InterruptedException {
    
	//Job Related Configurations
	Configuration conf = new Configuration();
	Job job = new Job(conf, "Custom Counter Sample");
	job.setJarByClass(CustomCounter.class);
    
	//Explicitly set the reduce task to zero so that the reducer does not come into the picture
    job.setNumReduceTasks(0);
    
    //Set the mapper class
    job.setMapperClass(CustomCounterMapper.class);
    
    //Set the output key class and values
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
   
    //Set the path to take input file
    FileInputFormat.addInputPath(job, new Path(args[0]));
    
	//set the out path and delete it on the second run
	Path outputPath = new Path(args[1]);
	FileOutputFormat.setOutputPath(job, outputPath);
	outputPath.getFileSystem(conf).delete(outputPath, true);

    //Execute the job
    job.waitForCompletion(true);
    
    //Some std outs for the counters to come on the console
    Counters counters = job.getCounters();
    
    Counter c1 = counters.findCounter(MONTH.DEC);
    System.out.println(c1.getDisplayName()+ " : " + c1.getValue());
    c1 = counters.findCounter(MONTH.JAN);
    System.out.println(c1.getDisplayName()+ " : " + c1.getValue());
    c1 = counters.findCounter(MONTH.FEB);
    System.out.println(c1.getDisplayName()+ " : " + c1.getValue());
    
  }
}