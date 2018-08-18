package CaseStudy1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CaseStudyIUseCasesDriver {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
    if (args.length != 3) {
      System.err.println("Usage: CaseStudyIUseCase2Driver <input path1> <input path2> <output path>");
      System.exit(-1);
    }
    
	//Job Related Configurations
	Configuration conf = new Configuration();
	Job job = new Job(conf, "CaseStudyIUseCase2Driver");
	job.setJarByClass(CaseStudyIUseCasesDriver.class);

	//job.setNumReduceTasks(0);
    
	//Since there are multiple input, there is a slightly different way of specifying input path, input format and mapper
	MultipleInputs.addInputPath(job, new Path(args[0]),TextInputFormat.class, CaseStudyIUseCasesMoviesMapper.class);
	MultipleInputs.addInputPath(job, new Path(args[1]),TextInputFormat.class, CaseStudyIUseCasesRatingsMapper.class);
	
	//Set the reducer
	job.setReducerClass(CaseStudyIUseCasesReducer.class);

    //set the out path
	Path outputPath = new Path(args[2]);
	FileOutputFormat.setOutputPath(job, outputPath);
	outputPath.getFileSystem(conf).delete(outputPath, true);

    //set up the output key and value classes 	
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    
    //execute the job
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}